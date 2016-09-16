package io.datanerds.rappelkiste.service;

import io.datanerds.rappelkiste.service.counter.CounterModule;
import io.datanerds.rappelkiste.service.exception.CounterNotFoundMapper;
import io.datanerds.rappelkiste.service.exception.InvalidPatchOperationMapper;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.ArrayList;
import java.util.List;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

public class Rappelkiste extends Server {

    public static void main(String[] args) throws Exception {
        Server rappelkiste = new Rappelkiste(8080);
        rappelkiste.start();
        rappelkiste.join();

        Runtime.getRuntime().addShutdownHook(new Thread(CounterModule::shutdown));
    }

    public static void main(int... ports) throws Exception {
        List<Server> servers = new ArrayList<>();
        for (int port : ports) {
            Server rappelkiste = new Rappelkiste(port);
            rappelkiste.start();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(CounterModule::shutdown));
    }

    public Rappelkiste(int port) {
        super(port);

        ServletContextHandler handler = new ServletContextHandler(NO_SESSIONS);
        handler.setContextPath("/");

        ServletHolder servletHolder = handler.addServlet(ServletContainer.class, "/*");
        servletHolder.setInitOrder(0);
        String classNames = String.format("%s, %s, %s, %s", PingService.class.getCanonicalName(),
                CounterService.class.getCanonicalName(), CounterNotFoundMapper.class.getCanonicalName(),
                InvalidPatchOperationMapper.class.getCanonicalName());
        servletHolder.setInitParameter("jersey.config.server.provider.packages", "com.fasterxml.jackson.jaxrs.json");
        servletHolder.setInitParameter("jersey.config.server.provider.classnames", classNames);


        setHandler(handler);
    }
}