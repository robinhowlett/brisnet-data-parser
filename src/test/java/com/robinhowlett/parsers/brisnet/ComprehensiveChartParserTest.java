package com.robinhowlett.parsers.brisnet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.robinhowlett.data.RaceResult;
import com.robinhowlett.parsers.brisnet.files.ComprehensiveChartFile;
import com.robinhowlett.parsers.brisnet.files.RaceFile;
import com.robinhowlett.parsers.brisnet.files.StartFile;
import com.robinhowlett.parsers.brisnet.formats.BrisnetCsvMapper;
import com.robinhowlett.parsers.tracks.TrackCsvRepository;
import com.robinhowlett.parsers.tracks.TrackService;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

public class ComprehensiveChartParserTest {

    private ComprehensiveChartParser parser;
    private BrisnetCsvMapper csvMapper;

    @Before
    public void setUp() throws Exception {
        TrackCsvRepository trackCsvRepository = new TrackCsvRepository();
        TrackService trackService = new TrackService(trackCsvRepository);
        parser = new ComprehensiveChartParser(trackService);

        csvMapper = new BrisnetCsvMapper();
    }

    @Test
    public void unzip() throws URISyntaxException, IOException {
        URI uri = getClass().getClassLoader().getResource("bel09292018c.zip").toURI();
        File zipFile = Paths.get(uri).toFile();
        ComprehensiveChartFile ccf = parser.unzip(zipFile);
        System.out.println(ccf);
    }

    @Test
    public void raceFile() {
        CsvSchema schema = csvMapper.schemaFor(RaceFile.class);

        try (InputStream tracks = getClass().getClassLoader()
                .getResourceAsStream("kdx09132018c/KDX09132018.1")) {
            MappingIterator<RaceFile> mappingIterator =
                    csvMapper.reader(schema)
                            .forType(RaceFile.class)
                            .readValues(tracks);
            List<RaceFile> raceFiles = mappingIterator.readAll();
            raceFiles.forEach(System.out::println);

            System.out.println("-----");
            ObjectWriter writer = csvMapper.writerFor(RaceFile.class).with(schema);

            raceFiles.forEach(raceFile -> {
                try {
                    System.out.println(writer.writeValueAsString(raceFile));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void startFile() {
        // temp hack for testing
        // https://stackoverflow.com/a/43763281
//        csvMapper.enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE);

        CsvSchema schema = csvMapper.schemaFor(StartFile.class);

        try (InputStream tracks = getClass().getClassLoader()
                .getResourceAsStream("kdx09132018c/KDX09132018.2")) {
            MappingIterator<StartFile> mappingIterator =
                    csvMapper.reader(schema)
                            .forType(StartFile.class)
                            .readValues(tracks);
            List<StartFile> startFiles = mappingIterator.readAll();
            startFiles.forEach(System.out::println);

            System.out.println("-----");
            ObjectWriter writer =
                    csvMapper.writerFor(StartFile.class).with(schema);

            startFiles.forEach(startFile -> {
                try {
                    System.out.println(writer.writeValueAsString(startFile));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void name() throws IOException, URISyntaxException {
        URI uri = getClass().getClassLoader().getResource("bel09292018c.zip").toURI();
        File zipFile = Paths.get(uri).toFile();

        ComprehensiveChartParser comprehensiveChartParser = ComprehensiveChartParser.create();
        List<RaceResult> raceResults = comprehensiveChartParser.parse(zipFile);

        System.out.println(
//                raceResults
        comprehensiveChartParser.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(raceResults)
        );
    }
}
