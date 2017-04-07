package com.wxmimperio.kafka;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;
import org.junit.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by weiximing.imperio on 2017/3/21.
 */
public class JsonTest {

    @org.junit.Test
    public void jsonTest() {
        String str = "{\"name\":\"465\"}";
        JsonObject json = new JsonParser().parse(str).getAsJsonObject();

        System.out.println(StringUtils.isBlank(json.get("name").getAsString()));

        System.out.println(json.has("age"));

        int errorCode = !StringUtils.isBlank(json.get("name").getAsString()) ? Integer.parseInt(json.get("name").getAsString()) : -1;
        System.out.println(errorCode);

        String jsonStr = "{\"req_str\": \"http_x_app_id:991000801^_^http_x_app_version:1.90^_^http_x_channel:G34^_^data:{\\\"model\\\":\\\"Coolpad 8079\\\",\\\"phoneNumber\\\":\\\"\\\",\\\"metrics\\\":\\\"480*854\\\",\\\"ram\\\":\\\"421\\\",\\\"merchant\\\":\\\"移动 3G\\\",\\\"systemVersion\\\":\\\"4.0.3\\\",\\\"cpu\\\":\\\"armeabi-v7a\\\",\\\"mac\\\":\\\"\\\",\\\"deviceId\\\":\\\"862532025733733\\\",\\\"ipAddress\\\":\\\"0.0.0.0\\\",\\\"storage\\\":\\\"7603\\\"}^_^type:android^_^deviceid:862532025733733-b3c76a723b30e0c6^_^http_x_channel_info:\"}";
        JsonObject jsObj = new JsonParser().parse(jsonStr).getAsJsonObject();

        String[] reqStr = jsObj.get("req_str").getAsString().split("\\^_\\^", -1);

        JsonObject reqJson = new JsonObject();
        for (String req : reqStr) {
            String[] temp = req.split(":", -1);
            if (req.contains("{") || req.contains("}")) {
                reqJson.addProperty(temp[0], req.substring(req.indexOf("{"), req.indexOf("}") + 1));
            } else {
                reqJson.addProperty(temp[0], temp[1]);
            }
        }

        System.out.println(reqJson);
        System.out.println(new JsonParser().parse(reqJson.get("data").getAsString()));
    }

    private static Map<Integer, Integer> appIdMap = new HashMap<Integer, Integer>() {
        {
            put(1007, 791000009);
            put(1010, 991001386);
            put(1011, 791000007);
            put(1017, 791000012);
        }
    };


    @Test
    public void mapTest() {
      /*  for (int key : appIdMap.keySet()) {
            System.out.println(key + " " + appIdMap.get(key));
        }

        String str = "{\"name\":\"\"}";

        String[] testStr = "name:".split(":", -1);

        JsonObject json = new JsonParser().parse(str).getAsJsonObject();
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty(testStr[0], testStr[1].equalsIgnoreCase("") ? "-1" : testStr[1]);
        System.out.println(jsonObj.get("name").getAsString());*/

        String pt_deposit_103 = "2017-03-07 17:43:56\t{\"messageType\": 103,\"orderId\": \"99000000030046170307174352452638\",\"contextId\": \"99000000030046170307174253450504\",\"appCode\": 8,\"settleTime\": \"2017-03-07 17:43:56\",\"endpointIp\": \"0.0.0.0\",\"ptIdSrc\": \"hn00098552621.pt\",\"sndaIdSrc\": \"1973609525\",\"appIdSrc\": 991002359,\"areaIdSrc\": 5,\"payTypeIdSrc\": 3,\"amountSrc\": 63,\"ptId\": \"hn00098552621.pt\",\"sndaId\": \"1973609525\",\"appId\": 991002359,\"areaId\": 5,\"payTypeId\": 3,\"amount\": 63,\"balanceBefore\": 366426,\"ptIdDest\": \"fi00688226404.pt\",\"sndaIdDest\": \"3493160875\",\"appIdDest\": 991002359,\"areaIdDest\": 5,\"payTypeIdDest\": 3,\"amountDest\": 63,\"payDetail\": \"\",\"feeSrc\": 2,\"feeDest\": 0,\"itemInfo\": \"1,991002359,5,1,1,65\",\"balanceBeforeSrc\": 366426,\"balanceBeforeDest\": 5926,\"messageId\": \"BS3437148887983650800001\",\"messageSourceIp\": \"10.129.34.37\",\"messageTimestamp\": \"2017-03-07 17:43:56.508\"}";

        JsonObject pt = new JsonParser().parse(pt_deposit_103.split("\t", -1)[1]).getAsJsonObject();
        System.out.println(pt + "");

    }
}
