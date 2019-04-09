package com.robinhowlett.parsers.brisnet.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.robinhowlett.data.running_line.MedicationEquipment.Medication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MedicationCodeConverter {

    public static class MedicationToStringConverter extends StdConverter<List<Medication>, String> {
        @Override
        public String convert(List<Medication> medicationCodes) {
            if (medicationCodes != null && !medicationCodes.isEmpty()) {
                return (medicationCodes.stream()
                        .map(Medication::getCodeAsString)
                        .collect(Collectors.joining("")));
            }
            return null;
        }
    }

    public static class StringToMedicationConverter extends StdConverter<String, List<Medication>> {
        @Override
        public List<Medication> convert(String text) {
            if (text != null && !text.isEmpty()) {
                return parse(text);
            }
            return Collections.emptyList();
        }

        List<Medication> parse(String medicationCodes) {
            if (medicationCodes != null && !medicationCodes.isEmpty()) {
                if (medicationCodes.length() == 1) {
                    return Arrays.asList(Medication.lookup(medicationCodes.charAt(0)));
                } else {
                    List<Medication> codes = new ArrayList<>();
                    Arrays.asList(medicationCodes.split("")).forEach(code -> {
                        codes.add(Medication.lookup(code.charAt(0)));
                    });
                    return codes;
                }
            }
            return Collections.emptyList();
        }
    }
}
