package com.robinhowlett.parsers.brisnet.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.robinhowlett.data.DistanceSurfaceTrackRecord.TrackCondition;

public class TrackConditionConverter {

    public static class TrackConditionToString extends StdConverter<TrackCondition, String> {
        @Override
        public String convert(TrackCondition trackCondition) {
            return trackCondition.getCode();
        }
    }

    public static class StringToTrackCondition extends StdConverter<String, TrackCondition> {
        @Override
        public TrackCondition convert(String code) {
            return TrackCondition.lookup(code);
        }
    }
}
