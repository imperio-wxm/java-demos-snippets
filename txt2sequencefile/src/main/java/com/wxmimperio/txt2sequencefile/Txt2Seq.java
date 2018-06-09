package com.wxmimperio.txt2sequencefile;

public class Txt2Seq {

    public static void main(String[] args) throws Exception {
        HDFSRunner.sequenceFileWrite(args[0], args[1]);
    }
}
