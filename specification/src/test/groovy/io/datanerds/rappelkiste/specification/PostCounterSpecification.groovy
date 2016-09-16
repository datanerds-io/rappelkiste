package io.datanerds.rappelkiste.specification

import com.fasterxml.jackson.databind.JsonNode
import com.jayway.restassured.RestAssured
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Narrative
import spock.lang.Title

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.instanceOf
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.notNullValue


@Narrative("Testing the Post part of the Counter Service")
@Title("Post Counter Testsuite")
class PostCounterSpecification extends BaseSpecification implements AwaitCounter {

    static final Logger logger = LoggerFactory.getLogger(PostCounterSpecification.class);

    def "Posting a new Counter to the Service"(String baseUrl) {

        given: "The services are running locally"
        URI postUri = new URI(baseUrl + counterPath)
        logger.info("Testing url: " + postUri.toString())

        when: "A counter is being posted"
        def id = RestAssured.expect().statusCode(201).when().post(postUri).thenReturn().body().as(UUID.class)

        then: "expect that the response is a valid JSON representation of an UUID"
        assertThat(id, is(instanceOf(UUID.class)))

        and: "It contains a String parsable to a Json"
        assertThat(id, is(notNullValue()))

        and: "The Counter is available to get"
        URI getUri = new URI(baseUrl + counterPath + "/" + id)

        def response = await(baseUrl, id)

        and: "The counter has a value of zero"
        def counter = response.body().as(Integer.class)
        assertThat(counter, is(equalTo(0)))

        where:
        baseUrl << servers

    }
}