package com.pm.patientService.service;


import com.pm.patientService.dto.PatientRequestDTO;
import com.pm.patientService.dto.PatientResponseDTO;
import com.pm.patientService.exceptions.EmailAlreadyExistException;
import com.pm.patientService.exceptions.PatientNotFoundException;
import com.pm.patientService.grpc.BillingServiceGrpcClient;
import com.pm.patientService.kafka.producer.KafkaProducer;
import com.pm.patientService.kafka.producer.publisher.PatientEventPublisher;
import com.pm.patientService.mapper.PatientMapper;
import com.pm.patientService.model.Patient;
import com.pm.patientService.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final PatientEventPublisher patientEventPublisher;
    private final KafkaProducer kafkaProducer;
    
    public PatientService(PatientRepository patientRepository ,
                          BillingServiceGrpcClient billingServiceGrpcClient,
                          PatientEventPublisher patientEventPublisher,
                          KafkaProducer kafkaProducer
                              ){
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.patientEventPublisher = patientEventPublisher;
        this.kafkaProducer = kafkaProducer;
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

        // creating a billing account in the billing service after creating a new patient.
        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(),newPatient.getName(),newPatient.getEmail());



        // sending an event of patient created !!
        //patientEventPublisher.publishPatientCreated(newPatient);
        kafkaProducer.sendEvent(newPatient);

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
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));


        Patient udpatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(udpatedPatient);

    }

    public void deletePatient(UUID id){
        patientRepository.deleteById(id);
    }
}
