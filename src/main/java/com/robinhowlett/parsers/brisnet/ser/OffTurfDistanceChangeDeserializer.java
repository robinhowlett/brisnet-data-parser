package com.robinhowlett.parsers.brisnet.ser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class OffTurfDistanceChangeDeserializer extends StdDeserializer<Boolean> {
    public OffTurfDistanceChangeDeserializer() {
        super(Boolean.class);
    }

    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext) throws IOException, JsonProcessingException {
        if (jsonParser != null) {
            String text = jsonParser.getText();
            if (text != null) {
                return ("Y".equals(text));
            }
        }
        return null;
    }
}
