package io.datanerds.rappelkiste.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PatchOperation {

    public final String op;
    public final String path;
    public final long value;

    @JsonCreator
    public PatchOperation(@JsonProperty("op") String op, @JsonProperty("path") String path,
                          @JsonProperty("value") long value) {
        this.op = op;
        this.path = path;
        this.value = value;
        verifyOperation();
    }

    private void verifyOperation() {

    }
}
