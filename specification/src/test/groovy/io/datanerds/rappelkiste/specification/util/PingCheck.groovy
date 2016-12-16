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
    final URI[] hosts

    public PingCheck(URI[] hosts) {
        this.hosts = hosts
    }

    @Override
    protected void before() throws Throwable {
        for(URI baseUrl : hosts) {
            await().until({
                RestAssured
                        .when().get(baseUrl.resolve("/ping"))
                        .then().assertThat()
                            .statusCode(200)
                            .and().body(is(equalTo("pong")))
            })
        }
        logger.info("Nodes {} are available", hosts)
    }

    @Override
    protected void after() { /** nothing to do right now */ }
}