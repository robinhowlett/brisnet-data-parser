package com.robinhowlett.parsers.brisnet.files;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.robinhowlett.parsers.brisnet.files.RaceFile.DayEvening;

import java.time.LocalDate;

import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
public class BreedingFile {
    private String trackCode;
    private LocalDate date;
    private Integer raceNumber;
    private DayEvening dayEvening;
    private String horseName;
    // if not US, CAN, or PR
    private String foreignBredCode;
    private String stateBredCode;
    private String programNumber;
    private String breeder;
    private String color;
    private LocalDate foalDate;
    private Integer age;
    private String sex;
    private String sire;
    private String dam;
    private String broodmareSire;
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
