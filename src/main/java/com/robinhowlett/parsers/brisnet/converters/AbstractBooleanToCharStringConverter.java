package com.robinhowlett.parsers.brisnet.converters;

import com.fasterxml.jackson.databind.util.StdConverter;

public abstract class AbstractBooleanToCharStringConverter extends StdConverter<Boolean, String> {

    private final char character;

    public AbstractBooleanToCharStringConverter(char character) {
        this.character = character;
    }

    @Override
    public String convert(Boolean aBoolean) {
        return (aBoolean ? String.valueOf(character) : "");
    }
}
