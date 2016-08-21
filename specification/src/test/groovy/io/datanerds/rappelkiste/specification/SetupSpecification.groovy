package io.datanerds.rappelkiste.specification

import com.jayway.awaitility.Awaitility
import spock.lang.Specification

import java.util.concurrent.TimeUnit

class SetupSpecification extends Specification {

    static {
        Awaitility.setDefaultTimeout(5, TimeUnit.SECONDS)
        Awaitility.setDefaultPollInterval(1, TimeUnit.SECONDS)
        Awaitility.setDefaultPollDelay(500, TimeUnit.MILLISECONDS)
    }

}