package com.wxmimperio.ipset.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IpSet {
    private String name;
    private String type;
    private Integer reVersion;
    private String hearer;
    private Long sizeInMemory;
    private String references;
    private List<String> members;

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
                members = new ArrayList<>();
                int start = infoList.indexOf("Members:") + 1;
                for (; start < infoList.size(); start++) {
                    if (StringUtils.isNotEmpty(infoList.get(start))) {
                        members.add(infoList.get(start).trim());
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

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
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
}
