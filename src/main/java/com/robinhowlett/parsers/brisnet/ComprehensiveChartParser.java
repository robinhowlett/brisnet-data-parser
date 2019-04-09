package com.robinhowlett.parsers.brisnet;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.robinhowlett.data.RaceResult;
import com.robinhowlett.parsers.brisnet.files.BreedingFile;
import com.robinhowlett.parsers.brisnet.files.ComprehensiveChartFile;
import com.robinhowlett.parsers.brisnet.files.ExoticPayoffFile;
import com.robinhowlett.parsers.brisnet.files.FootnotesFile;
import com.robinhowlett.parsers.brisnet.files.ITMPayoffFile;
import com.robinhowlett.parsers.brisnet.files.RaceFile;
import com.robinhowlett.parsers.brisnet.files.StartFile;
import com.robinhowlett.parsers.brisnet.formats.BrisnetCsvMapper;
import com.robinhowlett.parsers.brisnet.formats.BrisnetJsonMapper;
import com.robinhowlett.parsers.brisnet.parsers.ComprehensiveChartFileParser;
import com.robinhowlett.parsers.tracks.TrackCsvRepository;
import com.robinhowlett.parsers.tracks.TrackService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ComprehensiveChartParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComprehensiveChartParser.class);

    private final BrisnetCsvMapper csvMapper;
    private final TrackService trackService;
    public ObjectMapper mapper = new BrisnetJsonMapper();
//    protected final FractionalService fractionalService;
//    protected final PointsOfCallService pointsOfCallService;

    public ComprehensiveChartParser(
            TrackService trackService
// , FractionalService fractionalService,
//            PointsOfCallService pointsOfCallService
    ) {
        this.trackService = trackService;
        this.csvMapper = new BrisnetCsvMapper();
//        this.fractionalService = fractionalService;
//        this.pointsOfCallService = pointsOfCallService;
    }

    public static ComprehensiveChartParser create() {
        TrackService trackService = new TrackService(new TrackCsvRepository());
//        FractionalService fractionalService = new FractionalService(
//                new FractionalPointRepository(jsonMapper));
//        PointsOfCallService pointsOfCallService = new PointsOfCallService(
//                new PointsOfCallRepository(jsonMapper));

        return new ComprehensiveChartParser(
                trackService
// , fractionalService, pointsOfCallService
        );
    }

    public BrisnetCsvMapper getCsvMapper() {
        return csvMapper;
    }

    public List<RaceResult> parse(File comprehensiveChartFileZip) throws IOException {
        // unzip and store the files contained within
        ComprehensiveChartFile ccf = unzip(comprehensiveChartFileZip);

        ComprehensiveChartFileParser parser = new ComprehensiveChartFileParser(trackService);
        List<RaceResult> raceResults = parser.parse(ccf);

//        List<RaceFile> raceFiles = parseRaceFile(comprehensiveChartFileZip);
//        raceFiles.forEach(raceFile -> {
//            RaceResult.Builder raceBuilder = new RaceResult.Builder();
//            Optional<Track> track = trackService.getTrack(raceFile.getTrackCode());
//            if (track.isPresent()) {
//                raceBuilder.track(track.get())
//                        .raceDate(raceFile.getDate())
//                        .raceNumber(raceFile.getRaceNumber());
//
//                String text = raceFile.getRaceConditions();
//                ClaimingPriceRange claimingPriceRange = null;
//                if (raceFile.getMaxClaimingPrice() != null) {
//                    claimingPriceRange = new ClaimingPriceRange(
//                            raceFile.getMaxClaimingPrice(),
//                            raceFile.getMaxClaimingPrice());
//                }
////                RaceRestrictions restrictions = new RaceRestrictions(
////                        raceFile.getAbbrevRaceClass(),
////                        raceFile.getAgeSexRestriction().getAge());
//                RaceTypeNameBlackTypeBreed raceTypeNameBlackTypeBreed =
//                        new RaceTypeNameBlackTypeBreed("", "", raceFile.getRaceName(), raceFile
//                                .getGrade(), "", Breed.THOROUGHBRED);
//                Purse purse = new Purse(raceFile.getPurse(), "", "", "", new ArrayList<>());
//
//                RaceConditions raceConditions = new RaceConditions(text, claimingPriceRange,
//                        null, raceTypeNameBlackTypeBreed, purse);
//
//                raceBuilder.raceConditionsAndClaimingPricesRange(raceConditions);
//
////                raceBuilder.distanceAndSurfaceAndTrackRecord(
////                        new DistanceSurfaceTrackRecord(
////                                new RaceDistance("", )
////                        )
////                )
//
//                RaceResult raceResult = raceBuilder.build();
//                raceResults.add(raceResult);
//            }
//        });

        return raceResults;
    }

    ComprehensiveChartFile unzip(File comprehensiveChartZipFile)
            throws IOException {
        ComprehensiveChartFile.Builder builder = new ComprehensiveChartFile.Builder();

        ZipFile zipFile = new ZipFile(comprehensiveChartZipFile);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();
            System.out.println(zipEntry);
            try (InputStream inputStream = zipFile.getInputStream(zipEntry)) {
                String name = zipEntry.getName();
                if (name != null && name.length() > 2) {
                    CsvSchema schema;
                    switch (name.charAt(name.length() - 1)) {
                        case '1':
//                            String collect = new BufferedReader(new InputStreamReader
// (inputStream))
//                                    .lines().collect(Collectors.joining(System.lineSeparator()));
                            schema = getCsvMapper().schemaFor(RaceFile.class);
                            MappingIterator<RaceFile> raceFileMappingIterator =
                                    getCsvMapper().reader(schema)
                                            .forType(RaceFile.class)
                                            .readValues(inputStream);
                            List<RaceFile> raceFiles = raceFileMappingIterator.readAll();
                            builder.raceFiles(raceFiles);
                            break;
                        case '2':
                            schema = getCsvMapper().schemaFor(StartFile.class);
                            MappingIterator<StartFile> startFileMappingIterator =
                                    getCsvMapper().reader(schema)
                                            .forType(StartFile.class)
                                            .readValues(inputStream);
                            List<StartFile> startFiles = startFileMappingIterator.readAll();
                            builder.startFiles(startFiles);
                            break;
                        case '3':
                            schema = getCsvMapper().schemaFor(ITMPayoffFile.class);
                            MappingIterator<ITMPayoffFile> itmPayoffFileMappingIterator =
                                    getCsvMapper().reader(schema)
                                            .forType(ITMPayoffFile.class)
                                            .readValues(inputStream);
                            List<ITMPayoffFile> itmPayoffFiles =
                                    itmPayoffFileMappingIterator.readAll();
                            builder.itmPayoffFiles(itmPayoffFiles);
                            break;
                        case '4':
                            schema = getCsvMapper().schemaFor(ExoticPayoffFile.class);
                            MappingIterator<ExoticPayoffFile> exoticPayoffFileMappingIterator =
                                    getCsvMapper().reader(schema)
                                            .forType(ExoticPayoffFile.class)
                                            .readValues(inputStream);
                            List<ExoticPayoffFile> exoticPayoffFiles =
                                    exoticPayoffFileMappingIterator.readAll();
                            builder.exoticPayoffFiles(exoticPayoffFiles);
                            break;
                        case '5':
                            schema = getCsvMapper().schemaFor(BreedingFile.class);
                            MappingIterator<BreedingFile> breedingFileMappingIterator =
                                    getCsvMapper().reader(schema)
                                            .forType(BreedingFile.class)
                                            .readValues(inputStream);
                            List<BreedingFile> breedingFiles =
                                    breedingFileMappingIterator.readAll();
                            builder.breedingFiles(breedingFiles);
                            break;
                        case '6':
                            schema = getCsvMapper().schemaFor(FootnotesFile.class);
                            MappingIterator<FootnotesFile> footnotesFileMappingIterator =
                                    getCsvMapper().reader(schema)
                                            .forType(FootnotesFile.class)
                                            .readValues(inputStream);
                            List<FootnotesFile> footnotesFiles = footnotesFileMappingIterator
                                    .readAll();
                            builder.footnotesFiles(footnotesFiles);
                            break;
                    }
                }
            }
        }

        ComprehensiveChartFile ccf = builder.build();

        return ccf;
    }

    List<RaceFile> parseRaceFile(File comprehensiveChartFileZip) {
        List<RaceFile> raceFiles = new ArrayList<>();
        CsvSchema schema = getCsvMapper().schemaFor(RaceFile.class);

        try (InputStream tracks = getClass().getClassLoader()
                .getResourceAsStream("kdx09132018c/KDX09132018.1")) {
            MappingIterator<RaceFile> mappingIterator =
                    getCsvMapper().reader(schema)
                            .forType(RaceFile.class)
                            .readValues(tracks);
            raceFiles = mappingIterator.readAll();
//            raceFiles.forEach(System.out::println);
//
//            System.out.println("-----");
//            ObjectWriter writer =
//                    getCsvMapper().writerFor(RaceFile.class).with(schema);

//            raceFiles.forEach(raceFile -> {
//                try {
//                    System.out.println(writer.writeValueAsString(raceFile));
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                }
//            });

            return raceFiles;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return raceFiles;
    }

}
