package org.orange_hrm.models;

public record UserData(
        String username,
        String userRole,
        String employeeName,
        String status
) {
}
