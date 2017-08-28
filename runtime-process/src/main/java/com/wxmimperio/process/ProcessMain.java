package com.wxmimperio.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by weiximing.imperio on 2017/8/28.
 */
public class ProcessMain {

    public static void main(String[] args) {

        String[] cmd = {"hive", "-e", args[0]};

        // jdk 1.8
        Supplier<Stream<String>> supplier = () -> ShellUtils.processShell(cmd);

        Iterator<String> strings = supplier.get().iterator();

        System.out.println("size = " + supplier.get().count());

        while (strings.hasNext()) {
            System.out.println(strings.next());
        }
        supplier.get().close();


        System.out.println("======================================");

        Process process = ShellUtils.processShellWithoutReader(cmd);
        BufferedReader br = null;

        br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        try {
            String output = null;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process.isAlive()) {
                // jdk 1.8
                process.destroyForcibly();
            }
        }
    }
}
