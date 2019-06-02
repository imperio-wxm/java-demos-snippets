package com.wxmimperio.ipset.entity;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class IpSet {
    private String name;
    private String type;
    private Integer reVersion;
    private String hearer;
    private Long sizeInMemory;
    private String references;
    private List<Member> members;

    public IpSet(String info) {
        make(info);
    }

    private void make(String info) {
        List<String> infoList = Arrays.asList(info.split("\n", -1));
        infoList.forEach(line -> {
            if (line.startsWith("Name")) {
                this.name = line.split(": ")[1].trim();
            }
            if (line.startsWith("Type")) {
                this.type = line.split(": ")[1].trim();
            }
            if (line.startsWith("Revision")) {
                this.reVersion = Integer.parseInt(line.split(": ")[1].trim());
            }
            if (line.startsWith("Header")) {
                this.hearer = line.split(": ")[1].trim();
            }
            if (line.startsWith("Size in memory")) {
                this.sizeInMemory = Long.parseLong(line.split(": ")[1].trim());
            }
            if (line.startsWith("References")) {
                this.references = line.split(": ")[1].trim();
            }
            if (line.startsWith("Members")) {
                members = Lists.newArrayList();
                int start = infoList.indexOf("Members:") + 1;
                for (; start < infoList.size(); start++) {
                    if (StringUtils.isNotEmpty(infoList.get(start))) {
                        String ipInfo = infoList.get(start).trim();
                        if (Protocol.check(ipInfo)) {
                            // 111.0.0.1,tcp:5240
                            String[] lineInfo = ipInfo.split(",", -1);
                            String[] protocolInfo = lineInfo[1].split(":", -1);
                            members.add(new Member(lineInfo[0], Protocol.valueOf(protocolInfo[0].toUpperCase()), Integer.parseInt(protocolInfo[1])));
                        } else {
                            members.add(new Member(ipInfo));
                        }
                    }
                }
            }
        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getReVersion() {
        return reVersion;
    }

    public void setReVersion(Integer reVersion) {
        this.reVersion = reVersion;
    }

    public String getHearer() {
        return hearer;
    }

    public void setHearer(String hearer) {
        this.hearer = hearer;
    }

    public Long getSizeInMemory() {
        return sizeInMemory;
    }

    public void setSizeInMemory(Long sizeInMemory) {
        this.sizeInMemory = sizeInMemory;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "IpSet{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", reVersion=" + reVersion +
                ", hearer='" + hearer + '\'' +
                ", sizeInMemory=" + sizeInMemory +
                ", references='" + references + '\'' +
                ", members=" + members +
                '}';
    }

    public static class Member {
        private String ip;
        private Protocol protocol;
        private Integer port;

        public Member(String ip) {
            this.ip = ip;
        }

        public Member(String ip, Protocol protocol, Integer port) {
            this.ip = ip;
            this.protocol = protocol;
            this.port = port;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Protocol getProtocol() {
            return protocol;
        }

        public void setProtocol(Protocol protocol) {
            this.protocol = protocol;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        @Override
        public String toString() {
            return "Member{" +
                    "ip='" + ip + '\'' +
                    ", protocol=" + protocol +
                    ", port=" + port +
                    '}';
        }

        public boolean withProtocol() {
            return null != protocol && null != port;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Member member = (Member) o;
            return Objects.equals(ip, member.ip) &&
                    protocol == member.protocol &&
                    Objects.equals(port, member.port);
        }

        @Override
        public int hashCode() {
            return Objects.hash(ip, protocol, port);
        }
    }
}
