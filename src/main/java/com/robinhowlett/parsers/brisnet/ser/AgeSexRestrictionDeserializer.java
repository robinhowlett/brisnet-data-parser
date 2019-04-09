package com.robinhowlett.parsers.brisnet.ser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.robinhowlett.parsers.brisnet.files.RaceFile;
import com.robinhowlett.parsers.brisnet.files.RaceFile.AgeSexRestriction;

import java.io.IOException;
import java.security.InvalidParameterException;

public class AgeSexRestrictionDeserializer extends StdDeserializer<AgeSexRestriction> {
    public AgeSexRestrictionDeserializer() {
        super(String.class);
    }

    @Override
    public AgeSexRestriction deserialize(JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {
        return parse(jsonParser.getText());
    }

    AgeSexRestriction parse(String value) {
        if (value == null || value.length() != 3) {
            throw new InvalidParameterException("Unable to create AgeSexRestriction");
        }
        char age = value.charAt(0);
        char range = value.charAt(1);
        char sex = value.charAt(2);
        return new AgeSexRestriction(RaceFile.Age.lookup(age), RaceFile.Range.lookup(range),
                RaceFile.Sex.lookup(sex));
    }
}
