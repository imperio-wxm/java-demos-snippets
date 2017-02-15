package com.wxmimperio.file.md5;

import java.io.File;

/**
 * Created by weiximing.imperio on 2017/2/15.
 */
public class MainCheck {

    public static void main(String args[]) {
        /**
         * args[0] file path1
         * args[1] file path2
         * 两个文件md5相同则表示文件内容一样
         */

        File file1 = new File(args[0]);
        File file2 = new File(args[1]);

        if (file1.exists() || file2.exists()) {
            if (FileMD5.getFileMD5(file1).equals(FileMD5.getFileMD5(file2))) {
                System.out.println("文件：" + file1.getPath() + " 和文件：" + file2.getPath() + " MD5相同");
            } else {
                System.out.println("文件：" + file1.getPath() + " 和文件：" + file2.getPath() + " MD5不相同");
            }
        } else {
            System.out.println("文件：" + file1.getPath() + " 或文件：" + file2.getPath() + " 不存在");
        }
    }
}
