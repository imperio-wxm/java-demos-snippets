package com.wxmimperio.ipset;

import com.jcraft.jsch.JSchException;
import com.wxmimperio.ipset.entity.Host;
import com.wxmimperio.ipset.entity.IpSet;
import com.wxmimperio.ipset.ipsets.IpSetOps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class IpSetManager {
    private final static Logger logger = LoggerFactory.getLogger(IpSetManager.class);

    public static void main(String[] args) throws IOException, JSchException {
        Host host = new Host("192.168.1.110", "root", "root", 22);

        String ipSetName = "test_ipset";

        // create ipset
        // IpSetOps.createIpSet(host,"test_ipset");

        // add ip to ipset
        //IpSetOps.addToIpSet(host, ipSetName, "127.0.0.2");

        // add ip batch
        IpSetOps.addBatchToIpSet(host, ipSetName, "127.0.3.0,127.0.2.1,127.0.3.3,127.0.3.4");

        // list ipset
        IpSet ipSet = IpSetOps.listIpSet(host, ipSetName);
        System.out.println(ipSet);
        System.out.println(ipSet.getMembers());
    }
}
