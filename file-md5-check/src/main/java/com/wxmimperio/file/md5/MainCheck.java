package com.wxmimperio.file.md5;

import com.wxmimperio.file.cli.OptionsUtils;

import java.io.File;
import java.util.Map;

/**
 * Created by weiximing.imperio on 2017/2/15.
 */
public class MainCheck {

    public static void main(String args[]) {

        Map<String, String[]> pathMap = OptionsUtils.getOptions(args);

        String[] inputPath = pathMap.get("inputPath");
        String[] outputPath = pathMap.get("outputPath");

        if (!pathMap.isEmpty()) {
            if (inputPath.length != outputPath.length) {
                System.out.println("输入、输出文件路径个数不同");
            } else if (inputPath.length == 0 || outputPath.length == 0) {
                System.out.println("输入、输出文件路径为空");
            } else {
                for (int i = 0; i < inputPath.length; i++) {
                    File inFile = new File(inputPath[i]);
                    File outFile = new File(outputPath[i]);

                    if (inFile.exists() || outFile.exists()) {
                        if (FileMD5.getFileMD5(inFile).equals(FileMD5.getFileMD5(outFile))) {
                            System.out.println("文件：" + inFile.getPath() + " 和文件：" + outFile.getPath() + " MD5相同");
                        } else {
                            System.out.println("文件：" + inFile.getPath() + " 和文件：" + outFile.getPath() + " MD5不相同");
                        }
                    } else {
                        System.out.println("文件：" + inFile.getPath() + " 或文件：" + outFile.getPath() + " 不存在");
                    }
                }
            }
        }
    }
}
