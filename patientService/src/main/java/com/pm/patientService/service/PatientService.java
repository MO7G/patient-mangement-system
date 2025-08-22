package com.pm.patientService.service;


import com.pm.patientService.dto.PatientRequestDTO;
import com.pm.patientService.dto.PatientResponseDTO;
import com.pm.patientService.exceptions.EmailAlreadyExistException;
import com.pm.patientService.mapper.PatientMapper;
import com.pm.patientService.model.Patient;
import com.pm.patientService.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
