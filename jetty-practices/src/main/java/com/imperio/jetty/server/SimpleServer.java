package com.imperio.jetty.server;

import com.imperio.jetty.servlet.SimpleServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class SimpleServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(9999);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new SimpleServlet()), "/simpleServer");
        server.start();
        server.join();
    }
}
