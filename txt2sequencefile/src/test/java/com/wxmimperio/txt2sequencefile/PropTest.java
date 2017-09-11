package com.wxmimperio.txt2sequencefile;

import com.google.common.collect.Lists;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by weiximing.imperio on 2017/4/12.
 */
public class PropTest {

    @Test
    public void prop() {
        System.out.println(PropManager.getInstance().getPropertyByString("hdfs.uri"));
        System.out.println(PropertiesUtil.getString("hdfs.uri"));

        System.out.println("fsol_forceshop_glog,fsol_heroperk_glog,fsol_battlepower_glog,fsol_herolevelup_glog,fsol_servercrash_glog,fsol_item_glog,fsol_instance_glog,fsol_resource_glog,fsol_robot_glog,fsol_databaseerror_glog,fsol_cap_glog,fsol_character_logout_glog,fsol_task_glog,fsol_reg_glog,fsol_gamesvronline_glog,fsol_gamesvrstate_glog,fsol_herostrength_glog,fsol_character_login_glog,fsol_character_levelup_glog,fsol_herocure_glog,fsol_security_fluence_glog,fsol_heroequip_glog,fsol_city_glog,fsol_herostar_glog,fsol_medicalroom_glog,fsol_character_create_glog,fsol_olnum_glog".split(",", -1).length);

        System.out.println("sxcq_money_glog,sxcq_deposit_glog,sxcq_olnum_glog,sxcq_character_logout_glog,sxcq_character_login_glog,sxcq_character_glog,sxcq_guild_member_glog,sxcq_item_glog,sxcq_ce_glog,sxcq_character_levelup_glog,sxcq_instance_glog,sxcq_reg_glog,sxcq_newbee_guide_glog,sxcq_mall_glog,sxcq_task_glog".split(",", -1).length);

        System.out.println("测试");
    }

    @Test
    public void queueTest() {
        List<String> bufferList = Lists.newArrayList();

        String data = "fsol_forceshop_glog,fsol_heroperk_glog,fsol_battlepower_glog,fsol_herolevelup_glog,fsol_servercrash_glog,fsol_item_glog,fsol_instance_glog,fsol_resource_glog,fsol_robot_glog,fsol_databaseerror_glog,fsol_cap_glog,fsol_character_logout_glog,fsol_task_glog,fsol_reg_glog,fsol_gamesvronline_glog,fsol_gamesvrstate_glog,fsol_herostrength_glog,fsol_character_login_glog,fsol_character_levelup_glog,fsol_herocure_glog,fsol_security_fluence_glog,fsol_heroequip_glog,fsol_city_glog,fsol_herostar_glog,fsol_medicalroom_glog,fsol_character_create_glog,fsol_olnum_glog\t";

        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000000; i++) {
            bufferList.add(data);
        }
        long end = System.currentTimeMillis();

        System.out.println("cost = " + (end - start));

        long start1 = System.currentTimeMillis();
        /*StringBuilder stringBuilder = new StringBuilder();
        for (String str : bufferList) {
            stringBuilder.append(str);
        }*/
        bufferList.size();
        long end1 = System.currentTimeMillis();

        System.out.println("cost = " + (end1 - start1));

        String count = "{\"workdir\":\"data\",\"maxFilePullThread\":10,\"maxDatabaseThread\":10,\"maxCmdThread\":10,\"maxUploadThread\":10,\"maxOutBandWidth\":-1,\"cleanTimeout\":3,\"logLevel\":\"INFO\",\"useCell\":false,\"restEnable\":false}";
    }

    @Test
    public void timeTest() {
        Date now = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

        String strNow = format.format(now);
        String strSs = strNow.substring(12, 14);
        String strMi = strNow.substring(10, 12);
        String strDate = strNow.substring(0, 8);

        int sec = Integer.parseInt(strSs);
        int mi = Integer.parseInt(strMi);

        System.out.println(sec);

        System.out.println(mi);

        Date startMin = new Date((long) (Math.floor((now.getTime() / 1000 - 1 * 60) / 60) * 60 * 1000));
        String strStartMin = format.format(startMin);
        Date endMin = new Date((long) (Math.floor((now.getTime() / 1000) / 60) * 60 * 1000));
        String strEndMin = format.format(endMin);
        System.out.println("strStartMin:" + strStartMin + " strEndMin:" + strEndMin);


        long startTime = 0;
        long endTime = 0;
        // NumPropertiesUtil numUtil=NumPropertiesUtil.getInstance();

        startTime = (parseDateString(strStartMin) / (1 * 60 * 1000)) * 1 * 60;
        endTime = (parseDateString(strEndMin) / (1 * 60 * 1000)) * 1 * 60;


        System.out.println(startTime);
        System.out.println(endTime);

        String insertStr = "2016-03-09 16:32:00\t2\t100000600\t111\t18886\t34501\t43783\t2\t934\t580\t2016-03-09 16:32:14";

        String[] insert = insertStr.split("\\t", -1);
        System.out.println(Arrays.asList(insert));

    }

    private static Long parseDateString(String dateStr) {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;

        format = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            date = format.parse(dateStr);
            return date.getTime();
        } catch (java.text.ParseException e) {
        }
        if (null != date) {
            return date.getTime();
        } else {
            return 0l;
        }
    }
}
