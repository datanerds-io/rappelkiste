package io.datanerds.rappelkiste.specification.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.io.IOException;

public class JsonMatcher extends BaseMatcher {

    private ObjectMapper objectMapper = new ObjectMapper();

    public static JsonMatcher aValidJsonString() {
        return new JsonMatcher();
    }

    @Override
    public boolean matches(Object item) {
        if (item instanceof String) {
            try {
                objectMapper.readTree((String) item);
            } catch (IOException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue("a valid JSON String");

    }
}

