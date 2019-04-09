package com.robinhowlett.parsers.brisnet.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanSerializer;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.robinhowlett.parsers.brisnet.files.RaceFile.AgeSexRestriction;

import java.io.IOException;

public class AgeSexRestrictionSerializer extends UnwrappingBeanSerializer {
    public AgeSexRestrictionSerializer(BeanSerializerBase src, NameTransformer transformer) {
        super(src, transformer);
    }

    @Override
    public JsonSerializer<Object> unwrappingSerializer(NameTransformer transformer) {
        return new AgeSexRestrictionSerializer(this, transformer);
    }

    @Override
    protected void serializeFields(Object bean, JsonGenerator gen,
            SerializerProvider provider) throws IOException {
        AgeSexRestriction ageSexRestriction = (AgeSexRestriction) bean;
        gen.writeString(ageSexRestriction.value());
    }
}
