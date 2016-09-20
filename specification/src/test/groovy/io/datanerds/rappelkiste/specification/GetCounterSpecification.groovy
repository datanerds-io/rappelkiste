package io.datanerds.rappelkiste.specification

import com.jayway.restassured.RestAssured
import spock.lang.Narrative
import spock.lang.Title

import static java.util.UUID.randomUUID
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.is
import static io.datanerds.rappelkiste.specification.util.Constants.Service.COUNTER_PATH


@Narrative("Testing the Get part of the counter service")
@Title("Get Counter Testsuite")
class GetCounterSpecification extends BaseSpecification implements AwaitCounter {

    def "Retrieving an existing counter"(String baseUrl) {

        given: "A preexisting counter, with a uuid"
            def uuid = RestAssured
                    .expect()
                        .statusCode(201)
                    .when()
                         .post(baseUrl + COUNTER_PATH)
                    .andReturn()
                        .as(UUID.class)

        when: "The counter is being retrieved"
            def response = await(baseUrl, uuid)

        then: "The Patch request has status code 200"
            assertThat(response.statusCode, is(equalTo(200)))

        and: "The counter has a value of 0"
            assertThat(response.body.asString(), is(equalTo("0")))

        where:
            baseUrl << CONFIGURATION.servers

    }

    def "Retrieving an non-existing counter"(String baseUrl) {

        given: "A UUID with no matching counter"
            def id = randomUUID()

        when: "The Service is queried for this UUID"
            def response = RestAssured.get(baseUrl + COUNTER_PATH + "/" + id)

        then: "The Patch request has status code 404"
            assertThat(response.statusCode, is(equalTo(404)))

        where:
            baseUrl << CONFIGURATION.servers

    }
}
