package com.robinhowlett.parsers.brisnet.files;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.robinhowlett.parsers.brisnet.files.RaceFile.DayEvening;

import java.time.LocalDate;

import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
public class ExoticPayoffFile {
    private String trackCode;
    private LocalDate date;
    private Integer raceNumber;
    private DayEvening dayEvening;
    private String wagerType;
    private Double betAmount;
    private Double payoffAmount;
    private Integer numberCorrect;
    // may also contain the word "ALL" and "/" and "-"
    private String winningNumbers;
    private Double wagerPool;
    private Double carryoverAmount;
    @JsonInclude(NON_DEFAULT)
    private String reserved12;
    @JsonInclude(NON_DEFAULT)
    private String reserved13;
    @JsonInclude(NON_DEFAULT)
    private String reserved14;
    @JsonInclude(NON_DEFAULT)
    private String reserved15;
    @JsonInclude(NON_DEFAULT)
    private String reserved16;
    @JsonInclude(NON_DEFAULT)
    private String reserved17;
    @JsonInclude(NON_DEFAULT)
    private String reserved18;
    @JsonInclude(NON_DEFAULT)
    private String reserved19;
    @JsonInclude(NON_DEFAULT)
    private String reserved20;
    @JsonInclude(NON_DEFAULT)
    private String reserved21;
    @JsonInclude(NON_DEFAULT)
    private String reserved22;
    @JsonInclude(NON_DEFAULT)
    private String reserved23;
    @JsonInclude(NON_DEFAULT)
    private String reserved24;
    @JsonInclude(NON_DEFAULT)
    private String reserved25;
}
