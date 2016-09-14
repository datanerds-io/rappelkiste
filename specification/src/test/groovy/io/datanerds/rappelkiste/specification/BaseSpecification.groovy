package io.datanerds.rappelkiste.specification

import com.jayway.restassured.RestAssured
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static com.jayway.awaitility.Awaitility.await
import static org.hamcrest.Matchers.containsString

class BaseSpecification extends SetupSpecification {

    static final Logger logger = LoggerFactory.getLogger(BaseSpecification.class);

    def "lets ping the two services"(String baseUrl) {

        given: "Two servers running locally on 8080 and 8081"
        URI uri = new URI(baseUrl + "/ping")

        when: "The Ping services are queried"

        then: "expect a pong in the body"
        logger.info("Testing url: " + uri.toString())
        await().ignoreExceptions().until({
            RestAssured.get(uri).then().assertThat().body(containsString("pong"))
        })

        where:
        baseUrl << ["http://localhost:8080"]

    }
}