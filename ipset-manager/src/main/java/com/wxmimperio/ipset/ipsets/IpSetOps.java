package com.wxmimperio.ipset.ipsets;

import com.google.common.collect.Lists;
import com.wxmimperio.ipset.common.IpSetCommandSegment;
import com.wxmimperio.ipset.entity.Host;
import com.wxmimperio.ipset.entity.IpSet;
import com.wxmimperio.ipset.ssh.SshUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class IpSetOps {
    private final static Logger logger = LoggerFactory.getLogger(IpSetOps.class);

    public static void createIpSet(Host host, String name, Long maxSize) {
        Optional<Long> size = Optional.ofNullable(maxSize);
        String command;
        if (size.isPresent()) {
            command = String.format(IpSetCommandSegment.CREATE_IPSET_WITH_MAXZISE, name, size.get());
        } else {
            command = String.format(IpSetCommandSegment.CREATE_IPSET, name);
        }
        SshUtils.exeSshCommand(host, command);
    }

    public static void createIpSet(Host host, String name) {
        createIpSet(host, name, null);
    }

    public static void addToIpSet(Host host, String ipSetName, String ip) {
        String command = String.format(IpSetCommandSegment.ADD_IP_TO_IPSET, ipSetName, ip);
        SshUtils.exeSshCommand(host, command);
    }

    public static void addBatchToIpSet(Host host, String ipSetName, String ips) {
        IpSet ipSet = listIpSet(host, ipSetName);
        List<String> oldIps = ipSet.getMembers().stream().map(IpSet.Member::getIp).collect(Collectors.toList());
        logger.info(String.format("1. Already exists ip = %s", oldIps));
        List<String> needAddIps = Lists.newArrayList(ips.split(",", -1));
        logger.info(String.format("2. Need add ip = %s", needAddIps));
        List<String> diffIps = ipSet.getMembers().stream().map(IpSet.Member::getIp).collect(Collectors.toList());
        diffIps.retainAll(needAddIps);
        if (CollectionUtils.isNotEmpty(diffIps)) {
            logger.warn(String.format("IP = %s has been added", diffIps));
        }
        needAddIps.removeAll(oldIps);
        logger.info(String.format("3. Really added ip = %s", needAddIps));
        StringBuilder batchCommand = new StringBuilder();
        needAddIps.forEach(ip -> {
            String command = String.format(IpSetCommandSegment.ADD_IP_TO_IPSET, ipSetName, ip);
            batchCommand.append(command).append(" && ");
        });
        if (batchCommand.length() > 0) {
            batchCommand.delete(batchCommand.length() - 4, batchCommand.length());
            SshUtils.exeSshCommand(host, batchCommand.toString());
        }
    }

    public static void deleteFromIpSet(Host host, String ipSetName, String ip) {
        String command = String.format(IpSetCommandSegment.DELETE_IP_FROM_IPSET, ipSetName, ip);
        SshUtils.exeSshCommand(host, command);
    }

    public static IpSet listIpSet(Host host, String ipSetName) {
        String command = String.format(IpSetCommandSegment.LIST_IPSET, ipSetName);
        return new IpSet(SshUtils.exeSshCommand(host, command));
    }

    public static boolean checkIpExist(Host host, String ipSetName, String ip) {
        IpSet ipSet = listIpSet(host, ipSetName);
        return ipSet.getMembers().stream().map(IpSet.Member::getIp).collect(Collectors.toList()).contains(ip);
    }
}
