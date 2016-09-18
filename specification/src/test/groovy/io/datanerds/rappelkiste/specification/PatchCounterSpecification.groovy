package io.datanerds.rappelkiste.specification

import com.jayway.restassured.RestAssured
import com.jayway.restassured.response.Response
import spock.lang.Narrative
import spock.lang.Title

import static org.awaitility.Awaitility.await
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.is


@Narrative("Testing the Patch part of the Counter Service")
@Title("Patch Counter Testsuite")
class PatchCounterSpecification extends BaseSpecification {


    def "Patching an existing Counter by adding 1"(String baseUrl) {

        given: "A preexisting counter, with a uuid and a PatchOperation Object"

        def uuid = RestAssured
                .expect()
                    .statusCode(201)
                .when()
                    .post(baseUrl + counterPath)
                .andReturn()
                    .as(UUID.class)

        def patchTo = [
                               "op"   : "add",
                               "path" : "path",
                               "value": "1"
                       ]

        when: "A counter is being Patched"
        def response = RestAssured
                .given()
                    .contentType(acceptJsonHeader)
                    .body(patchTo)
                .patch(baseUrl + counterPath + "/" +uuid)

        then: "The Patch request has status code 204"
        assertThat(response.statusCode, is(204))

        and: "The counter has been updated by one"
        await().ignoreExceptions().until({
            RestAssured.given().when().get(baseUrl + counterPath + "/" +uuid).then().assertThat().body(equalTo("1"))
        })

        where:
        baseUrl << configuration.servers

    }


    def "Patching an existing Counter by adding -1"(String baseUrl) {

        given: "A preexisting counter, with a uuid and a PatchOperation Object"

        def uuid = RestAssured
                .expect()
                    .statusCode(201)
                .when()
                    .post(baseUrl + counterPath)
                .andReturn()
                    .as(UUID.class)

        def patchTo = [
                "op"   : "add",
                "path" : "path",
                "value": "-1"
        ]

        when: "A counter is being Patched"
        URI patchUri = new URI(baseUrl + counterPath + "/" + uuid)
        def Response response = RestAssured
                .given()
                    .contentType(acceptJsonHeader)
                    .body(patchTo)
                .patch(patchUri)

        then: "The Patch request has status code 204"
        assertThat(response.statusCode, is(204))

        and: "The counter has been updated by one"
        await().ignoreExceptions().until({
            RestAssured.given().get(patchUri).then().assertThat().body(equalTo("-1"))
        })

        where:
        baseUrl << configuration.servers

    }

    def "Patching an existing Counter by adding -1 and 1"(String baseUrl) {

        given: "A preexisting counter, with a uuid and a PatchOperation Object"
        def uuid = RestAssured
                .expect()
                    .statusCode(201)
                .when()
                    .post(baseUrl + counterPath)
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
        URI patchUri = new URI(baseUrl + counterPath + "/" + uuid)
        def Response response1 = RestAssured
                .given()
                    .contentType(acceptJsonHeader)
                    .body(patchTo1)
                .patch(patchUri)

        def Response response2 = RestAssured
                .given()
                    .contentType(acceptJsonHeader)
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
        baseUrl << configuration.servers

    }


    def "Trying to patch a counter with an invalid PatchTo missing a value"(String baseUrl) {

        given: "A preexisting counter, with a uuid and a PatchOperation Object"
        def uuid = RestAssured
                .expect()
                    .statusCode(201)
                .when()
                    .post(baseUrl + counterPath)
                .andReturn()
                    .as(UUID.class)

        def patchTo = [
                "op"   : "set"
        ]

        when: "A counter is being Patched"
        URI patchUri = new URI(baseUrl + counterPath + "/" + uuid)
        def Response response = RestAssured
                .given()
                    .contentType(acceptJsonHeader)
                    .body(patchTo)
                .patch(patchUri)

        then: "The Patch request has status code 400"
        assertThat(response.statusCode, is(400))

        where:
        baseUrl << configuration.servers

    }


    def "Trying to patch a counter with an invalid PatchTo missing the operation"(String baseUrl) {

        given: "A preexisting counter, with a uuid and a PatchOperation Object"
        def uuid = RestAssured
                .expect()
                    .statusCode(201)
                .when()
                    .post(baseUrl + counterPath)
                .andReturn()
                    .as(UUID.class)

        def patchTo = [
                "value"   : "1"
        ]

        when: "A counter is being Patched"
        URI patchUri = new URI(baseUrl + counterPath + "/" + uuid)
        def Response response = RestAssured
                .given()
                    .contentType(acceptJsonHeader)
                    .body(patchTo)
                .patch(patchUri)

        then: "The Patch request has status code 400"
        assertThat(response.statusCode, is(400))

        where:
        baseUrl << configuration.servers

    }

    def "Trying to patch an existing Counter with an invalid value"(String baseUrl) {

        given: "A preexisting counter, with a uuid and a PatchOperation Object"

        def uuid = RestAssured
                .expect()
                    .statusCode(201)
                .when()
                    .post(baseUrl + counterPath)
                .andReturn()
                    .as(UUID.class)

        def patchTo = [
                "op"   : "add",
                "path" : "path",
                "value": "this is not a number"
        ]

        when: "A counter is being Patched"
        URI patchUri = new URI(baseUrl + counterPath + "/" + uuid)
        def Response response = RestAssured
                .given()
                    .contentType(acceptJsonHeader)
                    .body(patchTo)
                .patch(patchUri)

        then: "The Patch request has status code 204"
        assertThat(response.statusCode, is(400))

        where:
        baseUrl << configuration.servers

    }
}
