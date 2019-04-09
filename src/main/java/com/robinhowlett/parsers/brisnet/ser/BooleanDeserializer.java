package com.robinhowlett.parsers.brisnet.ser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class BooleanDeserializer extends StdDeserializer<Boolean> {
    public BooleanDeserializer() {
        super(Boolean.class);
    }

    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext) throws IOException {
        if (jsonParser != null) {
            String text = jsonParser.getText();
            return (text != null && !text.isEmpty());
        }
        return false;
    }
}
