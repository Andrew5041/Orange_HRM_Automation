package org.orange_hrm.models;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;

@Data
@Value
@Builder
public class Employee {
    
    String firstName;
    String middleName;
    String lastName;
    String employeeId;
    String otherId;
    String driverLicenseNumber;
    LocalDate licenseExpiryDate;
    String nationality;
    String maritalStatus;
    LocalDate dateOfBirth;
    String gender;
    String bloodType;
    String testField;
}