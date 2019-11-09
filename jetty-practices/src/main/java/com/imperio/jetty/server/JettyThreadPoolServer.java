package com.imperio.jetty.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class JettyThreadPoolServer {

    public static void main(String[] args) throws Exception {
        int maxThreads = 1000;
        int minThreads = 50;
        QueuedThreadPool queuedThreadPool = new QueuedThreadPool(maxThreads,minThreads);
        Server server = new Server(queuedThreadPool) {
            @Override
            protected void doStop() throws Exception {
                super.doStop();
            }
        };
        server.start();
        System.out.println("total = " + server.getThreadPool().getThreads());
        System.out.println("idle = " + server.getThreadPool().getIdleThreads());
    }
}
