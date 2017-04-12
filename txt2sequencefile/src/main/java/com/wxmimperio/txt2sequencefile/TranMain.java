package com.wxmimperio.txt2sequencefile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by weiximing.imperio on 2017/4/12.
 */
public class TranMain {
    private static final Logger LOG = LoggerFactory.getLogger(TranMain.class);

    public static void main(String[] args) {
        String[] textFiles = PropManager.getInstance().getPropertyByString("text.files").split(",", -1);
        String[] sequenceFiles = PropManager.getInstance().getPropertyByString("sequence.files").split(",", -1);

        if (textFiles.length != sequenceFiles.length) {
            LOG.error("text files length is not match sequence files length");
            System.exit(0);
        }

        for (int i = 0; i < textFiles.length; i++) {
            HDFSRunner.txtFile2SequenceFile(textFiles[i], sequenceFiles[i]);
            LOG.info("===================================");
            LOG.info("text files path=" + textFiles[i]);
            LOG.info("sequence files path=" + sequenceFiles[i]);
        }
    }
}
