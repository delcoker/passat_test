package com.deloop.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Getter
@RequiredArgsConstructor
public enum Tax {

    SERVICE_CHARGE("Service Charge", 0, Status.ENABLED),

//    DISCOUNT("Meal Plan", 0),

    VAT("VAT", 12.5, Status.ENABLED),

//    GET_FUND("Get Fund", 2.5, Status.ENABLED),

    NHIL("NHIL", 0, Status.ENABLED),

    COVID_19("Covid-19", 0, Status.ENABLED),

    TOURISM_LEVY("Tourism Levy", 0, Status.ENABLED);

    private static final Map<String, Double> TAXES = new HashMap<>();

    static {
        for (Tax tax : values()) {
            TAXES.put(tax.label, tax.percent);
        }
    }

    private final String label;
    private final double percent;
    private final Status status;


    public static Tax getTax(String statusString) {
        return Arrays.stream(Tax.values())
                .filter(status -> compareStatus(status, statusString))
                .findFirst()
                .orElse(Tax.VAT);
    }

    private static boolean compareStatus(Tax status, String statusString) {
        return status.getLabel().equalsIgnoreCase(statusString);
    }

    public static Map<String, Double> getTaxes() {
        return TAXES;
    }
}
