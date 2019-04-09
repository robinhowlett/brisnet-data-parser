package com.robinhowlett.parsers.brisnet.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class FourDecimalPaddedSerializer extends StdSerializer<Double> {
    public FourDecimalPaddedSerializer() {
        super(Double.class);
    }

    @Override
    public void serialize(Double aDouble, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        BigDecimal bd = new BigDecimal(aDouble).setScale(4, RoundingMode.FLOOR);
        jsonGenerator.writeRawValue(bd.toString());
    }
}
