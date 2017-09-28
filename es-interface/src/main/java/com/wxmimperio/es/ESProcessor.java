package com.wxmimperio.es;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;


public class ESProcessor {

    private static Log logger = LogFactory.getLog(ESProcessor.class);

    // private static String hdfsUri = "";
    private static final String DEFAULTFS = "fs.defaultFS";
    private static final String DFS_FAILURE_ENABLE = "dfs.client.block.write.replace-datanode-on-failure.enable";
    private static final String DFS_FAILURE_POLICY = "dfs.client.block.write.replace-datanode-on-failure.policy";
    private static final String DFS_SESSION_TIMEOUT = "dfs.client.socket-timeout";
    private static final String DFS_TRANSFER_THREADS = "dfs.datanode.max.transfer.threads";
    private static final String DFS_SUPPORT_APPEND = "dfs.support.append";
    private static final String CORE_SITE_XML = "core-site.xml";
    private static final String HDFS_SITE_XML = "hdfs-site.xml";


    public static Configuration config() {
        Configuration conf = new Configuration();
        conf.addResource(CORE_SITE_XML);
        conf.addResource(HDFS_SITE_XML);
        conf.set(DFS_SUPPORT_APPEND, "true");
        conf.set(DFS_FAILURE_ENABLE, "true");
        conf.set(DEFAULTFS, "");
        conf.set(DFS_FAILURE_POLICY, "NEVER");
        // conf.set(DFS_SESSION_TIMEOUT, "180000");
        conf.set(DFS_TRANSFER_THREADS, "16000");
        conf.set("dfs.client.block.write.locateFollowingBlock.retries", "20");
        return conf;
    }


    /*public boolean query() throws Exception {

        String sql = "select * from swy_deposit_glog limit 10";
        StringBuffer sb = new StringBuffer();
        boolean hasInit = false;

        List<String> fields = new ArrayList<String>();

        if (sql.contains("limit") || sql.contains("LIMIT")) {
            //含有limit 循环处理
            String scrollSql = getScrollSql(sql);
            String scrollId = "";
            int limit = getLimit(sql);
            logger.info("------------temp----------limit ： " + limit);
            *//*if (limit >= 100000) {
                //大于10万的sql不执行
				hiveJob.setErrorLog("limit 不能超过10万");
				hiveJob.setIsSuccess(HiveJob.FAILED);
				return false;
			}*//*
            int beginIndex = 0;

            // http:///_sql?sql=SELECT+*+FROM+wooolh_olnum_glog+order+BY+event_time+desc
            String url = "http:///_sql?sql=" + URLEncoder.encode(scrollSql, "UTF-8");
            String result = HttpClientUtil.doGet(url);
            JSONObject jobJson = JSONObject.fromObject(result);


            System.out.println(jobJson);


            //handleResult(jobJson, fields, sb, hasInit, beginIndex, limit);


            hasInit = true;
            beginIndex += jobJson.getJSONObject("hits").getJSONArray("hits").size();
            if (jobJson.containsKey("_scroll_id")) {
                //聚合函数即使有limit 也没有_scroll_id 报错
                scrollId = jobJson.getString("_scroll_id");

                while (!jobJson.getJSONObject("hits").getJSONArray("hits").isEmpty() && beginIndex < limit) {
                    String scrollUrl = "http:///_search/scroll?scroll=10s&scroll_id=" + scrollId;
                    String scrollResult = HttpClientUtil.doGet(scrollUrl);
                    jobJson = JSONObject.fromObject(scrollResult);


                    System.out.println(jobJson);


                    //handleResult(jobJson, hiveJob, fields, sb, hasInit, beginIndex, limit);
                    //将结果写入文件
                    beginIndex += jobJson.getJSONObject("hits").getJSONArray("hits").size();
                }
            }

        } else {
            // 没有limit的处理方式
            String url = "http:///_sql?sql=" + URLEncoder.encode(sql);
            String result = HttpClientUtil.doGet(url);
            JSONObject jobJson = JSONObject.fromObject(result);

            System.out.println(jobJson);

            //return handleResult(jobJson, hiveJob, fields, sb, hasInit, 0, Integer.MAX_VALUE);

            return true;
        }
        return true;
    }

    private static final String SCROLL_CLAUSE = " *//*! USE_SCROLL(1000,10000)*//* ";

    private String getScrollSql(String sql) {
        StringBuilder sb = new StringBuilder(sql);
        sb.insert(sql.toUpperCase().indexOf("SELECT") + "SELECT".length(), SCROLL_CLAUSE);
        return sb.toString();
    }

    private int getLimit(String sql) throws Exception {
        StringTokenizer st = new StringTokenizer(sql);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("limit".equalsIgnoreCase(token) && st.hasMoreTokens()) {
                return Integer.parseInt(st.nextToken());
            }
        }
        throw new Exception("Can not parse 'LIMIT' from sql : " + sql);
    }

    private boolean handleResult(JSONObject jobJson, List<String> fields, StringBuffer sb, boolean hasInit, int beginIndex, int limit) throws Exception {

        JSONObject hitsObject = jobJson.optJSONObject("hits");
        JSONObject errorObject = jobJson.optJSONObject("error");
        JSONObject aggObject = jobJson.optJSONObject("aggregations");


        SequenceFile.Writer writer = null;

        Path sequencePath = new Path(filePath);
        writer = SequenceFile.createWriter(
                config(),
                SequenceFile.Writer.file(sequencePath),
                SequenceFile.Writer.keyClass(Text.class),
                SequenceFile.Writer.valueClass(Text.class),
                // In hadoop-2.6.0-cdh5, it can use hadoop-common-2.6.5
                // with appendIfExists()
                SequenceFile.Writer.appendIfExists(true),
                SequenceFile.Writer.compression(SequenceFile.CompressionType.BLOCK)
        );


        if (errorObject != null) {
            String errorType = errorObject.getString("type");
            String errorReason = errorObject.getString("reason");

            logger.debug(errorType + " " + errorReason);

            return false;
        }

        if (aggObject != null) {
            //聚合sql  sb 一次性写入文件
            //  没有判断limit
            if (hasBuckets(aggObject)) {
                //有buckets的情况
                getColumn(aggObject, fields);
                for (String column : fields) {
                    sb.append(column).append("\t");
                }
                sb.append("\n");
                getResult(aggObject, sb, "", fields);


                CommonUtil.appendJobResult(hiveJob.getResultPath(), sb.toString());
                sb.delete(0, sb.length());
            } else {
                //没有buckets 的情况
                //{"took":20,"timed_out":false,"_shards":{"total":2,"successful":2,"failed":0},"hits":{"total":2157,"max_score":0.0,"hits":[]},"aggregations":{"SUM(area_id)":{"value":2133775.0}}}
                Iterator it = aggObject.keys();
                while (it.hasNext()) {
                    String keyName = it.next().toString();
                    fields.add(keyName);
                    sb.append(keyName).append("\t");
                }
                sb.append("\n");
                for (int j = 0; j < fields.size(); j++) {
                    sb.append(aggObject.getJSONObject(fields.get(j)).get("value")).append("\t");
                }
                sb.append("\n");
                writer.append();
                sb.delete(0, sb.length());
            }

        } else {
            //普通的查询
            if (hitsObject != null) {
                JSONArray dataArray = hitsObject.optJSONArray("hits");
                JSONObject _sourceObject = new JSONObject();

                //{"took":7,"timed_out":false,"_shards":{"total":2,"successful":2,"failed":0},"hits":{"total":38968,"max_score":1.0,"hits":[{"_index":"glog_swy_card_equip_up-170324","_type":"data","_id":"AVsepTEkLlM26UJjFd60","_score":1.0,"_source":{"role_id": 9980000091, "area_id": 998, "platform_id": 999, "user_name": "818694700", "level": 11, "equipid": 1022, "equiplevel": 0, "afterequipid": 1022, "afterequiplevel": 1, "diamond_consume": 0, "time": "2017-03-29 20:51:56"}}]}}
                if (dataArray != null && dataArray.size() != 0) {
                    JSONObject dataJson = null;
                    for (int i = 0; i < dataArray.size(); i++) {
                        dataJson = dataArray.optJSONObject(i);

                        _sourceObject = (JSONObject) dataJson.get("_source");

                        if (_sourceObject == null) {
                            continue;
                        }

                        if (i == 0 && !hasInit) {
                            //遍历key
                            Iterator it = _sourceObject.keys();
                            while (it.hasNext()) {
                                String keyName = it.next().toString();
                                fields.add(keyName);
                                sb.append(keyName).append("\t");
                            }
                            sb.append("\n");
                        }
                        if (beginIndex < limit) {
                            for (int j = 0; j < fields.size(); j++) {
                                sb.append(_sourceObject.get(fields.get(j))).append("\t");
                            }
                            sb.append("\n");
                            beginIndex++;
                        } else {
                            break;
                        }
                    }
                }
                CommonUtil.appendJobResult(hiveJob.getResultPath(), sb.toString());
                sb.delete(0, sb.length());
            }
        }
        return true;

    }

    private boolean hasBuckets(JSONObject aggObject) {

        for (Object objKey : aggObject.keySet()) {
            Object valObj = aggObject.get(objKey);
            if (valObj instanceof JSONObject) {
                JSONObject valJson = (JSONObject) valObj;
                if (valJson.containsKey("buckets")) {
                    return true;
                }
            }
        }

        return false;
    }

    private void getColumn(JSONObject aggObject, List<String> columnList) {

        Iterator it = aggObject.keys();
        while (it.hasNext()) {
            String keyName = it.next().toString();
            if (keyName.equals("key") || keyName.equals("doc_count")) {
                continue;
            }

//			System.out.println(keyName);
            columnList.add(keyName);
            Object value = aggObject.get(keyName);
            if (value instanceof JSONObject) {
                JSONObject valueJson = (JSONObject) value;
                if (valueJson.containsKey("buckets")) {
                    JSONArray array = valueJson.getJSONArray("buckets");
                    if (array.size() > 0) {
                        Object arrayObject = array.get(0);
                        if (arrayObject instanceof JSONObject) {
                            getColumn((JSONObject) arrayObject, columnList);
                        }
                    }
                }
            }

        }
    }

    private void getResult(JSONObject aggObject, StringBuffer sb, String prefix, List<String> columnList) {
        Iterator it = aggObject.keys();
        if (aggObject.containsKey("key")) {

            if (StringUtils.isEmpty(prefix)) {
                prefix = aggObject.getString("key");
            } else {
                prefix = prefix + "\t" + aggObject.getString("key");
            }
        }

        while (it.hasNext()) {
            Object value = aggObject.get(it.next().toString());
            if (value instanceof JSONObject) {
                JSONObject valueJson = (JSONObject) value;
                if (valueJson.containsKey("buckets")) {
                    for (Object subBucket : valueJson.getJSONArray("buckets")) {
                        getResult((JSONObject) subBucket, sb, prefix, columnList);
                    }
                } else {
                    //最后一层
                    sb.append(prefix);
                    for (String colName : columnList) {
                        if (aggObject.containsKey(colName)) {
                            sb.append("\t").append(aggObject.getJSONObject(colName).get("value"));
                        }
                    }
                    sb.append("\n");
                    break;
                }
            }

        }
    }*/

    public static void main(String[] args) {
        try {
            String hql = "select * from test where '${date}';";

            String dateDate = "2017-09-05";

            String newHql = hql.replace("${date}", dateDate);

            System.out.println(newHql);


            System.out.println(String.format("{0}{1}{2}","{",1,"}"));

            System.out.println("\u0024\u007Bdate}");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
