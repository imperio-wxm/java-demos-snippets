package com.wxmimperio.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

public class MultiFileLock {

    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                writeFile();
            }
        }).start();

        Thread.sleep(3000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                writeFile();
            }
        }).start();
    }

    public static void writeFile() {
        FileChannel channel = null;
        FileLock lock = null;
        try (RandomAccessFile randomAccessFile = new RandomAccessFile("test.txt", "rw")) {
            channel = randomAccessFile.getChannel();
            lock = channel.tryLock();
            if(lock == null) {
                throw new RuntimeException("can not get file process...");
            }
            randomAccessFile.seek(0);
            for (int i = 0; i < 10; i++) {
                String str = "456465\n";
                randomAccessFile.writeBytes(str);
                System.out.println(str);
                Thread.sleep(1000);
            }
        } catch (OverlappingFileLockException e) {
            e.printStackTrace();
            System.out.println("多线程读取独占锁文件");
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (lock != null) {
                    lock.release();
                }
                if (channel != null) {
                    channel.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void readFile() {
        FileLock lock = null;
        try (RandomAccessFile randomAccessFile = new RandomAccessFile("test.txt", "rw");
             FileChannel channel = randomAccessFile.getChannel()) {
            lock = channel.tryLock();
            if (lock == null) {
                throw new RuntimeException("Can not get file process....");
            }
            randomAccessFile.length();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != lock) {
                try {
                    lock.release();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
