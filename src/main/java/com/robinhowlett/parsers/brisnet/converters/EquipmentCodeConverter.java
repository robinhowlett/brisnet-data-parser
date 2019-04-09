package com.robinhowlett.parsers.brisnet.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.robinhowlett.data.running_line.MedicationEquipment.Equipment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EquipmentCodeConverter {

    public static class EquipmentToStringConverter extends StdConverter<List<Equipment>, String> {
        @Override
        public String convert(List<Equipment> medicationCodes) {
            if (medicationCodes != null && !medicationCodes.isEmpty()) {
                return (medicationCodes.stream()
                        .map(Equipment::getCodeAsString)
                        .map(String::toUpperCase)
                        .collect(Collectors.joining("")));
            }
            return null;
        }
    }

    public static class StringToEquipmentConverter extends StdConverter<String, List<Equipment>> {
        @Override
        public List<Equipment> convert(String text) {
            if (text != null && !text.isEmpty()) {
                return parse(text);
            }
            return Collections.emptyList();
        }

        List<Equipment> parse(String equipmentCodes) {
            if (equipmentCodes != null && !equipmentCodes.isEmpty()) {
                if (equipmentCodes.length() == 1) {
                    return Arrays.asList(Equipment.lookup(equipmentCodes.toLowerCase().charAt(0)));
                } else {
                    List<Equipment> codes = new ArrayList<>();
                    Arrays.asList(equipmentCodes.toLowerCase().split("")).forEach(code -> {
                        codes.add(Equipment.lookup(code.charAt(0)));
                    });
                    return codes;
                }
            }
            return Collections.emptyList();
        }
    }


}
