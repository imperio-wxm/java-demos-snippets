package com.wxmimperio.echarts.utils;

import java.util.*;

public class CommonUtils {

    public static Map<Integer, String> getJobMap() {
        String str = "";

        Map<Integer, String> map = new HashMap<>();
        for (String lines : str.split("\n", -1)) {
            String[] line = lines.split("\t", -1);
            map.put(Integer.valueOf(line[0]), line[1]);
        }
        return map;
    }

    public static String getOlnum() {
        String str = "346\n" +
                "354\n" +
                "364\n" +
                "365\n" +
                "376\n" +
                "377\n" +
                "382\n" +
                "490\n" +
                "582\n" +
                "583\n" +
                "618\n" +
                "620\n" +
                "717\n" +
                "720\n" +
                "727\n" +
                "747\n" +
                "764\n" +
                "771\n" +
                "777\n" +
                "781\n" +
                "798\n" +
                "817\n" +
                "832\n" +
                "838\n" +
                "862\n" +
                "868";
        return getStr(str);
    }

    public static String getDeposit() {
        String str = "216\n" +
                "237\n" +
                "238\n" +
                "239\n" +
                "240\n" +
                "241\n" +
                "295\n" +
                "296\n" +
                "297\n" +
                "298\n" +
                "299\n" +
                "306\n" +
                "319\n" +
                "329\n" +
                "330\n" +
                "332\n" +
                "333\n" +
                "334\n" +
                "366\n" +
                "368\n" +
                "369\n" +
                "434\n" +
                "435\n" +
                "438\n" +
                "493\n" +
                "498\n" +
                "499\n" +
                "500\n" +
                "576\n" +
                "577\n" +
                "578\n" +
                "580\n" +
                "581\n" +
                "621\n" +
                "622\n" +
                "623\n" +
                "624\n" +
                "625\n" +
                "626\n" +
                "712\n" +
                "725\n" +
                "730\n" +
                "751\n" +
                "755\n" +
                "760\n" +
                "776\n" +
                "782\n" +
                "785\n" +
                "790\n" +
                "795\n" +
                "800\n" +
                "801\n" +
                "807\n" +
                "819\n" +
                "824\n" +
                "825\n" +
                "839\n" +
                "845\n" +
                "861";
        return getStr(str);
    }

    public static String getLogin() {
        String str = "189\n" +
                "193\n" +
                "217\n" +
                "250\n" +
                "251\n" +
                "307\n" +
                "317\n" +
                "322\n" +
                "335\n" +
                "354\n" +
                "361\n" +
                "362\n" +
                "363\n" +
                "367\n" +
                "370\n" +
                "376\n" +
                "377\n" +
                "382\n" +
                "412\n" +
                "437\n" +
                "490\n" +
                "498\n" +
                "499\n" +
                "500\n" +
                "584\n" +
                "585\n" +
                "586\n" +
                "587\n" +
                "588\n" +
                "627\n" +
                "628\n" +
                "629\n" +
                "630\n" +
                "631\n" +
                "632\n" +
                "657\n" +
                "658\n" +
                "659\n" +
                "708\n" +
                "715\n" +
                "719\n" +
                "748\n" +
                "765\n" +
                "772\n" +
                "775\n" +
                "780\n" +
                "796\n" +
                "813\n" +
                "815\n" +
                "818\n" +
                "831\n" +
                "837\n" +
                "860\n" +
                "866\n" +
                "870";
        return getStr(str);
    }

    public static String getNewLogin() {
        String str = "327\n" +
                "366\n" +
                "369\n" +
                "434\n" +
                "435\n" +
                "662\n" +
                "718\n" +
                "721\n" +
                "728\n" +
                "752\n" +
                "766\n" +
                "779\n" +
                "820\n" +
                "822\n" +
                "830\n" +
                "840\n" +
                "842\n" +
                "843\n" +
                "850\n" +
                "851\n" +
                "852\n" +
                "853\n" +
                "854\n" +
                "855\n" +
                "856\n" +
                "858\n" +
                "863\n" +
                "869";
        return getStr(str);
    }

    public static String getRemain() {
        String str = "223\n" +
                "224\n" +
                "261\n" +
                "284\n" +
                "315\n" +
                "323\n" +
                "324\n" +
                "325\n" +
                "328\n" +
                "342\n" +
                "343\n" +
                "344\n" +
                "345\n" +
                "379\n" +
                "430\n" +
                "431\n" +
                "432\n" +
                "433\n" +
                "505\n" +
                "552\n" +
                "553\n" +
                "554\n" +
                "555\n" +
                "556\n" +
                "589\n" +
                "591\n" +
                "592\n" +
                "594\n" +
                "595\n" +
                "650\n" +
                "651\n" +
                "652\n" +
                "653\n" +
                "654\n" +
                "655\n" +
                "656\n" +
                "660\n" +
                "661\n" +
                "683\n" +
                "684\n" +
                "685\n" +
                "686\n" +
                "687\n" +
                "688\n" +
                "689\n" +
                "701\n" +
                "702\n" +
                "703\n" +
                "704\n" +
                "705\n" +
                "706\n" +
                "707\n" +
                "713\n" +
                "722\n" +
                "724\n" +
                "729\n" +
                "749\n" +
                "767\n" +
                "778\n" +
                "821\n" +
                "833\n" +
                "841\n" +
                "859\n" +
                "867\n" +
                "871";
        return getStr(str);
    }

    public static String getOther() {
        StringBuilder stringBuilder = new StringBuilder("not in (");
        for (String id : getOlnum().replaceAll("\\(","").replaceAll("\\)","").split(",", -1)) {
            stringBuilder.append(id).append(",");
        }
        for (String id : getDeposit().replaceAll("\\(","").replaceAll("\\)","").split(",", -1)) {
            stringBuilder.append(id).append(",");
        }
        for (String id : getLogin().replaceAll("\\(","").replaceAll("\\)","").split(",", -1)) {
            stringBuilder.append(id).append(",");
        }
        for (String id : getNewLogin().replaceAll("\\(","").replaceAll("\\)","").split(",", -1)) {
            stringBuilder.append(id).append(",");
        }
        for (String id : getRemain().replaceAll("\\(","").replaceAll("\\)","").split(",", -1)) {
            stringBuilder.append(id).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1).append(")");
        return stringBuilder.toString();
    }

    private static String getStr(String str) {
        StringBuilder stringBuilder = new StringBuilder("(");
        for (String id : str.split("\n", -1)) {
            stringBuilder.append(id).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1).append(")");
        return stringBuilder.toString();
    }

}
