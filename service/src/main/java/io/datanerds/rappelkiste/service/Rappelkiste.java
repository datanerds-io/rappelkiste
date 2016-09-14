package io.datanerds.rappelkiste.service;

import io.datanerds.rappelkiste.service.counter.CounterModule;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

public class Rappelkiste extends Server {

    public static void main(String[] args) throws Exception {
        Server rappelkiste = new Rappelkiste(8080);
        rappelkiste.start();
        rappelkiste.join();

        Runtime.getRuntime().addShutdownHook(new Thread(CounterModule::shutdown));
    }

    public Rappelkiste(int port) {
        super(port);

        ServletContextHandler handler = new ServletContextHandler(NO_SESSIONS);
        handler.setContextPath("/");

        ServletHolder servletHolder = handler.addServlet(ServletContainer.class, "/*");
        servletHolder.setInitOrder(0);
        String classNames = String.format("%s, %s, %s", PingService.class.getCanonicalName(),
                CounterService.class.getCanonicalName(), CounterNotFoundMapper.class.getCanonicalName());
        servletHolder.setInitParameter("jersey.config.server.provider.classnames", classNames);
        servletHolder.setInitParameter("jersey.config.server.provider.packages", "com.fasterxml.jackson.jaxrs.json");

        setHandler(handler);
    }
}