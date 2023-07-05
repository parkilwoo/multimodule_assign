package com.example.search_module.enums;

/**
 * Sorting Enum Class
 */
public enum KAKAOSorting {

    ACCURACY("accuracy"),
    RECENCY("recency");

    private final String sortingValue;

    KAKAOSorting(String sortingValue) {
        this.sortingValue = sortingValue;
    }

    public String getValue() {return sortingValue;}

}
