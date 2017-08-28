package com.wxmimperio.process;

import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Created by weiximing.imperio on 2017/8/28.
 */
public class ProcessMain {

    public static void main(String[] args) {
        Stream streams = ShellUtils.processShell(args);

        Iterator<String> strings = streams.iterator();

        while (strings.hasNext()) {
            System.out.println(strings.next());
        }
    }
}
