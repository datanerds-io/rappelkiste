package io.datanerds.rappelkiste.specification

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.restassured.RestAssured
import org.awaitility.Awaitility
import org.junit.BeforeClass
import org.slf4j.LoggerFactory
import spock.lang.Specification

import java.util.concurrent.TimeUnit

import static org.awaitility.Awaitility.await
import static org.hamcrest.Matchers.containsString

class BaseSpecification extends Specification {

    def static servers = ["http://localhost:8080"]
    def static logger = LoggerFactory.getLogger(BaseSpecification.class)
    def static acceptJsonHeader = "application/json-patch+json"

    def counterPath = "/v1/counter"

    static {
        Awaitility.setDefaultTimeout(5, TimeUnit.SECONDS)
        Awaitility.setDefaultPollInterval(1, TimeUnit.SECONDS)
        Awaitility.setDefaultPollDelay(500, TimeUnit.MILLISECONDS)
    }

    @BeforeClass
    public void 'block until Ping works'() {
        URI uri = new URI("http://localhost:8080/ping")
        logger.info("Testing url: " + uri.toString())
        await().ignoreExceptions().until({
            RestAssured.get(uri).then().assertThat().body(containsString("pong"))
        })
    }

}