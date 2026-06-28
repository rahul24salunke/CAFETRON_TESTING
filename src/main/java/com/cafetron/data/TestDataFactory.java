package com.cafetron.data;

import com.cafetron.config.ConfigReader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.Map;

public final class TestDataFactory {
    private static final DateTimeFormatter ID_FORMAT = DateTimeFormatter.ofPattern("MMddHHmmss");
    private static final Map<Role, TestUser> CREATED_USERS = new EnumMap<>(Role.class);

    private TestDataFactory() {
    }

    public static TestUser configuredOrGeneratedUser(Role role) {
        if (role == Role.EMPLOYEE) {
            return new TestUser("Kiran QA", "kiran01@cafetron.test", ConfigReader.get("validPassword"),
                    ConfigReader.get("validEmployeeId"), "QA", Role.EMPLOYEE);
        }

        String configuredId = ConfigReader.getOptional(role.name().toLowerCase() + "EmployeeId");
        String configuredPassword = ConfigReader.getOptional(role.name().toLowerCase() + "Password");
        if (!configuredId.isBlank() && !configuredPassword.isBlank()) {
            return new TestUser(role.name() + " QA", configuredId.toLowerCase() + "@cafetron.test",
                    configuredPassword, configuredId, "QA", role);
        }

        return CREATED_USERS.computeIfAbsent(role, TestDataFactory::uniqueUser);
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
}
