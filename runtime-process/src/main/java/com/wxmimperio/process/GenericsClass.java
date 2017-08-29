package com.wxmimperio.process;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiximing.imperio on 2017/8/29.
 */
public abstract class GenericsClass<T> {
    public abstract List<T> getResult();

    public abstract void exec(T param);

    public static void main(String[] args) {
        GenericsClass genericsClassList = new GenericsFactory().createGenericsClass("list");
        List<String> list = genericsClassList.getResult();
        genericsClassList.exec(list);

        GenericsClass genericsClassProcess = new GenericsFactory().createGenericsClass("process");
        List<Process> processList = genericsClassProcess.getResult();
        genericsClassProcess.exec(processList);
    }

    static class GenericsClassList<T> extends GenericsClass<T> {

        @Override
        public List<T> getResult() {
            List<String> list = new ArrayList<>();
            list.add("dsfasdfa");
            list.add("5464");
            return (List<T>) list;
        }

        @Override
        public void exec(T param) {
            System.out.println("This is a list.\n" + param);
        }
    }

    static class GenericsClassProcess<T> extends GenericsClass<T> {

        private String processCmd;

        public GenericsClassProcess(String processCmd) {
            this.processCmd = processCmd;
        }

        @Override
        public List<T> getResult() {
            List<Process> list = new ArrayList<>();
            try {
                Process process = Runtime.getRuntime().exec(processCmd);
                list.add(process);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return (List<T>) list;
        }

        @Override
        public void exec(T param) {
            List<Process> processes = (List<Process>) param;
            for (Process process : processes) {
                System.out.println("This is a process");
                getProcessInput(process);
            }
        }

        private void getProcessInput(Process process) {
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
