package com.wxmimperio.kafka.main;

import com.wxmimperio.kafka.utils.ProcessBuilderUtils;

import java.io.*;

/**
 * Created by weiximing.imperio on 2017/1/18.
 */
public class JavaCmdMain {

    public static void main(String args[]) {
        dirOpt();
    }



    public static void dirOpt() {
        System.out.println("------------------dirOpt()--------------------");
        Process process;

        try {

            String groupCmd = "E:\\Env_SoftWare\\kafka_2.10-0.10.1.0\\kafka_2.10-0.10.1.0\\bin\\windows\\kafka-simple-consumer-shell.bat --topic __consumer_offsets --partition 49 --broker-list 192.168.18.74:9092 --fetchsize 1 --max-messages 1 --formatter 'kafka.coordinator.GroupMetadataManager\\$OffsetsMessageFormatter'";

            String cmd = "E:\\Env_SoftWare\\kafka_2.10-0.10.1.0\\kafka_2.10-0.10.1.0\\bin\\windows\\kafka-run-class.bat kafka.admin.ConsumerGroupCommand -new-consumer -describe -group group_1 --bootstrap-server 192.168.18.74:9092";
            //执行命令
            process = Runtime.getRuntime().exec(groupCmd);
            //取得命令结果的输出流
            InputStream fis = process.getInputStream();
            //用一个读输出流类去读
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            //逐行读取输出到控制台
            while ((line = br.readLine()) != null) {
                System.out.println(line.trim());
            }

            InputStream error = process.getErrorStream();
            //用一个读输出流类去读
            BufferedReader errorBuffer = new BufferedReader(new InputStreamReader(error));
            String errorLine;
            //逐行读取输出到控制台
            while ((errorLine = errorBuffer.readLine()) != null) {
                System.out.println(errorLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
