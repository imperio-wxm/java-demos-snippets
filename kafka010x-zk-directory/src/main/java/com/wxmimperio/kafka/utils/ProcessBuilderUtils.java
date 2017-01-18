package com.wxmimperio.kafka.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by weiximing.imperio on 2016/9/1.
 */

public class ProcessBuilderUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessBuilderUtils.class);

    public ProcessBuilderUtils() {
    }

    public boolean executeResult(ProcessBuilder pb) {
        StringBuffer errorSb;
        StringBuffer inputSb;
        Map<String, Object> result;
        //shell执行状态
        int runningStatus = -1;

        try {
            result = this.outputResult(pb);
            runningStatus = (int) result.get("runningStatus");
            errorSb = (StringBuffer) result.get("errorOutput");
            inputSb = (StringBuffer) result.get("inputOutput");
            LOG.info(errorSb.toString() + "\n" + inputSb.toString());
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return runningStatus == 0;
    }

    public Map<String, Object> outputResult(ProcessBuilder pb) throws InterruptedException, IOException {
        Process process = pb.start();
        int runningStatus;
        Map<String, Object> result = new ConcurrentHashMap<String, Object>();

        //防止shell阻塞，其他线程做输出
        //errorOutput得到日志信息
        //inputOutput得到hive 结果
        InputIOThreadHandler inputHandler = new InputIOThreadHandler(process.getInputStream());
        ErrorIOThreadHandler errorHandler = new ErrorIOThreadHandler(process.getErrorStream());
        new Thread(inputHandler, "ProcessBuilderInput").start();
        new Thread(errorHandler, "ProcessBuilderError").start();

        runningStatus = process.waitFor();

        result.put("runningStatus", runningStatus);
        result.put("errorOutput", errorHandler.getOutput());
        result.put("inputOutput", inputHandler.getOutput());
        return result;
    }

    /**
     * 获得hive 结果输出
     */
    private static class InputIOThreadHandler implements Runnable {
        private InputStream inputStream;
        private StringBuffer inputOutput = new StringBuffer();

        InputIOThreadHandler(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            appendBuffer(inputOutput, inputStream);
        }

        StringBuffer getOutput() {
            return inputOutput;
        }
    }

    /**
     * 获得hive 日志输出
     */
    private static class ErrorIOThreadHandler implements Runnable {
        private InputStream errorStream;
        private StringBuffer errorOutput = new StringBuffer();

        ErrorIOThreadHandler(InputStream errorStream) {
            this.errorStream = errorStream;
        }

        @Override
        public void run() {
            appendBuffer(errorOutput, errorStream);
        }

        StringBuffer getOutput() {
            return errorOutput;
        }
    }

    private static void appendBuffer(StringBuffer sb, InputStream stream) {
        BufferedReader bufferedReader = null;
        String line;
        //获取shell输出
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(stream));
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            LOG.error("BufferedReader read error");
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                LOG.error("BufferedReader closed error");
                e.printStackTrace();
            }
        }
    }
}
