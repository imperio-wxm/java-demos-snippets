package com.wxmimperio.memory;

import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className DirectMemory.java
 * @description This is the description of DirectMemory.java
 * @createTime 2020-12-25 18:05:00
 */
public class DirectMemory {

    public static void main(String[] args) throws Exception {
        int i = 0;
        while (true) {
            ByteBuffer byteBuffer1 = ByteBuffer.allocateDirect(1024);
            if (byteBuffer1 instanceof DirectBuffer) {
                ((DirectBuffer) byteBuffer1).cleaner().clean();
            }
            i++;
            System.out.println("count = " + i);
            Thread.sleep(1000);
        }
    }
}
