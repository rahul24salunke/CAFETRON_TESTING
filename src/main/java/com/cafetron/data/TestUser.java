package com.cafetron.data;

public class TestUser {
    private final String name;
    private final String email;
    private final String password;
    private final String employeeId;
    private final String department;
    private final Role role;

    public TestUser(String name, String email, String password, String employeeId, String department, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.employeeId = employeeId;
        this.department = department;
        this.role = role;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

    public String employeeId() {
        return employeeId;
    }

    public String department() {
        return department;
    }

    public Role role() {
        return role;
    }
}
