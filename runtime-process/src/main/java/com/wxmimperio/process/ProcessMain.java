package com.wxmimperio.process;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by weiximing.imperio on 2017/8/28.
 */
public class ProcessMain {

    public static void main(String[] args) {

        String[] cmd = {"hive", "-e", args[0]};

        Supplier<Stream<String>> supplier = () -> ShellUtils.processShell(cmd);

        Iterator<String> strings = supplier.get().iterator();

        System.out.println("size = " + supplier.get().count());

        while (strings.hasNext()) {
            System.out.println(strings.next());
        }
    }
}
