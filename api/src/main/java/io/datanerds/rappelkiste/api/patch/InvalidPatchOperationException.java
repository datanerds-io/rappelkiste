package io.datanerds.rappelkiste.api.patch;

public class InvalidPatchOperationException extends IllegalArgumentException {

    public InvalidPatchOperationException(String message) {
        super(message);
    }
}
