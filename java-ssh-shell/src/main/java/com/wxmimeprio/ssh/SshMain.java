package com.wxmimeprio.ssh;

import com.wxmimeprio.ssh.common.ChannelType;
import com.wxmimeprio.ssh.utils.SshUtils;

public class SshMain {

    public static void main(String[] args) throws Exception {
        String ip = args[0];
        Integer port = Integer.parseInt(args[1]);
        String user = args[2];
        String cmd = args[3];

        SshUtils sshUtils = new SshUtils(ip, port, user);
        sshUtils.loginAndOpenConnect();
        sshUtils.exec(cmd, ChannelType.EXEC);
        sshUtils.closeSession();
    }
}
