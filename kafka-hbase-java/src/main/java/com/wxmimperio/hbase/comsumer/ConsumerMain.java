package com.wxmimperio.hbase.comsumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class ConsumerMain {
    private static final ThreadLocal<SimpleDateFormat> srcFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        }
    };

    public static void main(String args[]) {
        /*List<String> topicList = new ArrayList<>();
        topicList.add("test_001");
        topicList.add("test_2");
        topicList.add("test_1");

        List<String> topicList1 = new ArrayList<>();
        topicList1.add("test_001");
        topicList1.add("test_1");

        KafkaNewConsumer consumer = new KafkaNewConsumer(topicList, "group_1");
        consumer.execute(3);

        KafkaNewConsumer consumer1 = new KafkaNewConsumer(topicList, "group_2");
        consumer1.execute(3);

        KafkaNewConsumer consumer2 = new KafkaNewConsumer(topicList1, "group_4");
        consumer2.execute(3);*/

        try {
            JSONObject messageObj = JSON.parseObject("{\"source_path\":\"/opt/logs/us-ugw/auditlog/request_audit.log.2017-06-01\",\"service_id\":59501,\"area_id\":\"-1\",\"idx2\":\"18022106958\",\"service_id_msg_id\":\"59501_149\",\"error_code\":\"0\",\"type\":\"us-ugw\",\"req_str\":\"app_version:i.7.1.0^_^device_id:CFA4CF9D-547E-496F-BD4D-4AAF9D57B720^_^device_os:iOS10.3.2^_^media_channel:AppStore^_^src_code:8^_^usersessid:US_3E4CE0883B574A9489AF919CD360B818^_^user_client_ip:14.114.173.151^_^circle_id:854698^_^extend_input:^_^_version:\",\"source_host\":\"10.129.20.123\",\"client_ip\":\"14.114.173.151\",\"timestamp\":\"2017-06-01T00:00:01.210+08:00\",\"proxy_ip\":\"10.129.20.122\",\"duration\":97,\"request_id\":\"9850F00FCECBFA4FA1649D5243741491\",\"res_str\":\"triggle_phone:18022106958^_^bpecode:0^_^bpemsg:success^_^extend_return:\",\"msg_id\":149,\"soc_name\":\"\",\"app_id\":\"-1\"}");
            String time = messageObj.getString("timestamp");

            Date date = srcFormat.get().parse(time);
            System.out.println(date.before(srcFormat.get().parse("2017-06-02T00:00:00.000+08:00")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
