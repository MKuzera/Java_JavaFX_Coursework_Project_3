package com.example.paoimlab3.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum TeacherCondition {
    OBECNY,
    DELEGACJA,
    CHORY,
    NIEOBECNY;
    public static ObservableList<String> convertEnumToObservableList() {
        TeacherCondition[] conditions = TeacherCondition.values();
        return FXCollections.observableArrayList(
                java.util.Arrays.stream(conditions)
                        .map(TeacherCondition::toString)
                        .toArray(String[]::new)
        );
    }

    public static TeacherCondition fromString(String condition) {
        return TeacherCondition.valueOf(condition.toUpperCase());
    }
}
