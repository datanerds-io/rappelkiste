package io.datanerds.rappelkiste.api.patch;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PatchOperation {

    public final String op;
    public final String path;
    public final long value;

    @JsonCreator
    public PatchOperation(@JsonProperty(value = "op", required = true) String op,
            @JsonProperty(value = "path", required = true) String path,
            @JsonProperty(value = "value", required = true) long value) {
        this.op = op;
        this.path = path;
        this.value = value;
        verifyOperation();
    }

    private void verifyOperation() {
        if (!"ADD".equalsIgnoreCase(op)) {
            throw new InvalidPatchOperationException("Currently only ADD is supported for PATCH.");
        }
    }
}
