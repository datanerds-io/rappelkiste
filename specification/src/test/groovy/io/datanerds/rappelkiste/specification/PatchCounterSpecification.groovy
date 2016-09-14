package io.datanerds.rappelkiste.specification

import com.fasterxml.jackson.databind.JsonNode
import com.jayway.restassured.RestAssured
import com.jayway.restassured.response.Response

import static org.awaitility.Awaitility.await
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.is

class PatchCounterSpecification extends BaseSpecification {


    def "Patching an existing Counter"(String baseUrl) {

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

        //todo @fw should this be 204??
        then: "The Patch request has status code 200"
        assertThat(response.statusCode, is(200))

        and: "The counter has been updated by one"
        await().ignoreExceptions().until({
            RestAssured.given().get(patchUri).then().assertThat().body(equalTo("1"))
        })

        where:
        baseUrl << servers

    }
}
