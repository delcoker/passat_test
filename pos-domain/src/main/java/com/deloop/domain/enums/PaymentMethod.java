package com.deloop.domain.enums;

//import io.ebean.annotation.DbEnumValue; //

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This enum represents genders with that option if it's not known of if someone would rather not
 * state it.
 */
@RequiredArgsConstructor
public enum PaymentMethod {

    CASH("cash"),

    MEAL_PLAN("meal plan"),

    VISA("visa"),

    MOMO("momo");


    private static final Map<PaymentMethod, String> PAYMENT_METHODS = new HashMap<>();

    static {
        for (PaymentMethod paymentMethod : values()) {
            PAYMENT_METHODS.put(paymentMethod, paymentMethod.label);
        }
    }


    private final String label;

    public static PaymentMethod getStatus(String statusString) {
        return Arrays.stream(PaymentMethod.values())
                .filter(status -> compareStatus(status, statusString))
                .findFirst()
                .orElse(PaymentMethod.CASH);
    }

    private static boolean compareStatus(PaymentMethod status, String statusString) {
        return status.getLabel().equalsIgnoreCase(statusString);
    }

    public static Map<PaymentMethod, String> getPaymentMethods() {
        return PAYMENT_METHODS;
    }

    //   @DbEnumValue
    public String getLabel() {
        return this.label;
    }
}
