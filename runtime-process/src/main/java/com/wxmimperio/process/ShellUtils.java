package com.wxmimperio.process;

import java.io.BufferedReader;
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

            /*String output = null;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println("error = " + line);
            }*/

            int rs = process.waitFor();

            if (0 != rs) {
                System.out.println("Exec error.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static Process processShellWithoutReader(String[] processCmd) {

        Process process = null;
        try {
            process = Runtime.getRuntime().exec(processCmd);

            int rs = process.waitFor();

            if (0 != rs) {
                System.out.println("Exec error.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return process;
    }
}
