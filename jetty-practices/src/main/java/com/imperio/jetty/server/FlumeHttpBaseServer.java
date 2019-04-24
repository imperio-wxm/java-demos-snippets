package com.imperio.jetty.server;

import com.imperio.jetty.servlet.FlumeHttpBaseServlet;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlumeHttpBaseServer {

    private static final Logger LOG = LoggerFactory.getLogger(FlumeHttpBaseServer.class);
    private static Server srv;

    public static void main(String[] args) {

        if (srv == null) {
            QueuedThreadPool threadPool = new QueuedThreadPool();
            threadPool.setMaxThreads(10);
            threadPool.setMinThreads(2);
            threadPool.setIdleTimeout(600000);
            srv = new Server(threadPool);
        }

        HttpConfiguration httpConfiguration = new HttpConfiguration();
        httpConfiguration.addCustomizer(new SecureRequestCustomizer());

        ServerConnector connector = new ServerConnector(srv, new HttpConnectionFactory(httpConfiguration));
        /*
        SO_REUSEADDR可以用在以下四种情况下
        1、当有一个有相同本地地址和端口的socket1处于TIME_WAIT状态时，而你启
        动的程序的socket2要占用该地址和端口，你的程序就要用到该选项。
        2、SO_REUSEADDR允许同一port上启动同一服务器的多个实例(多个进程)。但
        每个实例绑定的IP地址是不能相同的。在有多块网卡或用IP Alias技术的机器可
        以测试这种情况。
        3、SO_REUSEADDR允许单个进程绑定相同的端口到多个socket上，但每个soc
        ket绑定的ip地址不同。这和2很相似，区别请看UNPv1。
        4、SO_REUSEADDR允许完全相同的地址和端口的重复绑定。但这只用于UDP的
        多播，不用于TCP。
        */
        connector.setReuseAddress(true);
        int port = 9999;
        String host = "127.0.0.1";
        connector.setPort(port);
        connector.setHost(host);
        srv.addConnector(connector);
        try {
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");
            srv.setHandler(context);
            context.addServlet(new ServletHolder(new FlumeHttpBaseServlet()),"/");
            srv.start();
        } catch (Exception ex) {
            LOG.error("Error while starting HTTPSource. Exception follows.", ex);
        }
    }
}
