package com.robinhowlett.parsers.brisnet.files;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.robinhowlett.parsers.brisnet.files.RaceFile.DayEvening;

import java.time.LocalDate;

import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
public class FootnotesFile {
    private String trackCode;
    private LocalDate date;
    private Integer raceNumber;
    private DayEvening dayEvening;
    private Integer footnoteSequence;
    private String footnoteText;
    @JsonInclude(NON_DEFAULT)
    private String reserved7;
    @JsonInclude(NON_DEFAULT)
    private String reserved8;
    @JsonInclude(NON_DEFAULT)
    private String reserved9;
    @JsonInclude(NON_DEFAULT)
    private String reserved10;
}
