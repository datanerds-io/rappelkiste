package io.datanerds.rappelkiste.specification.utils;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.UUID;

public class UuidMatcher extends BaseMatcher {


    public static UuidMatcher aUUID() {
        return new UuidMatcher();
    }

    @Override
    public boolean matches(Object item) {
        if (item instanceof UUID) {
            return true;
        }
        if (item instanceof String) {
            try {
                UUID.fromString((String) item);
            } catch (IllegalArgumentException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue("a UUID or a String parsable to UUID");

    }
}
