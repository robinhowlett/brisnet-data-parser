package com.robinhowlett.parsers.brisnet.converters;

import com.fasterxml.jackson.databind.util.StdConverter;

public class YesNoCharConverter {

    public static class YesNoBooleanToString extends StdConverter<Boolean, String> {
        @Override
        public String convert(Boolean aBoolean) {
            return (aBoolean ? "Y" : "N");
        }

    }

    public static class YesNoStringToBoolean extends StdConverter<String, Boolean> {
        @Override
        public Boolean convert(String s) {
            return (s != null && s.equals("Y"));
        }
    }
}
