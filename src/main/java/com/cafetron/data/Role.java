package com.cafetron.data;

public enum Role {
    EMPLOYEE("/menu"),
    VENDOR("/vendor/orders"),
    ADMIN("/admin");

    private final String landingPath;

    Role(String landingPath) {
        this.landingPath = landingPath;
    }

    public String landingPath() {
        return landingPath;
    }
}
