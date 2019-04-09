package com.robinhowlett.parsers.brisnet.parsers;

import com.robinhowlett.data.*;
import com.robinhowlett.data.DistanceSurfaceTrackRecord.RaceDistance;
import com.robinhowlett.data.FractionalPoint.Fractional;
import com.robinhowlett.data.PointsOfCall.PointOfCall;
import com.robinhowlett.data.PointsOfCall.PointOfCall.RelativePosition;
import com.robinhowlett.data.PointsOfCall.PointOfCall.RelativePosition.Lengths;
import com.robinhowlett.data.PointsOfCall.PointOfCall.RelativePosition.LengthsAhead;
import com.robinhowlett.data.PointsOfCall.PointOfCall.RelativePosition.TotalLengthsBehind;
import com.robinhowlett.data.RaceConditions.ClaimingPriceRange;
import com.robinhowlett.data.brisnet.BrisnetRaceResult;
import com.robinhowlett.data.running_line.MedicationEquipment;
import com.robinhowlett.data.running_line.Odds;
import com.robinhowlett.data.running_line.Weight;
import com.robinhowlett.data.wagering.WagerPayoffPools;
import com.robinhowlett.data.wagering.WagerPayoffPools.ExoticPayoffPool;
import com.robinhowlett.data.wagering.WagerPayoffPools.WinPlaceShowPayoffPool;
import com.robinhowlett.data.wagering.WagerPayoffPools.WinPlaceShowPayoffPool.WinPlaceShowPayoff;
import com.robinhowlett.parsers.brisnet.files.ComprehensiveChartFile;
import com.robinhowlett.parsers.brisnet.files.RaceFile;
import com.robinhowlett.parsers.brisnet.files.RaceFile.AgeSexRestriction;
import com.robinhowlett.parsers.brisnet.files.RaceFile.SurfaceNew;
import com.robinhowlett.parsers.brisnet.files.StartFile;
import com.robinhowlett.parsers.brisnet.parsers.RaceFileParser.MinMaxAgeAndSexes;
import com.robinhowlett.parsers.tracks.TrackService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.robinhowlett.parsers.brisnet.files.RaceFile.SurfaceNew.INNER_TURF;
import static com.robinhowlett.parsers.brisnet.files.RaceFile.SurfaceNew.MAIN_TURF;

public class ComprehensiveChartFileParser {

    private TrackService trackService;
    private List<PointOfCall> pointsOfCall;

    public ComprehensiveChartFileParser(TrackService trackService) {
        this.trackService = trackService;
    }

    public List<RaceResult> parse(ComprehensiveChartFile comprehensiveChartFile) {
        List<RaceResult> raceResults = new ArrayList<>();
        if (comprehensiveChartFile == null) return raceResults;

        List<RaceResult.Builder> raceResultBuilders =
                parseRaces(comprehensiveChartFile.getRaceFiles());


        raceResultBuilders.forEach(builder -> raceResults.add(builder.build()));

        return raceResults;
    }

    List<Starter.Builder> parseStarters(List<StartFile> startFiles) {
        List<Starter.Builder> starterBuilders = new ArrayList<>();
        if (startFiles == null) return starterBuilders;

        for (StartFile startFile : startFiles) {
            Starter.Builder starterBuilder = new Starter.Builder();

            // TODO link to Race via Track, Race Date, Race Number

            // TODO link with breeding? Equibase ID?
            starterBuilder.horse(new Horse(startFile.getHorseName()));
            // TODO foreignBredCode, stateBredCode

            starterBuilder.postPosition(startFile.getPostPosition());

            StartFile.ProgramOrScratched programOrScratched = startFile.getProgramOrScratched();
            if (programOrScratched.isScratched()) {
                // TODO add scratches to RaceResult.Builder
            } else {
                starterBuilder.program(programOrScratched.getProgram());
            }

            // TODO year of birth vs foaling date?
            // TODO breed

            // coupled is handled by examination of the program numbers

            // TODO forget about middle name?
            starterBuilder.jockey(new Jockey(startFile.getJockeyFirstName(), startFile
                    .getJockeyLastName()));

            // TODO forget about middle name?
            starterBuilder.trainer(new Trainer(startFile.getTrainerFirstName(), startFile
                    .getTrainerLastName()));

            starterBuilder.comments(startFile.getTripComment());
            // TODO handle first, middle, blanks
            starterBuilder.owner(new Owner(startFile.getOwnerNames()));

            ClaimedHorse claimedHorse = null;
            if (startFile.isClaimed()) {
                // TODO standardize names
                claimedHorse = new ClaimedHorse(startFile.getClaimedByAbbrevTrainerName(),
                        startFile.getClaimedByAbbrevOwnerName());
            }
            Integer claimingPrice = startFile.getClaimingPrice();
            if (claimingPrice != null && claimingPrice > 0) {
                starterBuilder.claim(new Starter.Claim(new ClaimingPrice(claimingPrice),
                        claimedHorse));
            }

            // TODO build med equip text
            String text = null;
            starterBuilder.medicationAndEquipment(new MedicationEquipment(text, startFile
                    .getMedicationCodes(), startFile.getEquipmentCodes()));

            // TODO earnings
            // TODO purse-only, non-betting flag or use null
            starterBuilder.odds(new Odds(startFile.getOdds(), startFile.isFavoriteFlag()));

            // FIXME no jockey allowance available
            // TODO overweight
            starterBuilder.weight(new Weight(startFile.getWeight(), 0));

            for (int point = 0; point < pointsOfCall.size(); point++) {
                PointOfCall pointOfCall = pointsOfCall.get(point);
                Integer position = null;
                Double lengthsAhead = null, lengthsBehind = null;
                switch (point) {
                    // start
                    case 1:
                        // TODO how to handle zeros?
                        position = startFile.getStartCallPosition();
                        lengthsAhead = startFile.getStartCallMargin();
                        lengthsBehind = startFile.getStartCallLengthsBehind();
                        break;
                    case 2:
                        position = startFile.getCall1Position();
                        lengthsAhead = startFile.getCall1Margin();
                        lengthsBehind = startFile.getCall1LengthsBehind();
                        break;
                    case 3:
                        position = startFile.getCall2Position();
                        lengthsAhead = startFile.getCall2Margin();
                        lengthsBehind = startFile.getCall2LengthsBehind();
                        break;
                    case 4:
                        position = startFile.getCall3Position();
                        lengthsAhead = startFile.getCall3Margin();
                        lengthsBehind = startFile.getCall3LengthsBehind();
                        break;
                    case 5:
                        position = startFile.getStretchPosition();
                        lengthsAhead = startFile.getStretchMargin();
                        lengthsBehind = startFile.getStretchLengthsBehind();
                        break;
                    case 6:
                        position = startFile.getFinishPosition();
                        lengthsAhead = startFile.getFinishMargin();
                        lengthsBehind = startFile.getFinishLengthsBehind();
                        break;
                }

                RelativePosition relativePosition = new RelativePosition(position,
                        new LengthsAhead(Lengths.lengthsToText(lengthsAhead), lengthsAhead),
                        new TotalLengthsBehind(Lengths.lengthsToText(lengthsBehind),
                                lengthsBehind));
                pointOfCall.setRelativePosition(relativePosition);
            }

            // FIXME build?
            Starter starter = starterBuilder.build();

            if (startFile.isDqFlag()) {
                starter.updateDisqualification(new Disqualification(startFile.getDqPlacing()));
            }

            starter.setWinPlaceShowPayoff(
                    new WinPlaceShowPayoff(
                            startFile.getWinPayoff(),
                            startFile.getPlacePayoff(),
                            startFile.getShowPayoff()));

            if (startFile.getFinishPosition() != null && startFile.getFinishPosition() > 0) {
                starter.setFinishPosition(startFile.getFinishPosition());
            }
            if (startFile.getOfficialPosition() != null && startFile.getOfficialPosition() > 0) {
                starter.setOfficialPosition(startFile.getOfficialPosition());
            }
        }

        return starterBuilders;
    }

    List<RaceResult.Builder> parseRaces(List<RaceFile> raceFiles) {
        List<RaceResult.Builder> raceResultBuilders = new ArrayList<>();
        if (raceFiles == null) return raceResultBuilders;

        for (RaceFile raceFile : raceFiles) {
            BrisnetRaceResult.Builder raceResultBuilder = new BrisnetRaceResult.Builder();

            Optional<Track> track = trackService.getTrack(raceFile.getTrackCode());
            if (!track.isPresent()) {
                // TODO custom exception
                throw new RuntimeException("No track found for " + raceFile.getTrackCode());
            }

            // Track, Race Date, Race Number
            raceResultBuilder.track(track.get())
                    .raceDate(raceFile.getDate())
                    .raceNumber(raceFile.getRaceNumber());

            // Breed
            Breed breed;
            try {
                breed = Breed.forCode(raceFile.getBreed());
            } catch (Breed.NoMatchingBreedException e) {
                // TODO custom exception
                throw new RuntimeException(e);
            }

            // Race Type, Race Name, Black Type, and Breed
            String equibaseCode = raceFile.getEqbRaceType(); // STR
            Map.Entry<String, String> typeAndCode =
                    RaceTypeNameBlackTypeBreed.typeFromCode(equibaseCode, breed);
            String type = null, code = null;
            if (typeAndCode != null) {
                type = typeAndCode.getKey(); // STARTER ALLOWANCE
                code = typeAndCode.getValue(); // STA
            }
            // Check for Cancellation and stop if cancelled
            if (code != null && code.equals(RaceTypeNameBlackTypeBreed.CAN)) {
                RaceResult.Builder raceResult = raceResultBuilder.cancellation(
                        new Cancellation(Cancellation.NO_REASON_AVAILABLE));
                raceResultBuilders.add(raceResult);
                continue;
            }
            String name = raceFile.getRaceName(); // Breeders' Cup Classic
            Integer grade = raceFile.getGrade(); // 1
            String blackType = null; // Listed, Black Type
            RaceTypeNameBlackTypeBreed raceTypeNameBlackTypeBreed =
                    new RaceTypeNameBlackTypeBreed(type, code, name, grade, blackType, breed);
            raceResultBuilder.raceTypeAndRaceNameAndBlackTypeAndBreed(raceTypeNameBlackTypeBreed);

            // Race Restrictions and Conditions
            String text = raceFile.getRaceConditions(); // FOR MAIDENS, FILLIES AND MARES THREE ...
            ClaimingPriceRange claimingPriceRange = null;
            Integer maxClaimingPrice = raceFile.getMaxClaimingPrice(); // 15000
            if (RaceConditions.isClaimingRace(type) &&
                    (maxClaimingPrice != null && maxClaimingPrice > 0)) {
                claimingPriceRange = new ClaimingPriceRange(maxClaimingPrice, maxClaimingPrice);
            }
            RaceRestrictions raceRestrictions = null;
            String restrictionsCode = (raceFile.getRaceRestrictions() != null ?
                    raceFile.getRaceRestrictions().trim() : null);
            AgeSexRestriction ageSexRestriction = raceFile.getAgeSexRestriction();
            Integer minAge = null, maxAge = null;
            int sexes = 0;
            if (ageSexRestriction != null) {
                MinMaxAgeAndSexes minMaxAgeAndSexes =
                        RaceFileParser.parseAgeSexRestriction(ageSexRestriction);
                minAge = minMaxAgeAndSexes.getMinAge();
                maxAge = minMaxAgeAndSexes.getMaxAge();
                sexes = minMaxAgeAndSexes.getSexes();
            }
            raceRestrictions = new RaceRestrictions(restrictionsCode, minAge, maxAge, sexes,
                    raceFile.isStateBred());
            Integer totalValueOfRace = raceFile.getTotalValueOfRace();
            Purse purse = new Purse(raceFile.getPurse(), null, null,
                    (totalValueOfRace != null ? String.valueOf(totalValueOfRace) : null),
                    new ArrayList<>());
            RaceConditions raceConditions = new RaceConditions(text, claimingPriceRange,
                    raceRestrictions, raceTypeNameBlackTypeBreed, purse);
            raceResultBuilder.raceConditionsAndClaimingPricesRange(raceConditions);

            // Distance, Surface and Course
            RaceDistance raceDistance =
                    RaceDistance.fromFeet(raceFile.getDistanceInFeet(), raceFile.isAbout(), breed);
            SurfaceNew courseAndSurface = raceFile.getSurfaceNew();
            // Handle something that looks odd - AQU's new inner turf is 'T', but the outer turf
            // is 't'. That is the reverse of the Brisnet documentation, so flip it
            if (track.get().getCode().toUpperCase().equals("AQU")) {
                if (courseAndSurface.equals(INNER_TURF)) {
                    courseAndSurface = MAIN_TURF;
                } else if (courseAndSurface.equals(MAIN_TURF)) {
                    courseAndSurface = INNER_TURF;
                }
            }
            String course = courseAndSurface.getDescription();
            DistanceSurfaceTrackRecord distanceSurfaceTrackRecord =
                    new DistanceSurfaceTrackRecord(raceDistance, course, raceFile.isOffTurf());
            if (raceFile.getOffTurfDistanceChange() != null) {
                distanceSurfaceTrackRecord.setScheduledDistanceChange(
                        raceFile.getOffTurfDistanceChange());
            }
            raceResultBuilder.distanceAndSurfaceAndTrackRecord(distanceSurfaceTrackRecord);

            // Post Time and Start Comments
            // No Timer information present
            raceResultBuilder.postTimeAndStartCommentsAndTimer(
                    new PostTimeStartCommentsTimer(raceFile.getOffTime().toString(),
                            raceFile.getStartDescription()));

            // Run Up and Temp Rail
            raceResultBuilder.runUpTemporaryRail(
                    new RunUpTemporaryRail(raceFile.getRunUpDistanceFeet(),
                            raceFile.getTempRailDistanceFeet()));

            // Weather and Track Condition
            // Race Temperature appears to mix F and C without differentiating them - ignoring it
            // No wind speed or direction data present
            raceResultBuilder.weatherAndTrackCondition(
                    new WeatherTrackCondition(raceFile.getWeather(), raceFile.getTrackCondition()));

            // Build Race Fractionals
            List<Fractional> fractionals = RaceFileParser.getFractionals(
                    raceFile.isAbout(), breed,
                    raceFile.getFraction1Distance(), raceFile.getFraction1(),
                    raceFile.getFraction2Distance(), raceFile.getFraction2(),
                    raceFile.getFraction3Distance(), raceFile.getFraction3(),
                    raceFile.getFraction4Distance(), raceFile.getFraction4(),
                    raceFile.getFraction5Distance(), raceFile.getFraction5(),
                    raceFile.getDistanceInFeet(), raceFile.getFinalTime());
            raceResultBuilder.fractionals(fractionals);

            pointsOfCall = RaceFileParser.getPointsOfCall(
                    raceFile.isAbout(), breed,
                    raceFile.getStartCallDistanceYards(),
                    raceFile.getPointOfCall1DistanceYards(),
                    raceFile.getPointOfCall2DistanceYards(),
                    raceFile.getPointOfCall3DistanceYards(),
                    raceFile.getDistanceInFeet());

            List<ExoticPayoffPool> exoticPayoffPools = new ArrayList<>();
            List<WinPlaceShowPayoff> winPlaceShowPayoffs = new ArrayList<>();
            WagerPayoffPools wagerPayoffPools = new WagerPayoffPools(
                    new WinPlaceShowPayoffPool(raceFile.getWpsPool().intValue(),
                            winPlaceShowPayoffs),
                    exoticPayoffPools);
            raceResultBuilder.wagerPoolsAndPayoffs(wagerPayoffPools);

            raceResultBuilders.add(raceResultBuilder);
        }

        return raceResultBuilders;
    }
}
