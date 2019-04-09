package com.robinhowlett.parsers.brisnet.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class TwoDecimalPaddedSerializer extends StdSerializer<Double> {
    public TwoDecimalPaddedSerializer() {
        super(Double.class);
    }

    @Override
    public void serialize(Double aDouble, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        BigDecimal bd = new BigDecimal(aDouble).setScale(2, RoundingMode.FLOOR);
        jsonGenerator.writeRawValue(bd.toString());
    }
}
