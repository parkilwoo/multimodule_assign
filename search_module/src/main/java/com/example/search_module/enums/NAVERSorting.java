package com.example.search_module.enums;

/**
 * Sorting Enum Class
 */
public enum NAVERSorting {

    SIM("sim"),
    DATE("date");

    private final String sortingValue;

    NAVERSorting(String sortingValue) {
        this.sortingValue = sortingValue;
    }

    public String getValue() {return sortingValue;}

}
