package com.wxmimperio.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by weiximing.imperio on 2017/8/28.
 */
public class ShellUtils {

    public static Stream<String> processShell(String[] processCmd) {

        Stream<String> resultList = new ArrayList<String>().stream();
        Process process = null;
        BufferedReader br = null;
        try {
            process = Runtime.getRuntime().exec(processCmd);
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            resultList = br.lines();

            int rs = process.waitFor();

            if (0 != rs) {
                System.out.println("Exec error.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
        return resultList;
    }
}
