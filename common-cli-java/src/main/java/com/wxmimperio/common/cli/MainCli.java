package com.wxmimperio.common.cli;

/**
 * Created by weiximing.imperio on 2017/2/14.
 */
public class MainCli {
    public static void main(String args[]) {
        String[] cliArgs = {
                "-h", "help",
                "-host", "192.168.1.1",
                "-p", "9092",
                "-Dkey2=value2",
                "-d",
                "yyyy-MM-dd HH:mm:ss"
        };

        OptionsUtils.getOptions(cliArgs);

    }
}
