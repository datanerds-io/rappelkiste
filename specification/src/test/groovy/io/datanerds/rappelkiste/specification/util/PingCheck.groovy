package io.datanerds.rappelkiste.specification.util

import com.jayway.restassured.RestAssured
import org.junit.rules.ExternalResource
import org.slf4j.LoggerFactory

import static org.awaitility.Awaitility.await
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.is

/**
 * This class verifies that all nodes are accessible.
 */
class PingCheck extends ExternalResource {

    def static final logger = LoggerFactory.getLogger(PingCheck.class)
    def final Configuration configuration

    public PingCheck(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void before() throws Throwable {
        for (URI baseUrl : configuration.servers) {
            await().until({
                RestAssured.when().get(baseUrl.resolve("/ping"))
                        .then().assertThat()
                        .statusCode(200).and().body(is(equalTo("pong")))
            })
        }

        logger.info("Nodes {} are available", configuration.servers)
    }

    @Override
    protected void after() { /** nothing to do right now */ }
}