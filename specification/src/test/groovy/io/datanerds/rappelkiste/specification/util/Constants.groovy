package io.datanerds.rappelkiste.specification.util

interface Constants {

    interface CommandLine {
        def BASE_URL_PROPERTY = "baseUrls"
    }

    interface Service {
        def COUNTER_PATH = "/v1/counter"
        def ACCEPT_JSON_HEADER = "application/json-patch+json"
    }
}