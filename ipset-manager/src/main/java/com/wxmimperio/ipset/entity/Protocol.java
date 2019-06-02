package com.wxmimperio.ipset.entity;

public enum Protocol {
    /**
     * UDP\TCP
     */
    UDP, TCP;

    public static boolean check(String info) {
        return info.contains(UDP.name().toLowerCase()) || info.contains(TCP.name().toLowerCase());
    }
}
