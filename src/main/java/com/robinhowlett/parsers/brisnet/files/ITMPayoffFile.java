package com.robinhowlett.parsers.brisnet.files;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
public class ITMPayoffFile {
    private String trackCode;
    private LocalDate date;
    private Integer raceNumber;
    private RaceFile.DayEvening dayEvening;
    private String horseName;
    // if not US, CAN, or PR
    private String foreignBredCode;
    private String stateBredCode;
    private String programNumber;
    private Double winPayoff;
    private Double placePayoff;
    private Double showPayoff;
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
