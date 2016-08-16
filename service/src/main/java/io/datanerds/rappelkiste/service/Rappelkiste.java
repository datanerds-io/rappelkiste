package io.datanerds.rappelkiste.service;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

public class Rappelkiste {

    public static void main(String[] args) throws Exception {
        new Rappelkiste(8080);
    }

    public Rappelkiste(int port) throws Exception {
        Server server = new Server(port);

        ServletContextHandler handler = new ServletContextHandler(NO_SESSIONS);
        handler.setContextPath("/");
        server.setHandler(handler);

        ServletHolder servletHolder = handler.addServlet(ServletContainer.class, "/*");
        servletHolder.setInitOrder(0);
        String classNames = String.format("%s, %s", PingService.class.getCanonicalName(), CounterService.class.getCanonicalName());
        servletHolder.setInitParameter("jersey.config.server.provider.classnames", classNames);
        servletHolder.setInitParameter("jersey.config.server.provider.packages", "com.fasterxml.jackson.jaxrs.json");

        server.start();
        server.join();
    }
}