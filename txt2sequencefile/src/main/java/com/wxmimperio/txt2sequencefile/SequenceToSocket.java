package com.wxmimperio.txt2sequencefile;

public class SequenceToSocket {

    public static void main(String[] args) throws Exception {
        String inputPath = args[0];
        String ip = args[1];
        HDFSRunner.sequenceFileWriterSocket(inputPath, ip);
    }
}
