package com.wxmimperio.shell.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ShellUtils.class);

    public static void callShell(String shellString) {
        try {
            Process process = Runtime.getRuntime().exec(shellString);
            int exitValue = process.waitFor();
            if (0 != exitValue) {
                LOG.error(String.format("Call shell = %s failed. Error code is %s", shellString, exitValue));
            }
            LOG.info(String.format("Call shell = %s success.", shellString));
        } catch (Exception e) {
            LOG.error(String.format("Call shell = %s failed.", shellString), e);
        }
    }
}
