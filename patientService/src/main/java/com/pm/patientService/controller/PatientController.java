package com.pm.patientService.controller;


import com.pm.patientService.dto.PatientRequestDTO;
import com.pm.patientService.dto.PatientResponseDTO;
import com.pm.patientService.dto.validators.CreatePatientValidationGroup;
import com.pm.patientService.model.Patient;
import com.pm.patientService.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name="Patient",description = "API for managing Patients")
public class PatientController {
    private final PatientService patientService;


    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }



    @GetMapping
    @Operation(summary = "Get Patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients(){
        List<PatientResponseDTO> patients = patientService.getPatietns();
        return ResponseEntity.ok().body(patients);
    }


    @PostMapping
    @Operation(summary = "Create A Patient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class , CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update A Patient")
    public ResponseEntity<PatientResponseDTO> updatePatient( @PathVariable UUID id,@Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id,patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete A Patient")
    public ResponseEntity<Void> deletePatient( @PathVariable UUID id){
        patientService.deletePatient(id);
        // no content with status code of 204 will be returned to the user !!
        return ResponseEntity.noContent().build();
    }


}
