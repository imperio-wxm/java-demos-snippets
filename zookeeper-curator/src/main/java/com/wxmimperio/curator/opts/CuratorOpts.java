package com.wxmimperio.curator.opts;

import com.wxmimperio.curator.connect.CuratorConnect;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * Created by weiximing.imperio on 2016/11/14.
 */
public class CuratorOpts {
    private CuratorFramework client;

    public CuratorOpts() {
        CuratorConnect curatorConnect = new CuratorConnect();
        this.client = curatorConnect.getCuratorConnect();
    }

    public void addNode(String path, String value) throws Exception {
        //创建节点
        this.client.create()
                .creatingParentsIfNeeded()        //对节点路径上没有的节点进行创建
                .withMode(CreateMode.EPHEMERAL)   //临时节点
                .withACL((ZooDefs.Ids.OPEN_ACL_UNSAFE))   //递归创建
                .forPath(path, value.getBytes());  //节点路径，节点的值
        System.out.println(path);
    }

    public void readData(String path) throws Exception {
        List<String> topicList = this.client.getChildren().forPath(path);
        for (String topic : topicList) {
            System.out.println(topic);
        }
        byte[] newData = this.client.getData().forPath(path);
        System.out.println("Data = " + new String(newData));
    }

    public void updateNode(String path, String updateValue) throws Exception {
        Stat stat2 = new Stat();
        byte[] theValue2 = this.client.getData().storingStatIn(new Stat()).forPath(path);
        System.out.println(new String(theValue2));
        this.client.setData()
                .withVersion(stat2.getVersion())  //版本校验，与当前版本不一致则更新失败,-1则无视版本信息进行更新
                .forPath(path, updateValue.getBytes());
    }

    public void deleteNode(String path) throws Exception {
        //删除节点
        this.client.delete()
                .guaranteed()      //删除失败，则客户端持续删除，直到节点删除为止
                .deletingChildrenIfNeeded()   //删除相关子节点
                .withVersion(-1)    //无视版本，直接删除
                .forPath(path);
    }

    public boolean isPathExist(String path) throws Exception {
        //判断节点是否存在(存在返回节点信息，不存在则返回null)
        Stat s = client.checkExists().forPath(path);
        return s != null;
    }
}
