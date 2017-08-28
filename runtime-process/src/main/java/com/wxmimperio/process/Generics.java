package com.wxmimperio.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiximing.imperio on 2017/8/28.
 */
public abstract class Generics {

    public abstract <T> T getResult();

    public static void main(String[] args) {
        Generics genericsTest = new GenericsFactory().createGenerics("test1");
        List<String> list = (List<String>) genericsTest.getResult();
        System.out.println(list);

        Generics genericsTest2 = new GenericsFactory().createGenerics("test2");

        Process process = (Process) genericsTest2.getResult();
        if (process instanceof Process) {
            System.out.println("This is a process");
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
                String output = null;
                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (process.isAlive()) {
                    process.destroyForcibly();
                }
            }
        }

    }

    static class GenericsTest extends Generics {
        @Override
        public <T> T getResult() {
            List<String> list = new ArrayList<>();
            list.add("dfasdfas");
            list.add("54546");
            return (T) list;
        }
    }

    static class GenericsTest2 extends Generics {
        private String processCmd;

        public GenericsTest2(String processCmd) {
            this.processCmd = processCmd;
        }

        @Override
        public <T> T getResult() {
            Process process = null;
            try {
                process = Runtime.getRuntime().exec(processCmd);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return (T) process;
        }
    }
}
