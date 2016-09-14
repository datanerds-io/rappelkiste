package io.datanerds.rappelkiste.specification

import com.fasterxml.jackson.databind.JsonNode
import com.jayway.restassured.RestAssured
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static io.datanerds.rappelkiste.specification.utils.JsonMatcher.aValidJsonString
import static io.datanerds.rappelkiste.specification.utils.UuidMatcher.aUUID
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.is
import static org.awaitility.Awaitility.await

class PostCounterSpec extends BaseSpecification {

    static final Logger logger = LoggerFactory.getLogger(PostCounterSpec.class);

    def "Posting a new Counter to the Service"(String baseUrl) {

        given: "The services are running locally"
        URI postUri = new URI(baseUrl + counterPath)
        logger.info("Testing url: " + postUri.toString())

        when: "A counter is being posted"
        def String response = RestAssured.post(postUri).body.asString()

        then: "expect that the response is a valid Json"
        assertThat(response, is(aValidJsonString()))

        and: "It contains a String parsable to a Json"
        def JsonNode json = objectMapper.readTree(response)
        assertThat(json.asText(), is(aUUID()))

        and: "The Counter is available to get"
        URI getUri = new URI(baseUrl + counterPath + "/" + json.asText())

        await().ignoreExceptions().until({
            RestAssured.given().log().all().get(getUri).body.print()
        })

        where:
        baseUrl << servers

    }
}