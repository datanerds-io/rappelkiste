package io.datanerds.rappelkiste.specification


import io.datanerds.rappelkiste.specification.util.PingCheck
import org.awaitility.Awaitility
import org.junit.ClassRule
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.slf4j.LoggerFactory
import spock.lang.Shared
import spock.lang.Specification

import java.util.concurrent.TimeUnit

import static io.datanerds.rappelkiste.specification.util.Constants.CommandLine.BASE_URL_PROPERTY

abstract class BaseSpecification extends Specification {

    static final logger = LoggerFactory.getLogger(BaseSpecification.class)

    @Shared
    static final URI[] HOSTS = System.getProperty(BASE_URL_PROPERTY, "http://localhost:8080")
            .split(',')
            .collect{value -> new URI(value)}

    @Shared
    @ClassRule
    def PingCheck PING_CHECK = new PingCheck(HOSTS)

    @Rule
    public TestRule watcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            logger.debug(" +++ Starting Test: {} +++", description.getMethodName())
        }
    }

    static {
        Awaitility.setDefaultTimeout(10, TimeUnit.SECONDS)
        Awaitility.setDefaultPollInterval(1, TimeUnit.SECONDS)
        Awaitility.setDefaultPollDelay(500, TimeUnit.MILLISECONDS)
    }
}