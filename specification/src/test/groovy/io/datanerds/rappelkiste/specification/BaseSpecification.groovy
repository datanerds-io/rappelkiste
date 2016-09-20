package io.datanerds.rappelkiste.specification

import io.datanerds.rappelkiste.specification.util.Configuration
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

abstract class BaseSpecification extends Specification {

    def static final logger = LoggerFactory.getLogger(BaseSpecification.class)

    @Shared
    def static final CONFIGURATION = new Configuration()

    @Shared
    @ClassRule
    def PingCheck PING_CHECK = new PingCheck(CONFIGURATION)

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