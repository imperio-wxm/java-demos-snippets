package com.wxmimperio.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiximing.imperio on 2017/8/28.
 */
public abstract class GenericsMethod<T> {

    public abstract <T> T getResult();

    public abstract void exec(T param);

    public static void main(String[] args) {
        GenericsMethod genericsTest = new GenericsFactory().createGenerics("test1");
        List<String> list = (List<String>) genericsTest.getResult();
        genericsTest.exec(list);

        GenericsMethod genericsTest2 = new GenericsFactory().createGenerics("test2");

        Process process = (Process) genericsTest2.getResult();
        genericsTest2.exec(process);

    }

    static class GenericsTest<T> extends GenericsMethod<T> {
        @Override
        public <T> T getResult() {
            List<String> list = new ArrayList<>();
            list.add("dfasdfas");
            list.add("54546");
            return (T) list;
        }

        @Override
        public void exec(T param) {
            System.out.println((List) param);
        }
    }

    static class GenericsTest2<T> extends GenericsMethod<T> {
        private String processCmd;

        public GenericsTest2(String processCmd) {
            this.processCmd = processCmd;
        }

        @Override
        public Process getResult() {
            Process process = null;
            try {
                process = Runtime.getRuntime().exec(processCmd);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return process;
        }

        @Override
        public void exec(T processParam) {
            if (processParam instanceof Process) {
                Process process = (Process) processParam;
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
    }
}
