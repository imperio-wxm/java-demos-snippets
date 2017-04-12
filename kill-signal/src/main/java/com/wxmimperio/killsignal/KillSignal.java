package com.wxmimperio.killsignal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * Created by weiximing.imperio on 2017/4/12.
 */
public class KillSignal implements SignalHandler {
    private static final Logger LOG = LoggerFactory.getLogger(KillSignal.class);

    boolean flags = true;

    public KillSignal() {
    }

    public static void main(String[] args) {
        KillSignal killSignal = new KillSignal();
        // TERM（kill -15）、USR1（kill -10）、USR2（kill -12）、USR2（kill -9）
        //Signal.handle(new Signal("USR1"), killSignal);
        Signal.handle(new Signal("TERM"), killSignal);
        Signal.handle(new Signal("USR2"), killSignal);
        //Signal.handle(new Signal("KILL"), killSignal);
        killSignal.sleepTime();
    }

    @Override
    public void handle(Signal signal) {
        LOG.info("Signal [" + signal.getName() + "] is received, stopServer soon...");
        stopProject();
        LOG.info("Stop successfully.");
    }

    private void sleepTime() {
        while (flags) {
            try {
                Thread.sleep(1000);
                LOG.info("While in!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOG.info("While out!");
    }

    /**
     * 安全的关闭HttpServer监听服务
     */
    private void stopProject() {
        flags = false;
        LOG.info("Stop!");
    }
}
