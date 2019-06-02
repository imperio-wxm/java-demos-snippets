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

public class IpSetOps {
    private final static Logger logger = LoggerFactory.getLogger(IpSetOps.class);

    public static void createIpSet(Host host, String name, Long maxSize, Boolean withProtocol) {
        Optional<Long> size = Optional.ofNullable(maxSize);
        String command;
        if (size.isPresent()) {
            if (withProtocol) {
                command = String.format(IpSetCommandSegment.CREATE_IPSET_WITH_PROTOCAL_MAXZISE, name, size.get());
            } else {
                command = String.format(IpSetCommandSegment.CREATE_IPSET_WITH_MAXZISE, name, size.get());
            }
        } else {
            if (withProtocol) {
                command = String.format(IpSetCommandSegment.CREATE_IPSET_WITH_PROTOCAL, name);
            } else {
                command = String.format(IpSetCommandSegment.CREATE_IPSET, name);
            }
        }
        SshUtils.exeSshCommand(host, command);
    }


    public static void createIpSet(Host host, String name, Boolean withProtocol) {
        createIpSet(host, name, null, withProtocol);
    }

    public static void addToIpSet(Host host, String ipSetName, IpSet.Member member) {
        String command;
        if (member.withProtocol()) {
            command = String.format(IpSetCommandSegment.ADD_IP_TO_IPSET_WITH_PROTOCAL, ipSetName, member.getIp(), member.getProtocol(), member.getPort());
        } else {
            command = String.format(IpSetCommandSegment.ADD_IP_TO_IPSET, ipSetName, member.getIp());
        }
        SshUtils.exeSshCommand(host, command);
    }

    public static void batchAddToIpSet(Host host, String ipSetName, List<IpSet.Member> members) {
        List<IpSet.Member> oldMembers = listIpSet(host, ipSetName).getMembers();
        List<IpSet.Member> alreadyExist = Lists.newArrayList(oldMembers);
        alreadyExist.retainAll(members);
        if (CollectionUtils.isNotEmpty(alreadyExist)) {
            logger.warn(String.format("IP = %s has been added", alreadyExist));
        }
        StringBuilder batchCommand = new StringBuilder();
        members.forEach(member -> {
            String command;
            if (member.withProtocol()) {
                command = String.format(IpSetCommandSegment.ADD_IP_TO_IPSET_WITH_PROTOCAL, ipSetName, member.getIp(), member.getProtocol(), member.getPort());
            } else {
                command = String.format(IpSetCommandSegment.ADD_IP_TO_IPSET, ipSetName, member.getIp());
            }
            batchCommand.append(command).append(" && ");
        });
        if (batchCommand.length() > 0) {
            batchCommand.delete(batchCommand.length() - 4, batchCommand.length());
            SshUtils.exeSshCommand(host, batchCommand.toString());
        }
    }

    public static void batchDeleteFromIpSet(Host host, String ipSetName, List<IpSet.Member> members) {
        List<IpSet.Member> oldMembers = listIpSet(host, ipSetName).getMembers();
        List<IpSet.Member> notExist = Lists.newArrayList(members);
        notExist.removeAll(oldMembers);
        if (CollectionUtils.isNotEmpty(notExist)) {
            logger.warn(String.format("IP = %s not exists", notExist));
        }
        StringBuilder batchCommand = new StringBuilder();
        members.forEach(member -> {
            String command;
            if (member.withProtocol()) {
                command = String.format(IpSetCommandSegment.DELETE_IP_FROM_IPSET_WITH_PROTOCAL, ipSetName, member.getIp(), member.getProtocol(), member.getPort());
            } else {
                command = String.format(IpSetCommandSegment.DELETE_IP_FROM_IPSET, ipSetName, member.getIp());
            }
            batchCommand.append(command).append(" && ");
        });
        if (batchCommand.length() > 0) {
            batchCommand.delete(batchCommand.length() - 4, batchCommand.length());
            SshUtils.exeSshCommand(host, batchCommand.toString());
        }
    }

    public static void deleteFromIpSet(Host host, String ipSetName, IpSet.Member member) {
        String command;
        if (member.withProtocol()) {
            command = String.format(IpSetCommandSegment.DELETE_IP_FROM_IPSET_WITH_PROTOCAL, ipSetName, member.getIp(), member.getProtocol(), member.getPort());
        } else {
            command = String.format(IpSetCommandSegment.DELETE_IP_FROM_IPSET, ipSetName, member.getIp());
        }
        SshUtils.exeSshCommand(host, command);
    }

    public static IpSet listIpSet(Host host, String ipSetName) {
        String command = String.format(IpSetCommandSegment.LIST_IPSET, ipSetName);
        return new IpSet(SshUtils.exeSshCommand(host, command));
    }

    public static boolean checkIpExist(Host host, String ipSetName, IpSet.Member member) {
        IpSet ipSet = listIpSet(host, ipSetName);
        return ipSet.getMembers().contains(member);
    }

    public static String saveIpsetToFile(Host host, String ipSetName, String path) {
        String command = String.format(IpSetCommandSegment.SAVE_IPSET, ipSetName, path);
        return SshUtils.exeSshCommand(host, command);
    }

    public static void restoreIpsetFromFile(Host host, String path) {
        String command = String.format(IpSetCommandSegment.RESTORE_IPSET, path);
        SshUtils.exeSshCommand(host, command);
    }

    public static void destoryIpset(Host host, String ipSetName) {
        String command = String.format(IpSetCommandSegment.DESTROY_IPSET, ipSetName);
        SshUtils.exeSshCommand(host, command);
    }
}
