package io.datanerds.rappelkiste.service;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import io.datanerds.rappelkiste.service.counter.CounterModule;
import io.datanerds.rappelkiste.service.exception.mapper.CounterNotFoundMapper;
import io.datanerds.rappelkiste.service.exception.mapper.InvalidPatchOperationMapper;
import io.datanerds.rappelkiste.service.exception.mapper.JsonProcessingMapper;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
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

        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(JacksonJsonProvider.class);
        resourceConfig.register(InvalidPatchOperationMapper.class);
        resourceConfig.register(JsonProcessingMapper.class);
        resourceConfig.register(CounterNotFoundMapper.class);
        resourceConfig.register(CounterService.class);
        resourceConfig.register(PingService.class);
        ServletContainer servletContainer = new ServletContainer(resourceConfig);

        ServletHolder servletHolder = new ServletHolder(servletContainer);
        servletHolder.setInitOrder(0);

        ServletContextHandler handler = new ServletContextHandler(NO_SESSIONS);
        handler.setContextPath("/");
        handler.addServlet(servletHolder, "/*");
        setHandler(handler);
    }
}