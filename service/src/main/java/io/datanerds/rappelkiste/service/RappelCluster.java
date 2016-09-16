package io.datanerds.rappelkiste.service;

import io.datanerds.rappelkiste.service.counter.CounterModule;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RappelCluster {

    private final List<Server> servers = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(RappelCluster.class);

    public void startUp(int... ports) throws Exception {
        for (int port : ports) {
            Server rappelkiste = new Rappelkiste(port);
            servers.add(rappelkiste);
            rappelkiste.start();
        }
        logger.info("Started #{} server", servers.size());
    }

    public void shutDown() {
        CounterModule.shutdown();
        servers.forEach(this::stopServer);
    }

    private void stopServer(Server server) {
        try {
            server.stop();
        } catch (Exception ex) {
            logger.warn("Could not stop server", ex);
        }
    }
}
