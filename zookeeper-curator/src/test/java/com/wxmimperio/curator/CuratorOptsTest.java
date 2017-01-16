package com.wxmimperio.curator;

import com.wxmimperio.curator.connect.CuratorConnect;
import com.wxmimperio.curator.opts.CuratorOpts;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

/**
 * Created by weiximing.imperio on 2016/11/14.
 */
public class CuratorOptsTest {
    private CuratorConnect curatorConnect = new CuratorConnect();
    private CuratorFramework client = curatorConnect.getCuratorConnect();

    @Test
    public void OptsTest() throws Exception {
        String path = "/consumers/group_1";
        //String path = "/consumers/group_1/owners/test_111/0";
        CuratorOpts curatorOpts = new CuratorOpts();


        //添加节点
        //curatorOpts.addNode(path, "test");

        //检查/consumers/group_1是否存在
        System.out.println(curatorOpts.isPathExist(path));

        //System.out.println(curatorOpts.isPathExist(path));

        //添加临时节点

        String childPath1 = ZKPaths.makePath(path, "owners");

        System.out.println(childPath1);

        String childPath2 = ZKPaths.makePath(childPath1, "topic_111");
        System.out.println(childPath2);
        System.out.println(client.getChildren().forPath(childPath2));

        String childPath3 = ZKPaths.makePath(childPath2, "2");

        System.out.println(client.checkExists().forPath(childPath3));
        System.out.println(childPath3);

        System.out.println(client.getChildren().forPath("/consumers/group_1/ids"));

        /*client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(childPath3, "dfadsfa".getBytes());

        System.out.println(client.checkExists().forPath(childPath1));*/

        //curatorOpts.addVNode(childPath1, "");

        //String path1 = "/consumers/group_1/owners";

        //System.out.println(curatorOpts.isPathExist(path1));

        curatorOpts.readData(childPath3);

        //更新节点
        //curatorOpts.updateNode(path,"test_update");

        //读取更新后信息
        //curatorOpts.readData(path);

        //删除节点
        //curatorOpts.deleteNode(childPath1);

        //验证是否删除
        //System.out.println(curatorOpts.isPathExist(path));
    }
}
