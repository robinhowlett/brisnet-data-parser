package com.robinhowlett.parsers.brisnet.files;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.robinhowlett.data.running_line.MedicationEquipment.Equipment;
import com.robinhowlett.data.running_line.MedicationEquipment.Medication;
import com.robinhowlett.parsers.brisnet.converters.EquipmentCodeConverter
        .EquipmentToStringConverter;
import com.robinhowlett.parsers.brisnet.converters.EquipmentCodeConverter
        .StringToEquipmentConverter;
import com.robinhowlett.parsers.brisnet.converters.MedicationCodeConverter
        .MedicationToStringConverter;
import com.robinhowlett.parsers.brisnet.converters.MedicationCodeConverter
        .StringToMedicationConverter;
import com.robinhowlett.parsers.brisnet.converters.YesCharConverter;
import com.robinhowlett.parsers.brisnet.converters.YesNoCharConverter.YesNoBooleanToString;
import com.robinhowlett.parsers.brisnet.converters.YesNoCharConverter.YesNoStringToBoolean;
import com.robinhowlett.parsers.brisnet.files.RaceFile.DayEvening;
import com.robinhowlett.parsers.brisnet.ser.BooleanDeserializer;
import com.robinhowlett.parsers.brisnet.ser.DeadHeatSerializer;
import com.robinhowlett.parsers.brisnet.ser.OneZeroDeserializer;
import com.robinhowlett.parsers.brisnet.ser.OneZeroSerializer;
import com.robinhowlett.parsers.brisnet.ser.ProgramOrScratchedDeserializer;
import com.robinhowlett.parsers.brisnet.ser.TwoDecimalPaddedSerializer;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
public class StartFile {

    private String trackCode;
    private LocalDate date;
    private Integer raceNumber;
    private DayEvening dayEvening;
    private String horseName;
    // if not US, CAN, or PR
    private String foreignBredCode;
    private String stateBredCode;
    private Integer postPosition;
    @JsonDeserialize(using = ProgramOrScratchedDeserializer.class)
    // cannot declare both @JsonUnwrapped and a custom serializer, so see how
    // ProgramOrScratchedSerializer is declared as a module
    private ProgramOrScratched programOrScratched;
    private Integer yearOfBirth;
    private String breed;
    private String coupledFlag;
    private String abbrevJockeyName;
    private String jockeyLastName;
    private String jockeyFirstName;
    private String jockeyMiddleName;
    @JsonInclude(NON_DEFAULT)
    private String reserved17;
    private String abbrevTrainerName;
    private String trainerLastName;
    private String trainerFirstName;
    private String trainerMiddleName;
    private String tripComment;
    @JsonInclude(NON_DEFAULT)
    private String reserved23;
    private String ownerNames;
    private String ownerFirstName;
    private String ownerMiddleName;
    private Integer claimingPrice;
    @JsonDeserialize(converter = StringToMedicationConverter.class)
    @JsonSerialize(converter = MedicationToStringConverter.class)
    private List<Medication> medicationCodes;
    @JsonDeserialize(converter = StringToEquipmentConverter.class)
    @JsonSerialize(converter = EquipmentToStringConverter.class)
    private List<Equipment> equipmentCodes;
    private Integer earningsUSD;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double odds;
    @JsonDeserialize(using = BooleanDeserializer.class)
    @JsonSerialize(converter = YesCharConverter.class)
    private boolean nonBettingFlag;
    @JsonDeserialize(using = OneZeroDeserializer.class)
    @JsonSerialize(using = OneZeroSerializer.class)
    private boolean favoriteFlag;
    @JsonInclude(NON_DEFAULT)
    private String reserved34;
    @JsonInclude(NON_DEFAULT)
    private String reserved35;
    @JsonDeserialize(using = BooleanDeserializer.class)
    @JsonSerialize(converter = YesCharConverter.class)
    private boolean dqFlag;
    private Integer dqPlacing = 0;
    private Integer weight;
    @JsonDeserialize(using = BooleanDeserializer.class)
    @JsonSerialize(converter = YesCharConverter.class)
    private boolean correctedWeight;
    // ow = weight carried - program weight
    private Integer overweightAmount;
    @JsonDeserialize(converter = YesNoStringToBoolean.class)
    @JsonSerialize(converter = YesNoBooleanToString.class)
    private boolean claimed;
    private String claimedByAbbrevTrainerName;
    private String claimedByTrainerLastName;
    private String claimedByTrainerFirstName;
    private String claimedByTrainerMiddleName;
    private String reserved46;
    private String claimedByAbbrevOwnerName;
    private String claimedByOwnerLastName;
    private String claimedByOwnerFirstName;
    private String claimedByOwnerMiddleName;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double winPayoff;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double placePayoff;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double showPayoff;
    @JsonInclude(NON_DEFAULT)
    private String reserved54;
    private Integer startCallPosition;
    private Integer call1Position;
    private Integer call2Position;
    private Integer call3Position;
    private Integer stretchPosition;
    private Integer finishPosition;
    private Integer officialPosition;
    // used for leader only
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double startCallLengthsAhead;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double call1LengthsAhead;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double call2LengthsAhead;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double call3LengthsAhead;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double stretchLengthsAhead;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double finishLengthsAhead;
    // leader = 0
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double startCallLengthsBehind;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double call1LengthsBehind;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double call2LengthsBehind;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double call3LengthsBehind;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double stretchLengthsBehind;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double finishLengthsBehind;
    // lengths ahead of closest trailing horse
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double startCallMargin;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double call1Margin;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double call2Margin;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double call3Margin;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double stretchMargin;
    @JsonSerialize(using = TwoDecimalPaddedSerializer.class)
    private Double finishMargin;
    @JsonDeserialize(using = BooleanDeserializer.class)
    @JsonSerialize(using = DeadHeatSerializer.class)
    private boolean deadHeat;
    private String horseRegID;
    private String jockeyID;
    private String trainerID;
    private String ownerID;
    @JsonInclude(NON_DEFAULT)
    private String claimedByNewTrainerID;
    @JsonInclude(NON_DEFAULT)
    private String claimedByNewOwnerID;
    private Integer eqbReferenceNumber;
    @JsonDeserialize(using = BooleanDeserializer.class)
    @JsonSerialize(converter = YesCharConverter.class)
    private boolean voidIndicator;
    // TODO enum?
    private String voidReason;
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

//    public enum MedicationCode {
//        ADJUNCT_BLEEDER_MEDICATION('A'),
//        BUTE('B'),
//        FIRST_TIME_BUTE('C'),
//        LASIX('L'),
//        FIRST_LASIX('M');
//
//        private char flag;
//
//        MedicationCode(char flag) {
//            this.flag = flag;
//        }
//
//        @JsonCreator
//        public static MedicationCode lookup(char unit) {
//            switch (unit) {
//                case 'A':
//                    return ADJUNCT_BLEEDER_MEDICATION;
//                case 'B':
//                    return BUTE;
//                case 'C':
//                    return FIRST_TIME_BUTE;
//                case 'L':
//                    return LASIX;
//                case 'M':
//                    return FIRST_LASIX;
//            }
//            return null;
//        }
//
//        @JsonValue
//        public char getFlag() {
//            return flag;
//        }
//
//        public String getFlagAsString() {
//            return String.valueOf(flag);
//        }
//    }
//
//    public enum Equipment {
//        RUNNING_W_S('1'),
//        SCREENS('2'),
//        SHIELDS('3'),
//        ALUMINUM_PADS('A'),
//        BLINKERS('B'),
//        MUD_CALKS('C'),
//        GLUED_SHOES('D'),
//        INNER_RIMS('E'),
//        FRONT_BANDAGES('F'),
//        GOGGLES('G'),
//        OUTER_RIMS('H'),
//        INSERTS('I'),
//        ALUMINUM_PAD('J'),
//        FLIPPING_HALTER('K'),
//        BAR_SHOES('L'),
//        BLOCKS('M'),
//        NO_WHIP('N'),
//        BLINKERS_OFF('O'),
//        PADS('P'),
//        NASAL_STRIP_OFF('Q'),
//        BAR_SHOE('R'),
//        NASAL_STRIP('S'),
//        TURNDOWNS('T'),
//        SPURS('U'),
//        QUEENS_PLATES('W'),
//        NO_SHOES('Y'),
//        TONGUE_TIE('Z');
//
//        private char flag;
//
//        Equipment(char flag) {
//            this.flag = flag;
//        }
//
//        @JsonCreator
//        public static Equipment lookup(char unit) {
//            switch (unit) {
//                case '1':
//                    return RUNNING_W_S;
//                case '2':
//                    return SCREENS;
//                case '3':
//                    return SHIELDS;
//                case 'A':
//                    return ALUMINUM_PADS;
//                case 'B':
//                    return BLINKERS;
//                case 'C':
//                    return MUD_CALKS;
//                case 'D':
//                    return GLUED_SHOES;
//                case 'E':
//                    return INNER_RIMS;
//                case 'F':
//                    return FRONT_BANDAGES;
//                case 'G':
//                    return GOGGLES;
//                case 'H':
//                    return OUTER_RIMS;
//                case 'I':
//                    return INSERTS;
//                case 'J':
//                    return ALUMINUM_PAD;
//                case 'K':
//                    return LASIX;
//                case 'L':
//                    return FIRST_LASIX;
//                case 'M':
//                    return LASIX;
//                case 'N':
//                    return FIRST_LASIX;
//                case 'O':
//                    return LASIX;
//                case 'P':
//                    return FIRST_LASIX;
//                case 'Q':
//                    return LASIX;
//                case 'R':
//                    return FIRST_LASIX;
//                case 'S':
//                    return FIRST_LASIX;
//                case 'T':
//                    return LASIX;
//                case 'U':
//                    return FIRST_LASIX;
//                case 'V':
//                    return FIRST_LASIX;
//                case 'W':
//                    return LASIX;
//                case 'Y':
//                    return FIRST_LASIX;
//                case 'Z':
//                    return FIRST_LASIX;
//            }
//            return null;
//        }
//
//        public static List<Equipment> parse(String equipment) {
//
//        }
//
//        @JsonValue
//        public char getFlag() {
//            return flag;
//        }
//
//        public String getFlagAsString() {
//            return String.valueOf(flag);
//        }
//    }

    @EqualsAndHashCode
    @ToString
    public static class ProgramOrScratched {
        @Getter
        private final String program;
        @Getter
        private final boolean scratched;

        @JsonCreator
        public ProgramOrScratched(String program) {
            if (program != null && program.equals("SCR")) {
                this.scratched = true;
                this.program = null;
            } else {
                this.scratched = false;
                this.program = program;
            }
        }
    }

}
