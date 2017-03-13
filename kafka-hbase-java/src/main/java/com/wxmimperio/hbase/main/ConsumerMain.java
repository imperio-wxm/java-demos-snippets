package com.wxmimperio.hbase.main;

import com.wxmimperio.hbase.common.ParamsConst;
import com.wxmimperio.hbase.common.PropManager;
import com.wxmimperio.hbase.comsumer.KafkaNewConsumer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class ConsumerMain {
    public static void main(String args[]) {
        List<String> topicList = PropManager.getInstance().getPropertyByStringList(ParamsConst.TOPIC_NAME);
        String threadNum = PropManager.getInstance().getPropertyByString(ParamsConst.THREAD_NUM);
        String groupName = PropManager.getInstance().getPropertyByString(ParamsConst.GROUP_ID);

        KafkaNewConsumer consumer = new KafkaNewConsumer(topicList, groupName);
        consumer.execute(Integer.valueOf(threadNum));
    }
}
