package com.example.testtaskping.model;

public enum TestStatus {
    SUCCESS("Success"),
    FAILURE("Failure");
    private final String name;

    TestStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TestStatus fromString(String name) {
        for (TestStatus status : TestStatus.values()) {
            if (status.name.equalsIgnoreCase(name)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No TestStatus with name " + name + " found");
    }
}
