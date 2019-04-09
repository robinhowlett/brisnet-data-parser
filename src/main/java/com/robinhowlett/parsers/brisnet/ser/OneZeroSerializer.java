package com.robinhowlett.parsers.brisnet.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class OneZeroSerializer extends StdSerializer<Boolean> {
    public OneZeroSerializer() {
        super(Boolean.class);
    }

    @Override
    public void serialize(Boolean aBoolean, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        if (aBoolean) {
            jsonGenerator.writeNumber(1);
        } else {
            jsonGenerator.writeNumber(0);
        }
    }
}
