package com.wxmimperio.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by weiximing.imperio on 2017/9/15.
 */
public class FileWriterOps {

    public static void appendFile(String fileName, List<String> buffer) {
        try {
            checkFileExistAndCreate(fileName);
            FileWriter writer = new FileWriter(fileName, true);
            for (String content : buffer) {
                writer.write(content + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkFileExistAndCreate(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
