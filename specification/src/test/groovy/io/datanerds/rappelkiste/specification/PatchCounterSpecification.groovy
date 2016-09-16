package io.datanerds.rappelkiste.specification

import com.fasterxml.jackson.databind.JsonNode
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
        URI postUri = new URI(baseUrl + counterPath)
        def JsonNode uuid = objectMapper.readTree(RestAssured.post(postUri).body.asString())

        def patchTo = [
                               "op"   : "add",
                               "path" : "path",
                               "value": "1"
                       ]

        when: "A counter is being Patched"
        URI patchUri = new URI(baseUrl + counterPath + "/" + uuid.asText())
        def Response response = RestAssured
                .given()
                .contentType("application/json-patch+json")
                .body(patchTo)
                .patch(patchUri)

        then: "The Patch request has status code 204"
        assertThat(response.statusCode, is(204))

        and: "The counter has been updated by one"
        await().ignoreExceptions().until({
            RestAssured.given().get(patchUri).then().assertThat().body(equalTo("1"))
        })

        where:
        baseUrl << servers

    }


    def "Patching an existing Counter by adding -1"(String baseUrl) {

        given: "A preexisting counter, with a uuid and a PatchOperation Object"
        URI postUri = new URI(baseUrl + counterPath)
        def JsonNode uuid = objectMapper.readTree(RestAssured.post(postUri).body.asString())

        def patchTo = [
                "op"   : "add",
                "path" : "path",
                "value": "-1"
        ]

        when: "A counter is being Patched"
        URI patchUri = new URI(baseUrl + counterPath + "/" + uuid.asText())
        def Response response = RestAssured
                .given()
                .contentType("application/json-patch+json")
                .body(patchTo)
                .patch(patchUri)

        then: "The Patch request has status code 204"
        assertThat(response.statusCode, is(204))

        and: "The counter has been updated by one"
        await().ignoreExceptions().until({
            RestAssured.given().get(patchUri).then().assertThat().body(equalTo("-1"))
        })

        where:
        baseUrl << servers

    }

    def "Patching an existing Counter by adding -1 and 1"(String baseUrl) {

        given: "A preexisting counter, with a uuid and a PatchOperation Object"
        URI postUri = new URI(baseUrl + counterPath)
        def JsonNode uuid = objectMapper.readTree(RestAssured.post(postUri).body.asString())

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
        URI patchUri = new URI(baseUrl + counterPath + "/" + uuid.asText())
        def Response response1 = RestAssured
                .given()
                .contentType("application/json-patch+json")
                .body(patchTo1)
                .patch(patchUri)

        def Response response2 = RestAssured
                .given()
                .contentType("application/json-patch+json")
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
        baseUrl << servers

    }


    def "Patching an existing Counter by setting it to 42"(String baseUrl) {

        given: "A preexisting counter, with a uuid and a PatchOperation Object"
        URI postUri = new URI(baseUrl + counterPath)
        def JsonNode uuid = objectMapper.readTree(RestAssured.post(postUri).body.asString())

        def patchTo = [
                "op"   : "set",
                "path" : "path",
                "value": "42"
        ]

        when: "A counter is being Patched"
        URI patchUri = new URI(baseUrl + counterPath + "/" + uuid.asText())
        def Response response = RestAssured
                .given()
                .contentType("application/json-patch+json")
                .body(patchTo)
                .patch(patchUri)

        //todo @fw should this be 204??
        then: "The Patch request has status code 200"
        assertThat(response.statusCode, is(200))

        and: "The counter has been updated by one"
        await().ignoreExceptions().until({
            RestAssured.given().get(patchUri).then().assertThat().body(equalTo("42"))
        })

        where:
        baseUrl << servers

    }

    def "Patching an existing Counter by setting it to -42"(String baseUrl) {

        given: "A preexisting counter, with a uuid and a PatchOperation Object"
        URI postUri = new URI(baseUrl + counterPath)
        def JsonNode uuid = objectMapper.readTree(RestAssured.post(postUri).body.asString())

        def patchTo = [
                "op"   : "set",
                "path" : "path",
                "value": "-42"
        ]

        when: "A counter is being Patched"
        URI patchUri = new URI(baseUrl + counterPath + "/" + uuid.asText())
        def Response response = RestAssured
                .given()
                .contentType("application/json-patch+json")
                .body(patchTo)
                .patch(patchUri)

        //todo @fw should this be 204??
        then: "The Patch request has status code 200"
        assertThat(response.statusCode, is(200))

        and: "The counter has been updated by one"
        await().ignoreExceptions().until({
            RestAssured.given().get(patchUri).then().assertThat().body(equalTo("-42"))
        })

        where:
        baseUrl << servers

    }

    def "Patching an existing Counter with value 1 by setting it to 42"(String baseUrl) {

        given: "A preexisting counter, with a uuid and a PatchOperation Object"
        URI postUri = new URI(baseUrl + counterPath)
        def JsonNode uuid = objectMapper.readTree(RestAssured.post(postUri).body.asString())

        def patchTo1 = [
                "op"   : "set",
                "path" : "path",
                "value": "1"
        ]

        def patchTo2 = [
                "op"   : "set",
                "path" : "path",
                "value": "42"
        ]

        when: "A counter is being Patched"
        URI patchUri = new URI(baseUrl + counterPath + "/" + uuid.asText())
        def Response response1 = RestAssured
                .given().log().all()
                .contentType("application/json-patch+json")

                .body(patchTo1)
                .patch(patchUri)

        def Response response2 = RestAssured
                .given()
                .contentType("application/json-patch+json")
                .body(patchTo2)
                .patch(patchUri)

        //todo @fw should this be 204??
        then: "The Patch request has status code 200"
        assertThat(response1.statusCode, is(200))

        //todo @fw should this be 204??
        and: "The Patch request has status code 200"
        assertThat(response2.statusCode, is(200))

        and: "The counter has been updated by one"
        await().until({
            RestAssured.given().get(patchUri).then().assertThat().body(is(equalTo("42")))
        })

        where:
        baseUrl << servers

    }

    def "Trying to patch a counter with an invalid PatchTo missing a value"(String baseUrl) {

        given: "A preexisting counter, with a uuid and a PatchOperation Object"
        URI postUri = new URI(baseUrl + counterPath)
        def JsonNode uuid = objectMapper.readTree(RestAssured.post(postUri).body.asString())

        def patchTo = [
                "op"   : "set"
        ]

        when: "A counter is being Patched"
        URI patchUri = new URI(baseUrl + counterPath + "/" + uuid.asText())
        def Response response = RestAssured
                .given()
                .contentType("application/json-patch+json")
                .body(patchTo)
                .patch(patchUri)

        then: "The Patch request has status code 400"
        assertThat(response.statusCode, is(400))

        where:
        baseUrl << servers

    }


    def "Trying to patch a counter with an invalid PatchTo missing the operation"(String baseUrl) {

        given: "A preexisting counter, with a uuid and a PatchOperation Object"
        URI postUri = new URI(baseUrl + counterPath)
        def JsonNode uuid = objectMapper.readTree(RestAssured.post(postUri).body.asString())

        def patchTo = [
                "value"   : "1"
        ]

        when: "A counter is being Patched"
        URI patchUri = new URI(baseUrl + counterPath + "/" + uuid.asText())
        def Response response = RestAssured
                .given()
                .contentType("application/json-patch+json")
                .body(patchTo)
                .patch(patchUri)

        then: "The Patch request has status code 400"
        assertThat(response.statusCode, is(400))

        where:
        baseUrl << servers

    }
}
