package org.orange_hrm.models;

public record TestEmployee(
        String name,
        int age,
        String jobTitle,
        boolean activity,
        Address address
) {
}
