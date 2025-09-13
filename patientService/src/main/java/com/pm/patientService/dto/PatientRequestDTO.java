package com.pm.patientService.dto;

import com.pm.patientService.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;


@Setter
@Getter
public class PatientRequestDTO {

    @NotBlank
    @Size(max=100 , message= "Name can't exceed 100 characters")
    private String name;


    @NotBlank
    @Email(message =  "Email must be Valid")
    private String email;


    @NotBlank(message = "Address is required")
    private String address;


    @NotNull(message = "Date of birth is required")
    private String dateOfBirth;


    @NotBlank(groups = CreatePatientValidationGroup.class  , message = "Registered Date is required")
    private String registeredDate;

}
