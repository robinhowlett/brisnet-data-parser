package com.robinhowlett.parsers.brisnet.formats;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.robinhowlett.mixins.LinkMixin;
import com.robinhowlett.ser.SimpleLocalDateDeserializer;
import com.robinhowlett.ser.SimpleLocalDateSerializer;

import org.springframework.hateoas.Link;

import java.time.LocalDate;

public class BrisnetJsonMapper extends ObjectMapper {
    public BrisnetJsonMapper() {
        SimpleModule simpleLocalDateModule = new SimpleModule();
        simpleLocalDateModule.addSerializer(LocalDate.class, new SimpleLocalDateSerializer());
        simpleLocalDateModule.addDeserializer(LocalDate.class, new SimpleLocalDateDeserializer());
        registerModule(simpleLocalDateModule);

        // adds JDK 8 Parameter Name access for cleaner JSON-to-Object mapping
        registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));

        addMixIn(Link.class, LinkMixin.class);
    }
}
