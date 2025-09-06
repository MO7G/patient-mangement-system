package com.pm.patientService.service;


import com.pm.patientService.dto.PatientRequestDTO;
import com.pm.patientService.dto.PatientResponseDTO;
import com.pm.patientService.exceptions.EmailAlreadyExistException;
import com.pm.patientService.exceptions.PatientNotFoundException;
import com.pm.patientService.mapper.PatientMapper;
import com.pm.patientService.model.Patient;
import com.pm.patientService.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }



    public List<PatientResponseDTO> getPatietns(){
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTOs =
                patients.stream().map(patient -> PatientMapper.toDTO(patient)).toList();

        return patientResponseDTOs;
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        // checking if the email address is unique
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistException("A patient with this email exist " + patientRequestDTO.getEmail());
        }




        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));




        return PatientMapper.toDTO(newPatient);
    }




    public PatientResponseDTO updatePatient(UUID id , PatientRequestDTO patientRequestDTO){
        Patient patient = patientRepository.findById(id).orElseThrow(()-> new PatientNotFoundException("Patient not found with ID: " + id));

        System.out.println("this is him " + patient);

        // checking if the email address exist for users other than the id i passed !!
        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail() , id)){
            throw new EmailAlreadyExistException("A patient with this email exist " + patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(patientRequestDTO.getDateOfBirth());


        Patient udpatedPatient = patientRepository.save(patient);


        return PatientMapper.toDTO(udpatedPatient);

    }



    public void deletePatient(UUID id){
        patientRepository.deleteById(id);
    }
}
