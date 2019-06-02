package com.wxmimperio.ipset.ssh;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.wxmimperio.ipset.entity.Host;
import com.wxmimperio.ipset.exception.SshException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class SshUtils {
    private final static Logger logger = LoggerFactory.getLogger(SshUtils.class);
    private final static String ENCODING = "UTF-8";

    private static Session getJSchSession(Host host) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(host.getUserName(), host.getIp(), host.getPort());
        session.setPassword(host.getPassWord());
        // //第一次访问服务器不用输入yes
        session.setConfig("StrictHostKeyChecking", "no");
        session.setTimeout(host.getTimeout());
        session.connect();
        return session;
    }

    /**
     * 1.默认方式，执行单句命令
     *
     * @param command
     * @return
     * @throws IOException
     * @throws JSchException
     */
    private static String execCommandByJSch(Host host, String command) throws IOException, JSchException, SshException {
        String result = "";
        Optional<Session> session = Optional.empty();
        Optional<ChannelExec> channelExec = Optional.empty();
        try {
            session = Optional.ofNullable(getJSchSession(host));
            if (session.isPresent()) {
                channelExec = Optional.ofNullable((ChannelExec) session.get().openChannel("exec"));
                if (channelExec.isPresent()) {
                    InputStream in = channelExec.get().getInputStream();
                    InputStream error = channelExec.get().getErrStream();
                    channelExec.get().setCommand(command);
                    channelExec.get().connect();
                    result = IOUtils.toString(in, ENCODING);
                    String errorResult = IOUtils.toString(error, ENCODING);
                    if (StringUtils.isNotEmpty(errorResult)) {
                        throw new SshException(String.format("Command = %s, exec error = %s.", command, errorResult));
                    }
                } else {
                    throw new SshException(String.format("SSH channel can not get connect, please check your host = %s.", host));
                }
            } else {
                throw new SshException(String.format("SSH session can not get connect, please check your host = %s.", host));
            }
        } finally {
            session.ifPresent(Session::disconnect);
            channelExec.ifPresent(ChannelExec::disconnect);
        }
        return result;
    }

    public static String exeSshCommand(Host host, String command) {
        String result = "";
        if (StringUtils.isNotEmpty(command)) {
            try {
                result = execCommandByJSch(host, command);
                logger.info(String.format("Command = %s, exe success.", command));
            } catch (IOException | JSchException | SshException e) {
                logger.error("Exe command error.", e);
            }
        }
        return result;
    }
}
