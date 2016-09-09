package io.datanerds.rappelkiste.service.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class CounterEventTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void serialize() throws IOException {
        CreateCounter create = new CreateCounter(UUID.randomUUID());
        byte[] json = mapper.writeValueAsBytes(create);
        assertThat(mapper.readValue(json, CreateCounter.class), is(equalTo(create)));
    }
}