package com.wxmimperio.ipset.entity;

public class Host {
    private String ip;
    private String userName;
    private String passWord;
    private Integer port = 22;
    private Integer timeout = 60 * 60 * 1000;

    public Host(String ip, String userName) {
        this.ip = ip;
        this.userName = userName;
    }

    public Host(String ip, String userName, String passWord, Integer port) {
        this.ip = ip;
        this.userName = userName;
        this.passWord = passWord;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "Host{" +
                "ip='" + ip + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", port=" + port +
                ", timeout=" + timeout +
                '}';
    }
}
