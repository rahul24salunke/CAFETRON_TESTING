package com.cafetron.data;

import com.cafetron.config.ConfigReader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class TestDataFactory {
    private static final DateTimeFormatter ID_FORMAT = DateTimeFormatter.ofPattern("MMddHHmmss");

    private TestDataFactory() {
    }

    public static TestUser configuredUser(Role role) {
        if (role == Role.EMPLOYEE) {
            String employeeId = requiredCredential("validEmployeeId", "validPassword", role);
            String password = requiredCredential("validPassword", "validEmployeeId", role);
            return new TestUser("Employee QA", employeeId.toLowerCase() + "@cafetron.test", password,
                    employeeId, "QA", Role.EMPLOYEE);
        }

        String configuredId = ConfigReader.getOptional(role.name().toLowerCase() + "EmployeeId");
        String configuredPassword = ConfigReader.getOptional(role.name().toLowerCase() + "Password");
        if (configuredId.isBlank() || configuredPassword.isBlank()) {
            throw new IllegalArgumentException("Missing configured " + role.name().toLowerCase()
                    + " login credentials. Provide " + role.name().toLowerCase() + "EmployeeId and "
                    + role.name().toLowerCase() + "Password in config.properties or as system properties.");
        }

        return new TestUser(role.name() + " QA", configuredId.toLowerCase() + "@cafetron.test",
                configuredPassword, configuredId, "QA", role);
    }

    public static TestUser uniqueUser(Role role) {
        String suffix = LocalDateTime.now().format(ID_FORMAT);
        String prefix = "QA_" + role.name() + "_" + suffix;
        return new TestUser("QA " + role.name() + " " + suffix,
                prefix.toLowerCase() + "@cafetron.test",
                ConfigReader.get("qaUserPassword"),
                prefix,
                "QA Automation",
                role);
    }

    public static String uniqueName(String prefix) {
        return prefix + " " + LocalDateTime.now().format(ID_FORMAT);
    }

    private static String requiredCredential(String key, String pairedKey, Role role) {
        String value = ConfigReader.getOptional(key);
        if (value.isBlank()) {
            throw new IllegalArgumentException("Missing configured " + role.name().toLowerCase()
                    + " login credentials. Provide " + key + " and " + pairedKey
                    + " in config.properties or as system properties.");
        }
        return value;
    }
}
