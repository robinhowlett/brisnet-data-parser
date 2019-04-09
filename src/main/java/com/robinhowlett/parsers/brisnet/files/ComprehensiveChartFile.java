package com.robinhowlett.parsers.brisnet.files;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class ComprehensiveChartFile {
    @Getter
    private final List<RaceFile> raceFiles;
    @Getter
    private final List<StartFile> startFiles;
    @Getter
    private final List<ITMPayoffFile> itmPayoffFiles;
    @Getter
    private final List<ExoticPayoffFile> exoticPayoffFiles;
    @Getter
    private final List<BreedingFile> breedingFiles;
    @Getter
    private final List<FootnotesFile> footnotesFiles;

    protected ComprehensiveChartFile(List<RaceFile> raceFiles, List<StartFile> startFiles,
            List<ITMPayoffFile> itmPayoffFiles, List<ExoticPayoffFile> exoticPayoffFiles,
            List<BreedingFile> breedingFiles, List<FootnotesFile> footnotesFiles) {
        this.raceFiles = raceFiles;
        this.startFiles = startFiles;
        this.itmPayoffFiles = itmPayoffFiles;
        this.exoticPayoffFiles = exoticPayoffFiles;
        this.breedingFiles = breedingFiles;
        this.footnotesFiles = footnotesFiles;
    }

    protected ComprehensiveChartFile(Builder builder) {
        this(builder.raceFiles, builder.startFiles, builder.itmPayoffFiles,
                builder.exoticPayoffFiles, builder.breedingFiles, builder.footnotesFiles);
    }

    public static class Builder {
        private List<RaceFile> raceFiles;
        private List<StartFile> startFiles;
        private List<ITMPayoffFile> itmPayoffFiles;
        private List<ExoticPayoffFile> exoticPayoffFiles;
        private List<BreedingFile> breedingFiles;
        private List<FootnotesFile> footnotesFiles;

        public Builder raceFiles(List<RaceFile> raceFiles) {
            this.raceFiles = raceFiles;
            return this;
        }

        public Builder startFiles(List<StartFile> startFiles) {
            this.startFiles = startFiles;
            return this;
        }

        public Builder itmPayoffFiles(List<ITMPayoffFile> itmPayoffFiles) {
            this.itmPayoffFiles = itmPayoffFiles;
            return this;
        }

        public Builder exoticPayoffFiles(List<ExoticPayoffFile> exoticPayoffFiles) {
            this.exoticPayoffFiles = exoticPayoffFiles;
            return this;
        }

        public Builder breedingFiles(List<BreedingFile> breedingFiles) {
            this.breedingFiles = breedingFiles;
            return this;
        }

        public Builder footnotesFiles(List<FootnotesFile> footnotesFiles) {
            this.footnotesFiles = footnotesFiles;
            return this;
        }

        public ComprehensiveChartFile build() {
            return new ComprehensiveChartFile(this);
        }

    }
}
