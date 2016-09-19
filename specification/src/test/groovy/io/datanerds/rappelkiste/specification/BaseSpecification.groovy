package io.datanerds.rappelkiste.specification

import com.jayway.restassured.RestAssured
import io.datanerds.rappelkiste.specification.util.Configuration
import org.awaitility.Awaitility
import org.junit.BeforeClass
import org.slf4j.LoggerFactory
import spock.lang.Shared
import spock.lang.Specification

import java.util.concurrent.TimeUnit

import static org.awaitility.Awaitility.await
import static org.hamcrest.Matchers.containsString

class BaseSpecification extends Specification {

    def static logger = LoggerFactory.getLogger(BaseSpecification.class)

    @Shared
    def static configuration = new Configuration()

    static {
        Awaitility.setDefaultTimeout(5, TimeUnit.SECONDS)
        Awaitility.setDefaultPollInterval(1, TimeUnit.SECONDS)
        Awaitility.setDefaultPollDelay(500, TimeUnit.MILLISECONDS)
    }

    @BeforeClass
    public void 'block until Ping works'() {
        for(def baseurl : configuration.servers) {
            URI uri = new URI(baseurl + "/ping")
            logger.info("Testing url: " + uri.toString())
            await().ignoreExceptions().until({
                RestAssured.get(uri).then().assertThat().statusCode(200).and().body(containsString("pong"))
            })
        }
    }

}