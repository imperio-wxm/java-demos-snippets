package com.wxmimperio.process;

import javax.xml.bind.SchemaOutputResolver;
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
    private static final int BATCH_SIZE = 2;

    public static void main(String[] args) {

        String[] cmd = {"hive", "-e", args[0]};

        // jdk 1.8
        Supplier<Stream<String>> supplier = () -> ShellUtils.processShell(cmd);

        Iterator<String> strings = supplier.get().iterator();

        System.out.println("size = " + supplier.get().count());

        while (strings.hasNext()) {
            //System.out.println(strings.next());
            strings.next();
        }
        supplier.get().close();


        System.out.println("======================================");

        Process process = ShellUtils.processShellWithoutReader(cmd);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = null;
            long i = 1L;
            while ((output = br.readLine()) != null) {
                if (i % BATCH_SIZE == 0) {
                    System.out.println("current size = " + i);
                }
                System.out.println(output);
                i++;
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
