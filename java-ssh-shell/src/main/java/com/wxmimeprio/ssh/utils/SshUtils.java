package com.wxmimeprio.ssh.utils;

import com.jcraft.jsch.*;
import com.wxmimeprio.ssh.common.ChannelType;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

public class SshUtils {

    private Session session;
    private String ip;
    private Integer port;
    private String userName;
    private String passWord;

    public SshUtils(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    public SshUtils(String ip, Integer port, String userName) {
        this.ip = ip;
        this.port = port;
        this.userName = userName;
    }

    public SshUtils(String ip, Integer port, String userName, String passWord) {
        this.ip = ip;
        this.port = port;
        this.userName = userName;
        this.passWord = passWord;
    }

    public void loginAndOpenConnect() throws JSchException {
        JSch jsch = new JSch();
        // 根据用户名，主机ip，端口获取一个Session对象
        if (StringUtils.isNotEmpty(userName)) {
            session = jsch.getSession(userName, ip, port);
        } else {
            session = jsch.getSession(ip);
            session.setPort(port);
        }
        System.out.println("Session created...");
        if (StringUtils.isNotEmpty(passWord)) {
            session.setPassword(passWord);
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        config.put("userauth.gssapi-with-mic", "no");
        session.setConfig(config);
        session.setTimeout(60 * 1000);
        session.connect();
    }

    private Channel getChannel(ChannelType channelType) throws JSchException {
        Channel channel = session.openChannel(channelType.name().toLowerCase());
        channel.connect();
        System.out.println(String.format("Connected successfully to ip :%s, ftpUsername is :%s, return :%s", ip, userName, channel));
        return channel;
    }

    public int exec(String cmd, ChannelType channelType) throws Exception {
        int res = -1;
        ChannelExec channelExec = (ChannelExec) getChannel(channelType);
        channelExec.setCommand(cmd);
        System.out.println(processStream(channelExec));
        channelExec.disconnect();
        if (channelExec.isClosed()) {
            res = channelExec.getExitStatus();
        }
        return res;
    }

    private String processStream(Channel channel) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(channel.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String msg;
        while ((msg = bufferedReader.readLine()) != null) {
            sb.append(msg);
        }
        return sb.toString();
    }

    public void closeSession() {
        session.disconnect();
    }
}
