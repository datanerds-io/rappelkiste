package io.datanerds.rappelkiste.specification

import com.jayway.restassured.RestAssured

import static org.awaitility.Awaitility.await

trait AwaitCounter {

    def await = {baseUrl, id ->
        def response
        await().until({
            response = RestAssured
                    .expect()
                    .statusCode(200)
                    .when()
                    .get(baseUrl + counterPath + "/" + id)
                    .andReturn()
        })
        response
    }
}
