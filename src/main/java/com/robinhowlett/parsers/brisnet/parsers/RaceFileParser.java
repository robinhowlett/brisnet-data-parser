package com.robinhowlett.parsers.brisnet.parsers;

import com.robinhowlett.data.Breed;
import com.robinhowlett.data.DistanceSurfaceTrackRecord.RaceDistance;
import com.robinhowlett.data.FractionalPoint.Fractional;
import com.robinhowlett.data.PointsOfCall.PointOfCall;
import com.robinhowlett.data.RaceResult;
import com.robinhowlett.parsers.brisnet.files.RaceFile;
import com.robinhowlett.parsers.brisnet.files.RaceFile.Age;
import com.robinhowlett.parsers.brisnet.files.RaceFile.Range;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import static com.robinhowlett.data.RaceRestrictions.ALL_SEXES;

public class RaceFileParser {

    public static List<RaceResult> parse(RaceFile raceFile) {
        List<RaceResult> raceResults = new ArrayList<>();

        return raceResults;
    }

    public static MinMaxAgeAndSexes parseAgeSexRestriction(RaceFile.AgeSexRestriction
            ageSexRestriction) {
        Age age = ageSexRestriction.getAge();
        Range ageRange = ageSexRestriction.getRange();
        RaceFile.Sex sex = ageSexRestriction.getSex();

        MinMaxAgeAndSexes minMaxAgeAndSexes = new MinMaxAgeAndSexes(null, null);
        if (age != null) {
            switch (age) {
                case TWO_YEAR_OLDS:
                    minMaxAgeAndSexes = new MinMaxAgeAndSexes(2, 2);
                    break;
                case THREE_YEAR_OLDS:
                    minMaxAgeAndSexes = new MinMaxAgeAndSexes(3, 3);
                    break;
                case FOUR_YEAR_OLDS:
                    minMaxAgeAndSexes = new MinMaxAgeAndSexes(4, 4);
                    break;
                case FIVE_YEAR_OLDS:
                    minMaxAgeAndSexes = new MinMaxAgeAndSexes(5, 5);
                    break;
                case THREE_AND_FOUR_YEAR_OLDS:
                    minMaxAgeAndSexes = new MinMaxAgeAndSexes(3, 4);
                    break;
                case FOUR_AND_FIVE_YEAR_OLDS:
                    minMaxAgeAndSexes = new MinMaxAgeAndSexes(4, 5);
                    break;
                case THREE_FOUR_AND_FIVE_YEAR_OLDS:
                    minMaxAgeAndSexes = new MinMaxAgeAndSexes(3, 5);
                    break;
                case ALL_AGES:
                    minMaxAgeAndSexes = new MinMaxAgeAndSexes(2, -1);
            }
        }

        if (minMaxAgeAndSexes.getMinAge() != null && ageRange != null) {
            switch (ageRange) {
                case AND_UP:
                    minMaxAgeAndSexes.setMaxAge(-1);
                    break;
                case ONLY:
                    minMaxAgeAndSexes.setMaxAge(minMaxAgeAndSexes.getMinAge());
                    break;
            }
        }

        if (sex != null) {
            switch (sex) {
                case NO_SEX_RESTRICTIONS:
                    minMaxAgeAndSexes.setSexes(ALL_SEXES);
                    break;
                case COLTS_AND_OR_GELDINGS_ONLY:
                    minMaxAgeAndSexes.setSexes(3);
                    break;
                case FILLIES_ONLY:
                    minMaxAgeAndSexes.setSexes(8);
                    break;
                case MARES_AND_FILLIES_ONLY:
                    minMaxAgeAndSexes.setSexes(24);
                    break;
            }
        }

        return minMaxAgeAndSexes;
    }

    public static List<Fractional> getFractionals(
            boolean about, Breed breed,
            Integer fraction1Distance, Double fraction1,
            Integer fraction2Distance, Double fraction2,
            Integer fraction3Distance, Double fraction3,
            Integer fraction4Distance, Double fraction4,
            Integer fraction5Distance, Double fraction5,
            int distanceInFeet, Double finalTime) {
        List<Fractional> fractionals = new ArrayList<>();
        if (fraction1Distance != null && fraction1Distance > 0 &&
                fraction1 != null && fraction1 > 0) {
            RaceDistance distance = RaceDistance.fromFeet(fraction1Distance * 3, about, breed);
            fractionals.add(
                    new Fractional(1, distance.getText(), distance.getCompact(),
                            fraction1Distance * 3,
                            (long) (fraction1 * 1000)));
        }
        if (fraction2Distance != null && fraction2Distance > 0 &&
                fraction2 != null && fraction2 > 0) {
            RaceDistance distance = RaceDistance.fromFeet(fraction2Distance * 3, about, breed);
            fractionals.add(new Fractional(2, distance.getText(), distance.getCompact(),
                    fraction2Distance * 3,
                    (long) (fraction2 * 1000)));
        }
        if (fraction3Distance != null && fraction3Distance > 0 &&
                fraction3 != null && fraction3 > 0) {
            RaceDistance distance = RaceDistance.fromFeet(fraction3Distance * 3, about, breed);
            fractionals.add(new Fractional(3, distance.getText(), distance.getCompact(),
                    fraction3Distance * 3,
                    (long) (fraction3 * 1000)));
        }
        if (fraction4Distance != null && fraction4Distance > 0 &&
                fraction4 != null && fraction4 > 0) {
            RaceDistance distance = RaceDistance.fromFeet(fraction4Distance * 3, about, breed);
            fractionals.add(new Fractional(4, distance.getText(), distance.getCompact(),
                    fraction4Distance * 3,
                    (long) (fraction4 * 1000)));
        }
        if (fraction5Distance != null && fraction5Distance > 0 &&
                fraction5 != null && fraction5 > 0) {
            RaceDistance distance = RaceDistance.fromFeet(fraction5Distance * 3, about, breed);
            fractionals.add(new Fractional(5, distance.getText(), distance.getCompact(),
                    fraction5Distance * 3,
                    (long) (fraction5 * 1000)));
        }
        if (distanceInFeet > 0 && finalTime != null && finalTime > 0) {
            RaceDistance distance = RaceDistance.fromFeet(distanceInFeet, about, breed);
            fractionals.add(new Fractional(6, distance.getText(), distance.getCompact(),
                    distanceInFeet,
                    (long) (finalTime * 1000)));
        }
        return fractionals;
    }

    public static List<PointOfCall> getPointsOfCall(boolean about, Breed breed,
            Integer startCallDistanceYards, Integer pointOfCall1DistanceYards,
            Integer pointOfCall2DistanceYards, Integer pointOfCall3DistanceYards,
            int distanceInFeet) {
        List<PointOfCall> pointsOfCall = new ArrayList<>();

        if (startCallDistanceYards != null && startCallDistanceYards > 0) {
            PointOfCall start = new PointOfCall(1, "Start", "Start", startCallDistanceYards * 3);
            pointsOfCall.add(start);
        }
        if (pointOfCall1DistanceYards != null && pointOfCall1DistanceYards > 0) {
            int feet = pointOfCall1DistanceYards * 3;
            RaceDistance distance = RaceDistance.fromFeet(feet, about, breed);
            PointOfCall call = new PointOfCall(2, distance.getAlt(), distance.getCompact(), feet);
            pointsOfCall.add(call);
        }
        if (pointOfCall2DistanceYards != null && pointOfCall2DistanceYards > 0) {
            int feet = pointOfCall2DistanceYards * 3;
            RaceDistance distance = RaceDistance.fromFeet(feet, about, breed);
            PointOfCall call = new PointOfCall(3, distance.getAlt(), distance.getCompact(), feet);
            pointsOfCall.add(call);
        }
        if (pointOfCall3DistanceYards != null && pointOfCall3DistanceYards > 0) {
            int feet = pointOfCall3DistanceYards * 3;
            RaceDistance distance = RaceDistance.fromFeet(feet, about, breed);
            PointOfCall call = new PointOfCall(4, distance.getAlt(), distance.getCompact(), feet);
            pointsOfCall.add(call);
        }

        String compact = "Str";
        Integer stretchFeet = null;
        if (distanceInFeet >= 1320) {
            stretchFeet = distanceInFeet - 660;
            RaceDistance distance = RaceDistance.fromFeet(stretchFeet, false, breed);
            String stretchFurlongs = String.format("%.2ff", distance.getFurlongs());
            compact = (distance.getCompact() != null ? distance.getCompact() : stretchFurlongs);
        }
        PointOfCall stretch = new PointOfCall(5, "Str", compact, stretchFeet);
        pointsOfCall.add(stretch);

        RaceDistance distance = RaceDistance.fromFeet(distanceInFeet, about, breed);
        PointOfCall finish = new PointOfCall(6, "Fin", distance.getCompact(), distanceInFeet);
        pointsOfCall.add(finish);

        return pointsOfCall;
    }

    @Data
    static class MinMaxAgeAndSexes {
        private final Integer minAge;
        private Integer maxAge;
        private Integer sexes = 0;

        public MinMaxAgeAndSexes(Integer minAge, Integer maxAge) {
            this.minAge = minAge;
            this.maxAge = maxAge;
        }
    }
}
