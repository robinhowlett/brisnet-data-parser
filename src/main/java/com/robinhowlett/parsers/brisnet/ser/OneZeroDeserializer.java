package com.robinhowlett.parsers.brisnet.ser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class OneZeroDeserializer extends StdDeserializer<Boolean> {
    public OneZeroDeserializer() {
        super(Boolean.class);
    }

    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext) throws IOException {
        if (jsonParser != null) {
            String text = jsonParser.getText();
            if (text != null && !text.isEmpty()) {
                try {
                    int i = Integer.parseInt(text);
                    return (i == 1);
                } catch (NumberFormatException nfe) {
                }
            }
        }
        return false;
    }
}
