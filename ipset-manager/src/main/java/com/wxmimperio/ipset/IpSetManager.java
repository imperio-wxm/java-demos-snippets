package com.wxmimperio.ipset;

import com.google.common.collect.Lists;
import com.jcraft.jsch.JSchException;
import com.wxmimperio.ipset.entity.Host;
import com.wxmimperio.ipset.entity.IpSet;
import com.wxmimperio.ipset.entity.Protocol;
import com.wxmimperio.ipset.ipsets.IpSetOps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class IpSetManager {
    private final static Logger logger = LoggerFactory.getLogger(IpSetManager.class);

    public static void main(String[] args) throws IOException, JSchException {
        Host host = new Host("192.168.1.110", "root", "root", 22);

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        String ipSetName = "test_ipset";

        String ipSetNameProtocol = "test_ipset_Protocol";

        // create ipset
        // IpSetOps.createIpSet(host,"test_ipset");

        // add ip to ipset
        //IpSetOps.addToIpSet(host, ipSetName, "127.0.0.2");

        // add ip batch
        // IpSetOps.addBatchToIpSet(host, ipSetName, "127.0.3.0,127.0.2.1,127.0.3.3,127.0.3.4");

        // delete ip
        //IpSetOps.deleteFromIpSet(host, ipSetName, "127.0.3.0");

        // check ip exists
        //System.out.println(IpSetOps.checkIpExist(host, ipSetName,"127.0.3.0"));

        // delte ip batch
        // IpSetOps.deleteBatchFromIpSet(host, ipSetNameProtocol, new IpSet.Member("111.0.0.1"));

        // createIpSetWithProtocol
        // IpSetOps.createIpSetWithProtocol(host, ipSetNameProtocol);

        // addToIpSetWithProtocol
        //IpSetOps.addToIpSetWithProtocol(host, ipSetNameProtocol, new IpSet.Member("111.0.0.1", Protocol.TCP, 5240));

        //
        /*List<Future> futures = Lists.newArrayList();
        for (int i = 0; i < 20; i++) {
            futures.add(executorService.submit(new Runnable() {
                @Override
                public void run() {
                    IpSetOps.addToIpSet(host, ipSetName, new IpSet.Member("137.0.0." + new Random().nextInt(100)));
                }
            }));
        }

        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });*/


        // deleteFromIpSetWithProtocol
        //IpSetOps.deleteFromIpSetWithProtocol(host, ipSetNameProtocol, new IpSet.Member("111.0.0.1", Protocol.TCP, 5240));

        // addBatchToIpSetWithProtocol
        List<IpSet.Member> memberList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            memberList.add(new IpSet.Member("127.0.1." + i, Protocol.TCP, i));
        }
        IpSetOps.batchAddToIpSet(host, ipSetNameProtocol, memberList);

        //IpSetOps.addToIpSet(host, ipSetNameProtocol, new IpSet.Member("127.0.1.10", Protocol.TCP, 10));

        // list ipset
        IpSet ipSet = IpSetOps.listIpSet(host, ipSetNameProtocol);
        System.out.println(ipSet.getMembers());

        // save ipset
        String path = "/home/wxmimperio/ipset.txt";
        //System.out.println(IpSetOps.saveIpsetToFile(host, ipSetNameProtocol, path));

        //IpSetOps.destoryIpset(host, ipSetNameProtocol);

        //IpSetOps.restoreIpsetFromFile(host, path);
    }
}
