package io.datanerds.rappelkiste.service;

import io.datanerds.rappelkiste.service.counter.CounterModule;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RappelCluster {

    private static List<Server> servers = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(RappelCluster.class);

    public static void main(String[] args) throws Exception {

        List<Integer> ports = new ArrayList<>();
        for (String arg : args) {
            try {
                ports.add(Integer.valueOf(arg));
                logger.info("Added port {}", arg);
            } catch (NumberFormatException e) {
                logger.error("Error adding Port {}" , arg);
            }
        }
        startUp(ports);
    }

    private static void startUp(List<Integer> ports) throws Exception {
        for (int port : ports) {
            Server rappelkiste = new Rappelkiste(port);
            servers.add(rappelkiste);
            rappelkiste.start();
            Runtime.getRuntime().addShutdownHook(new Thread(CounterModule::shutdown));
        }
        logger.info("Started #{} server", servers.size());
    }

}
