package com.wxmimperio.txt2sequencefile;

public class SeqToSeq {

    public static void main(String[] args) {
        HDFSRunner.sequenceFileWriteTosql(args[0], args[1], args[2]);
    }
}
