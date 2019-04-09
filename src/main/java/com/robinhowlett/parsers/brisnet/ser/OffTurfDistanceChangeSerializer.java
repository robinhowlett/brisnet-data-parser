package com.robinhowlett.parsers.brisnet.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class OffTurfDistanceChangeSerializer extends StdSerializer<Boolean> {
    public OffTurfDistanceChangeSerializer() {
        super(Boolean.class);
    }

    @Override
    public void serialize(Boolean aBoolean, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        if (aBoolean != null) {
            jsonGenerator.writeString(aBoolean ? "Y" : "N");
        } else {
            jsonGenerator.writeNull();
        }
    }
}
