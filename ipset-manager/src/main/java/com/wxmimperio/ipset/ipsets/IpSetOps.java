package com.wxmimperio.ipset.ipsets;

import com.google.common.collect.Lists;
import com.jcraft.jsch.JSchException;
import com.wxmimperio.ipset.entity.Host;
import com.wxmimperio.ipset.entity.IpSet;
import com.wxmimperio.ipset.exception.SshException;
import com.wxmimperio.ipset.ssh.SshUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class IpSetOps {
    private final static Logger logger = LoggerFactory.getLogger(IpSetOps.class);

    public static void createIpSet(Host host, String name, Long maxSize) {
        Optional<Long> size = Optional.ofNullable(maxSize);
        String command;
        if (size.isPresent()) {
            command = String.format("sudo ipset create %s hash:net maxelem %s", name, size.get());
        } else {
            command = String.format("sudo ipset create %s hash:net", name);
        }
        exeSshCommand(host, command);
    }

    public static void createIpSet(Host host, String name) {
        createIpSet(host, name, null);
    }

    public static void addToIpSet(Host host, String ipSetName, String ip) {
        String command = String.format("ipset add %s %s", ipSetName, ip);
        exeSshCommand(host, command);
    }

    public static void addBatchToIpSet(Host host, String ipSetName, String ips) {
        IpSet ipSet = listIpSet(host, ipSetName);
        List<String> oldIps = ipSet.getMembers();
        logger.info(String.format("1. Already exists ip = %s", oldIps));
        List<String> needAddIps = Lists.newArrayList(ips.split(",", -1));
        logger.info(String.format("2. Need add ip = %s", needAddIps));
        List<String> diffIps = ipSet.getMembers();
        diffIps.retainAll(needAddIps);
        if (CollectionUtils.isNotEmpty(diffIps)) {
            logger.warn(String.format("IP = %s has been added", diffIps));
        }
        needAddIps.removeAll(oldIps);
        logger.info(String.format("3. Really added ip = %s", needAddIps));
        StringBuilder batchCommand = new StringBuilder();
        needAddIps.forEach(ip -> {
            String command = String.format("ipset add %s %s", ipSetName, ip);
            batchCommand.append(command).append(" && ");
        });
        if (batchCommand.length() > 0) {
            batchCommand.delete(batchCommand.length() - 4, batchCommand.length());
            exeSshCommand(host, batchCommand.toString());
        }
    }

    public static IpSet listIpSet(Host host, String ipSetName) {
        String command = String.format("ipset list %s", ipSetName);
        return new IpSet(exeSshCommand(host, command));
    }

    private static String exeSshCommand(Host host, String command) {
        String result = "";
        if (StringUtils.isNotEmpty(command)) {
            try {
                result = SshUtils.execCommandByJSch(host, command);
                logger.info(String.format("Command = %s, exe success.", command));
            } catch (IOException | JSchException | SshException e) {
                logger.error("Exe command error.", e);
            }
        }
        return result;
    }
}
