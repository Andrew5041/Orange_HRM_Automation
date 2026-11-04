package org.orange_hrm.models;

public record EmployeePIM(
        String employeeID,
        String firstName,
        String middleName,
        String lastName,
        String jobTitle,
        String employmentStatus
) {
}
