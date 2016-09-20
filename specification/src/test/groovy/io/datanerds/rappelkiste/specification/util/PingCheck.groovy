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
public class PingCheck extends ExternalResource {

    def static final logger = LoggerFactory.getLogger(PingCheck.class)
    def final Configuration configuration

    public PingCheck(Configuration configuration) {
        this.configuration = configuration
    }

    @Override
    protected void before() throws Throwable {
        logger.info("Started version check for all nodes")

        for(def baseurl : configuration.servers) {
            URI uri = new URI(baseurl + "/ping")
            logger.info("Testing url: " + uri.toString())
            await().until({
                RestAssured
                        .when().get(uri)
                        .then().assertThat()
                            .statusCode(200).and().body(is(equalTo("pong")))
            })
        }
    }

    @Override
    protected void after() {
        // nothing to do right now
    }
}
