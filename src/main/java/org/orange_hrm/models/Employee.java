package org.orange_hrm.models;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class Employee {

    private String firstName;
    private String middleName;
    private String lastName;
    private String employeeId;
    private String otherId;
    private String driverLicenseNumber;
    private LocalDate licenseExpiryDate;
    private String nationality;
    private String maritalStatus;
    private LocalDate dateOfBirth;
    private String gender;
    private String bloodType;
    private String testField;
}
