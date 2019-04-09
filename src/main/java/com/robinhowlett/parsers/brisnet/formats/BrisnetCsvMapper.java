package com.robinhowlett.parsers.brisnet.formats;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.robinhowlett.parsers.brisnet.files.RaceFile.AgeSexRestriction;
import com.robinhowlett.parsers.brisnet.files.StartFile.ProgramOrScratched;
import com.robinhowlett.parsers.brisnet.ser.AgeSexRestrictionSerializer;
import com.robinhowlett.parsers.brisnet.ser.ProgramOrScratchedSerializer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class BrisnetCsvMapper extends CsvMapper {

    private static final DateTimeFormatter YYYYMMDD =
            DateTimeFormatter.ofPattern("yyyyMMdd", Locale.US);
    private static final DateTimeFormatter HHHMM =
            DateTimeFormatter.ofPattern("0HHmm", Locale.US);
    private static final LocalDateDeserializer YYYYMMDD_DESERIALIZER =
            new LocalDateDeserializer(YYYYMMDD);
    private static final LocalDateSerializer YYYYMMDD_SERIALIZER =
            new LocalDateSerializer(YYYYMMDD);
    private static final LocalTimeDeserializer HHHMM_DESERIALIZER =
            new LocalTimeDeserializer(HHHMM);
    private static final LocalTimeSerializer HHHMM_SERIALIZER = new LocalTimeSerializer(HHHMM);

    public BrisnetCsvMapper() {
        super();

        SimpleModule simpleLocalDateModule = new SimpleModule();
        simpleLocalDateModule.addDeserializer(LocalDate.class, YYYYMMDD_DESERIALIZER);
        simpleLocalDateModule.addSerializer(LocalDate.class, YYYYMMDD_SERIALIZER);
        simpleLocalDateModule.addDeserializer(LocalTime.class, HHHMM_DESERIALIZER);
        simpleLocalDateModule.addSerializer(LocalTime.class, HHHMM_SERIALIZER);

        enable(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS)
                .disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                // adds JDK 8 Parameter Name access for cleaner JSON-to-Object mapping
                .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES))
                .registerModule(simpleLocalDateModule)
                .registerModule(unwrappingBeanSerializerModifierModule());
    }

    private Module unwrappingBeanSerializerModifierModule() {
        return new Module() {
            @Override
            public String getModuleName() {
                return "my.module";
            }

            @Override
            public Version version() {
                return Version.unknownVersion();
            }

            @Override
            public void setupModule(SetupContext setupContext) {
                // age, sex, restrictions
                setupContext.addBeanSerializerModifier(ageSexRestrictionsBeanSerializerModifier());

                // program number or scratched
                setupContext.addBeanSerializerModifier(programOrScratchedBeanSerializerModifier());
            }
        };
    }

    private BeanSerializerModifier ageSexRestrictionsBeanSerializerModifier() {
        return new BeanSerializerModifier() {
            @Override
            public JsonSerializer<?> modifySerializer(SerializationConfig config,
                    BeanDescription beanDesc, JsonSerializer<?> serializer) {
                if (beanDesc.getBeanClass().equals(AgeSexRestriction.class)) {
                    return new AgeSexRestrictionSerializer(
                            (BeanSerializerBase) serializer, NameTransformer.NOP);
                }
                return serializer;
            }
        };
    }

    private BeanSerializerModifier programOrScratchedBeanSerializerModifier() {
        return new BeanSerializerModifier() {
            @Override
            public JsonSerializer<?> modifySerializer(SerializationConfig config,
                    BeanDescription beanDesc, JsonSerializer<?> serializer) {
                if (beanDesc.getBeanClass().equals(ProgramOrScratched.class)) {
                    return new ProgramOrScratchedSerializer(
                            (BeanSerializerBase) serializer, NameTransformer.NOP);
                }
                return serializer;
            }
        };
    }
}
