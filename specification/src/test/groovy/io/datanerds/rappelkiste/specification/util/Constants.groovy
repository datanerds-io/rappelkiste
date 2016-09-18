package io.datanerds.rappelkiste.specification.util

interface Constants {

    interface CommandLine {
        def BASE_URL_PROPERTY = "baseUrls"
    }

    interface Service {

        def static counterPath = "/v1/counter"
        def static acceptJsonHeader = "application/json-patch+json"
    }
}