package com.robinhowlett.parsers.brisnet.ser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.robinhowlett.parsers.brisnet.files.StartFile.ProgramOrScratched;

import java.io.IOException;

public class ProgramOrScratchedDeserializer extends StdDeserializer<ProgramOrScratched> {
    public ProgramOrScratchedDeserializer() {
        super(ProgramOrScratched.class);
    }

    @Override
    public ProgramOrScratched deserialize(JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {
        if (jsonParser != null) {
            String text = jsonParser.getText();
            if (text != null && !text.isEmpty()) {
                return new ProgramOrScratched(text);
            }
        }
        return null;
    }
}
