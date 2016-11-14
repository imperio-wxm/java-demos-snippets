package com.wxmimperio.curator;

import com.wxmimperio.curator.opts.CuratorOpts;
import org.junit.Test;

/**
 * Created by weiximing.imperio on 2016/11/14.
 */
public class CuratorOptsTest {

    @Test
    public void OptsTest() throws Exception {
        String path = "/brokers/topics/third_test/partitions/0/state";
        CuratorOpts curatorOpts = new CuratorOpts();

        //检查/consumers/group_1是否存在
        System.out.println(curatorOpts.isPathExist(path));

        //添加节点
        //curatorOpts.addNode(path, "test");

        //System.out.println(curatorOpts.isPathExist(path));

        curatorOpts.readData(path);

        //更新节点
        //curatorOpts.updateNode(path,"test_update");

        //读取更新后信息
        //curatorOpts.readData(path);

        //删除节点
        //curatorOpts.deleteNode(path);

        //验证是否删除
        //System.out.println(curatorOpts.isPathExist(path));

    }
}
