package com.deloop.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Status {

    /**
     * enabled
     */
    ENABLED("enabled"),

    /**
     * disabled.
     */
    DISABLED("disabled"),

    /**
     * deleted.
     */
    DELETED("deleted");

    @Getter
    private final String label;

//    public String getLabel() {
//        return this.label;
//    }

    public static Status getStatus(String statusString) {
        return Arrays.stream(Status.values())
                .filter(gender -> compareStatus(gender, statusString))
                .findFirst()
                .orElse(Status.DELETED);
    }

    private static boolean compareStatus(Status status, String statusString) {
        return status.getLabel().equalsIgnoreCase(statusString);
    }
}
