package com.robinhowlett.parsers.brisnet.files;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.robinhowlett.data.DistanceSurfaceTrackRecord.TrackCondition;
import com.robinhowlett.parsers.brisnet.converters.AboutCharConverter;
import com.robinhowlett.parsers.brisnet.converters.AllWeatherCharConverter;
import com.robinhowlett.parsers.brisnet.converters.ChuteStartCharConverter;
import com.robinhowlett.parsers.brisnet.converters.OffTurfCharConverter;
import com.robinhowlett.parsers.brisnet.converters.StateBredCharConverter;
import com.robinhowlett.parsers.brisnet.converters.TrackConditionConverter.StringToTrackCondition;
import com.robinhowlett.parsers.brisnet.converters.TrackConditionConverter.TrackConditionToString;
import com.robinhowlett.parsers.brisnet.ser.AgeSexRestrictionDeserializer;
import com.robinhowlett.parsers.brisnet.ser.BooleanDeserializer;
import com.robinhowlett.parsers.brisnet.ser.FourDecimalPaddedSerializer;
import com.robinhowlett.parsers.brisnet.ser.OffTurfDistanceChangeDeserializer;
import com.robinhowlett.parsers.brisnet.ser.OffTurfDistanceChangeSerializer;
import com.robinhowlett.parsers.brisnet.ser.TwoDecimalPaddedSerializer;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
public class RaceFile {

    private String trackCode;
    private LocalDate date;
    private Integer raceNumber;
    private DayEvening dayEvening;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double distance;
    private DistanceUnit distanceUnit;
    @JsonDeserialize(using = BooleanDeserializer.class)
    @JsonSerialize(converter = AboutCharConverter.class)
    private boolean about;
    // uses the same surface code ("D") for both Dirt and All-Weather surfaces.
    private SurfaceOld surfaceOld;
    // uses a different surface code ("A") for All-Weather surfaces.
    private SurfaceNew surfaceNew;
    @JsonInclude(NON_DEFAULT)
    private String reserved10;
    // a flag indicating whether the race was run on an All-Weather surface.
    @JsonDeserialize(using = BooleanDeserializer.class)
    @JsonSerialize(converter = AllWeatherCharConverter.class)
    private boolean allWeather;
    @JsonDeserialize(using = BooleanDeserializer.class)
    @JsonSerialize(converter = ChuteStartCharConverter.class)
    private boolean chuteStart;
    private String brisRaceType;
    private String eqbRaceType;
    private int grade;
    @JsonDeserialize(using = AgeSexRestrictionDeserializer.class)
    // cannot declare both @JsonUnwrapped and a custom serializer, so see how
    // AgeSexRestrictionSerializer is declared as a module
    private AgeSexRestriction ageSexRestriction;
    private String raceRestrictions;
    @JsonDeserialize(using = BooleanDeserializer.class)
    @JsonSerialize(converter = StateBredCharConverter.class)
    private boolean stateBred;
    private String abbrevRaceClass;
    private String breed;
    private String raceCountryCode;
    private Integer purse;
    private Integer totalValueOfRace;
    @JsonInclude(NON_DEFAULT)
    private String reserved24;
    @JsonInclude(NON_DEFAULT)
    private String reserved25;
    @JsonInclude(NON_DEFAULT)
    private String reserved26;
    @JsonInclude(NON_DEFAULT)
    private String reserved27;
    private Integer maxClaimingPrice;
    @JsonInclude(NON_DEFAULT)
    private String reserved29;
    private String raceConditionsText1;
    private String raceConditionsText2;
    private String raceConditionsText3;
    private String raceConditionsText4;
    private String raceConditionsText5;
    @JsonInclude(NON_DEFAULT)
    private String reserved35;
    @JsonInclude(NON_DEFAULT)
    private String reserved36;
    private Integer fieldSize;
    @JsonDeserialize(converter = StringToTrackCondition.class)
    @JsonSerialize(converter = TrackConditionToString.class)
    private TrackCondition trackCondition;
    private Double fraction1;
    private Double fraction2;
    private Double fraction3;
    private Double fraction4;
    private Double fraction5;
    private Double finalTime;
    private Integer fraction1Distance;
    private Integer fraction2Distance;
    private Integer fraction3Distance;
    private Integer fraction4Distance;
    private Integer fraction5Distance;
    private LocalTime offTime;
    private Integer startCallDistanceYards;
    private Integer pointOfCall1DistanceYards;
    private Integer pointOfCall2DistanceYards;
    private Integer pointOfCall3DistanceYards;
    private String raceName;
    private String startDescription;
    private Integer tempRailDistanceFeet;
    @JsonDeserialize(using = BooleanDeserializer.class)
    @JsonSerialize(converter = OffTurfCharConverter.class)
    private boolean offTurf;
    @JsonDeserialize(using = OffTurfDistanceChangeDeserializer.class)
    @JsonSerialize(using = OffTurfDistanceChangeSerializer.class)
    @JsonInclude(NON_DEFAULT)
    private Boolean offTurfDistanceChange;
    @JsonInclude(NON_DEFAULT)
    private String reserved60;
    @JsonInclude(NON_DEFAULT)
    private String reserved61;
    @JsonInclude(NON_DEFAULT)
    private String reserved62;
    private String weather;
    private String raceTemperature;
    @JsonSerialize(using = FourDecimalPaddedSerializer.class)
    private Double wpsPool;
    private Integer runUpDistanceFeet;
    @JsonInclude(NON_DEFAULT)
    private String reserved67;
    @JsonInclude(NON_DEFAULT)
    private String reserved68;
    @JsonInclude(NON_DEFAULT)
    private String reserved69;
    @JsonInclude(NON_DEFAULT)
    private String reserved70;
    @JsonInclude(NON_DEFAULT)
    private String reserved71;
    @JsonInclude(NON_DEFAULT)
    private String reserved72;
    @JsonInclude(NON_DEFAULT)
    private String reserved73;
    @JsonInclude(NON_DEFAULT)
    private String reserved74;
    @JsonInclude(NON_DEFAULT)
    private String reserved75;
    @JsonInclude(NON_DEFAULT)
    private String reserved76;
    @JsonInclude(NON_DEFAULT)
    private String reserved77;
    @JsonInclude(NON_DEFAULT)
    private String reserved78;
    @JsonInclude(NON_DEFAULT)
    private String reserved79;
    @JsonInclude(NON_DEFAULT)
    private String reserved80;
    @JsonInclude(NON_DEFAULT)
    private String reserved81;
    @JsonInclude(NON_DEFAULT)
    private String reserved82;
    @JsonInclude(NON_DEFAULT)
    private String reserved83;
    @JsonInclude(NON_DEFAULT)
    private String reserved84;
    @JsonInclude(NON_DEFAULT)
    private String reserved85;
    @JsonInclude(NON_DEFAULT)
    private String reserved86;
    @JsonInclude(NON_DEFAULT)
    private String reserved87;
    @JsonInclude(NON_DEFAULT)
    private String reserved88;
    @JsonInclude(NON_DEFAULT)
    private String reserved89;
    @JsonInclude(NON_DEFAULT)
    private String reserved90;
    @JsonInclude(NON_DEFAULT)
    private String reserved91;
    @JsonInclude(NON_DEFAULT)
    private String reserved92;
    @JsonInclude(NON_DEFAULT)
    private String reserved93;
    @JsonInclude(NON_DEFAULT)
    private String reserved94;
    @JsonInclude(NON_DEFAULT)
    private String reserved95;
    @JsonInclude(NON_DEFAULT)
    private String reserved96;
    @JsonInclude(NON_DEFAULT)
    private String reserved97;
    @JsonInclude(NON_DEFAULT)
    private String reserved98;
    @JsonInclude(NON_DEFAULT)
    private String reserved99;

    @JsonIgnore
    public String getRaceConditions() {
        String raceConditions = null;
        if (raceConditionsText1 != null && !raceConditionsText1.isEmpty()) {
            raceConditions = raceConditionsText1;
        }
        if (raceConditionsText2 != null && !raceConditionsText2.isEmpty()) {
            raceConditions = raceConditions.concat(raceConditionsText2);
        }
        if (raceConditionsText3 != null && !raceConditionsText3.isEmpty()) {
            raceConditions = raceConditions.concat(raceConditionsText3);
        }
        if (raceConditionsText4 != null && !raceConditionsText4.isEmpty()) {
            raceConditions = raceConditions.concat(raceConditionsText4);
        }
        if (raceConditionsText5 != null && !raceConditionsText5.isEmpty()) {
            raceConditions = raceConditions.concat(raceConditionsText5);
        }
        return raceConditions;
    }

    @JsonIgnore
    public int getDistanceInFeet() {
        switch (getDistanceUnit()) {
            case YARDS:
                return (int) (getDistance() * 3);
            case METERS:
                return (int) (getDistance() * 3.280839895);
            case FURLONGS:
                return (int) (getDistance() * 660);
            default:
                // TODO custom exception
                throw new RuntimeException("unknown distance unit");
        }
    }

    public enum DayEvening {
        DAY('D'),
        EVENING('E');

        private char flag;

        DayEvening(char flag) {
            this.flag = flag;
        }

        @JsonCreator
        public static DayEvening lookup(char code) {
            switch (code) {
                case 'E':
                    return EVENING;
                case 'D':
                    return DAY;
            }
            return null;
        }

        @JsonValue
        public char getFlag() {
            return flag;
        }
    }

    public enum DistanceUnit {
        YARDS('Y'),
        METERS('M'),
        FURLONGS('F');

        private char unit;

        DistanceUnit(char unit) {
            this.unit = unit;
        }

        @JsonCreator
        public static DistanceUnit lookup(char unit) {
            switch (unit) {
                case 'Y':
                    return YARDS;
                case 'M':
                    return METERS;
                case 'F':
                    return FURLONGS;
            }
            return null;
        }

        @JsonValue
        public char getUnit() {
            return unit;
        }
    }

    public enum SurfaceOld {
        DIRT_OR_ALL_WEATHER('D'),
        INNER_DIRT('d'),
        MAIN_TURF('T'),
        INNER_TURF('t');

        private char flag;

        SurfaceOld(char flag) {
            this.flag = flag;
        }

        @JsonCreator
        public static SurfaceOld lookup(char unit) {
            switch (unit) {
                case 'D':
                    return DIRT_OR_ALL_WEATHER;
                case 'd':
                    return INNER_DIRT;
                case 'T':
                    return MAIN_TURF;
                case 't':
                    return INNER_TURF;
            }
            return null;
        }

        @JsonValue
        public char getFlag() {
            return flag;
        }
    }

    public enum SurfaceNew {
        MAIN_ALL_WEATHER('A', "All Weather Track"),
        DIRT('D', "Dirt"),
        INNER_DIRT('d', "Inner track"),
        MAIN_TURF('T', "Turf"),
        INNER_TURF('t', "Inner turf");

        private char flag;
        private String description;

        SurfaceNew(char flag, String description) {
            this.flag = flag;
            this.description = description;
        }

        @JsonCreator
        public static SurfaceNew lookup(char unit) {
            switch (unit) {
                case 'A':
                    return MAIN_ALL_WEATHER;
                case 'D':
                    return DIRT;
                case 'd':
                    return INNER_DIRT;
                case 'T':
                    return MAIN_TURF;
                case 't':
                    return INNER_TURF;
            }
            return null;
        }

        @JsonValue
        public char getFlag() {
            return flag;
        }

        @JsonIgnore
        public String getDescription() {
            return description;
        }
    }

    public enum Age {
        TWO_YEAR_OLDS('A'),
        THREE_YEAR_OLDS('B'),
        FOUR_YEAR_OLDS('C'),
        FIVE_YEAR_OLDS('D'),
        THREE_AND_FOUR_YEAR_OLDS('E'),
        FOUR_AND_FIVE_YEAR_OLDS('F'),
        THREE_FOUR_AND_FIVE_YEAR_OLDS('G'),
        ALL_AGES('H');

        private char flag;

        Age(char flag) {
            this.flag = flag;
        }

        public static Age lookup(char unit) {
            switch (unit) {
                case 'A':
                    return TWO_YEAR_OLDS;
                case 'B':
                    return THREE_YEAR_OLDS;
                case 'C':
                    return FOUR_YEAR_OLDS;
                case 'D':
                    return FIVE_YEAR_OLDS;
                case 'E':
                    return THREE_AND_FOUR_YEAR_OLDS;
                case 'F':
                    return FOUR_AND_FIVE_YEAR_OLDS;
                case 'G':
                    return THREE_FOUR_AND_FIVE_YEAR_OLDS;
                case 'H':
                    return ALL_AGES;
            }
            return null;
        }

        @JsonValue
        public char getFlag() {
            return flag;
        }
    }

    public enum Range {
        ONLY('O'),
        AND_UP('U');

        private char flag;

        Range(char flag) {
            this.flag = flag;
        }

        @JsonCreator
        public static Range lookup(char unit) {
            switch (unit) {
                case 'O':
                    return ONLY;
                case 'U':
                    return AND_UP;
            }
            return null;
        }

        @JsonValue
        public char getFlag() {
            return flag;
        }
    }

    public enum Sex {
        NO_SEX_RESTRICTIONS('N'),
        MARES_AND_FILLIES_ONLY('M'),
        COLTS_AND_OR_GELDINGS_ONLY('C'),
        FILLIES_ONLY('F');

        private char flag;

        Sex(char flag) {
            this.flag = flag;
        }

        @JsonCreator
        public static Sex lookup(char unit) {
            switch (unit) {
                case 'N':
                    return NO_SEX_RESTRICTIONS;
                case 'M':
                    return MARES_AND_FILLIES_ONLY;
                case 'C':
                    return COLTS_AND_OR_GELDINGS_ONLY;
                case 'F':
                    return FILLIES_ONLY;
            }
            return null;
        }

        @JsonValue
        public char getFlag() {
            return flag;
        }
    }

    @Data
    public static class AgeSexRestriction {
        private final Age age;
        private final Range range;
        private final Sex sex;

        @JsonIgnore
        public String value() {
            return String.valueOf(age.getFlag()).concat(
                    String.valueOf(range.getFlag()).concat(
                            String.valueOf(sex.getFlag())
                    )
            );
        }
    }

}
