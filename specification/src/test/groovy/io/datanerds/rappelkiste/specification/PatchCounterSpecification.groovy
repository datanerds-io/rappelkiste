package io.datanerds.rappelkiste.specification

import com.jayway.restassured.RestAssured
import com.jayway.restassured.response.Response
import io.datanerds.rappelkiste.specification.util.Constants
import spock.lang.Narrative
import spock.lang.Title

import static org.awaitility.Awaitility.await
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.is
import static io.datanerds.rappelkiste.specification.util.Constants.Service.COUNTER_PATH


@Narrative("Testing the PATCH part of the counter service")
@Title("Patch Counter Test Suite")
class PatchCounterSpecification extends BaseSpecification {

    def "Patching an existing counter by adding 42"(String baseUrl) {

        given: "A counter has been created an its UUID is known"
            def uuid = RestAssured
                    .expect()
                        .statusCode(201)
                    .when()
                        .post(baseUrl + COUNTER_PATH)
                    .andReturn()
                        .as(UUID.class)

        and: "A patch operation for adding 42 exists"
            def patch = ["op"   : "add",
                         "path" : "path",
                         "value": "42"]

        when: "A counter is being patched"
            def response = RestAssured
                    .given()
                        .contentType(Constants.Service.ACCEPT_JSON_HEADER)
                        .body(patch)
                    .patch(baseUrl + COUNTER_PATH + "/" +uuid)

        then: "Patch request has status code 204"
            assertThat(response.statusCode, is(204))

        and: "The counter has been updated by 42"
            await().ignoreExceptions().until({
                RestAssured
                        .when()
                            .get(baseUrl + COUNTER_PATH + "/" + uuid)
                        .then()
                            .assertThat().body(equalTo("42"))
            })

        where:
            baseUrl << HOSTS
    }

    def "Patching an existing Counter by adding -1"(String baseUrl) {

        given: "A preexisting counter, with a uuid and a PatchOperation Object"

        def uuid = RestAssured
                .expect()
                    .statusCode(201)
                .when()
                    .post(baseUrl + COUNTER_PATH)
                .andReturn()
                    .as(UUID.class)

        def patchTo = [
                "op"   : "add",
                "path" : "path",
                "value": "-1"
        ]

        when: "A counter is being Patched"
        URI patchUri = new URI(baseUrl + COUNTER_PATH + "/" + uuid)
        def Response response = RestAssured
                .given()
                    .contentType(Constants.Service.ACCEPT_JSON_HEADER)
                    .body(patchTo)
                .patch(patchUri)

        then: "The Patch request has status code 204"
        assertThat(response.statusCode, is(204))

        and: "The counter has been updated by one"
        await().ignoreExceptions().until({
            RestAssured.given().get(patchUri).then().assertThat().body(equalTo("-1"))
        })

        where:
        baseUrl << HOSTS
    }

    def "Patching an existing Counter by adding -1 and 1"(String baseUrl) {

        given: "A preexisting counter, with a uuid and a PatchOperation Object"
        def uuid = RestAssured
                .expect()
                    .statusCode(201)
                .when()
                    .post(baseUrl + COUNTER_PATH)
                .andReturn()
                    .as(UUID.class)

        def patchTo1 = [
                "op"   : "add",
                "path" : "path",
                "value": "1"
        ]

        def patchTo2 = [
                "op"   : "add",
                "path" : "path",
                "value": "-1"
        ]

        when: "A counter is being Patched"
        URI patchUri = new URI(baseUrl + COUNTER_PATH + "/" + uuid)
        def Response response1 = RestAssured
                .given()
                    .contentType(Constants.Service.ACCEPT_JSON_HEADER)
                    .body(patchTo1)
                .patch(patchUri)

        def Response response2 = RestAssured
                .given()
                    .contentType(Constants.Service.ACCEPT_JSON_HEADER)
                    .body(patchTo2)
                .patch(patchUri)

        then: "The first Patch request has status code 204"
        assertThat(response1.statusCode, is(204))

        and: "The second Patch request has status code 204"
        assertThat(response2.statusCode, is(204))

        and: "The counter has been updated by one"
        await().ignoreExceptions().until({
            RestAssured.given().get(patchUri).then().assertThat().body(equalTo("0"))
        })

        where:
        baseUrl << HOSTS
    }

    def "Trying to patch a counter with an invalid PatchTo missing a value"(String baseUrl) {

        given: "A preexisting counter, with a uuid and a PatchOperation Object"
        def uuid = RestAssured
                .expect()
                    .statusCode(201)
                .when()
                    .post(baseUrl + COUNTER_PATH)
                .andReturn()
                    .as(UUID.class)

        def patchTo = [
                "op"   : "set"
        ]

        when: "A counter is being Patched"
        URI patchUri = new URI(baseUrl + COUNTER_PATH + "/" + uuid)
        def Response response = RestAssured
                .given()
                    .contentType(Constants.Service.ACCEPT_JSON_HEADER)
                    .body(patchTo)
                .patch(patchUri)

        then: "The Patch request has status code 400"
        assertThat(response.statusCode, is(400))

        where:
        baseUrl << HOSTS
    }

    def "Trying to patch a counter with an invalid PatchTo missing the operation"(String baseUrl) {

        given: "A preexisting counter, with a uuid and a PatchOperation Object"
        def uuid = RestAssured
                .expect()
                    .statusCode(201)
                .when()
                    .post(baseUrl + COUNTER_PATH)
                .andReturn()
                    .as(UUID.class)

        def patchTo = [
                "value"   : "1"
        ]

        when: "A counter is being Patched"
        URI patchUri = new URI(baseUrl + COUNTER_PATH + "/" + uuid)
        def Response response = RestAssured
                .given()
                    .contentType(Constants.Service.ACCEPT_JSON_HEADER)
                    .body(patchTo)
                .patch(patchUri)

        then: "The Patch request has status code 400"
        assertThat(response.statusCode, is(400))

        where:
        baseUrl << HOSTS
    }

    def "Trying to patch an existing Counter with an invalid value"(String baseUrl) {

        given: "A preexisting counter, with a uuid and a PatchOperation Object"

        def uuid = RestAssured
                .expect()
                    .statusCode(201)
                .when()
                    .post(baseUrl + COUNTER_PATH)
                .andReturn()
                    .as(UUID.class)

        def patchTo = [
                "op"   : "add",
                "path" : "path",
                "value": "this is not a number"
        ]

        when: "A counter is being Patched"
        URI patchUri = new URI(baseUrl + COUNTER_PATH + "/" + uuid)
        def Response response = RestAssured
                .given()
                    .contentType(Constants.Service.ACCEPT_JSON_HEADER)
                    .body(patchTo)
                .patch(patchUri)

        then: "The Patch request has status code 204"
        assertThat(response.statusCode, is(400))

        where:
        baseUrl << HOSTS
    }
}