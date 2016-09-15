package io.datanerds.rappelkiste.specification

import com.fasterxml.jackson.databind.JsonNode
import com.jayway.restassured.RestAssured
import com.jayway.restassured.response.Response
import spock.lang.Narrative
import spock.lang.Title

import static io.datanerds.rappelkiste.specification.utils.JsonMatcher.aValidJsonString
import static org.awaitility.Awaitility.await
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.is


@Narrative("Testing the Get part of the Counter Service")
@Title("Get Counter Testsuite")
class GetCounterSpecification extends BaseSpecification {

    def "Retrieving an existing Counter"(String baseUrl) {

        given: "A preexisting counter, with a uuid"
        URI postUri = new URI(baseUrl + counterPath)
        def JsonNode uuid = objectMapper.readTree(RestAssured.post(postUri).body.asString())
        URI getUri = new URI(baseUrl + counterPath + "/" + uuid.asText())

        await().ignoreExceptions().until({
            RestAssured.given().get(getUri).then().assertThat().statusCode(200)
        })

        when: "The counter is being retrieved"
        def Response response = RestAssured
                .get(getUri)

        then: "The Patch request has status code 200"
        assertThat(response.statusCode, is(200))

        and: "The response is a valid Json"
        assertThat(response.asString(), is(aValidJsonString()))

        and: "The counter has a value of 0"
        assertThat(response.body.asString(), is(equalTo("0")))

        where:
        baseUrl << servers

    }

    def "Retrieving an non-existing Counter"(String baseUrl) {

        given: "A UUID with no matching counter"
        UUID uuid = UUID.randomUUID()
        URI getUri = new URI(baseUrl + counterPath + "/" + uuid)

        when: "The Service is queried for this UUID"
        def Response response = RestAssured
                .get(getUri)

        then: "The Patch request has status code 404"
        assertThat(response.statusCode, is(404))

        where:
        baseUrl << servers

    }
}
