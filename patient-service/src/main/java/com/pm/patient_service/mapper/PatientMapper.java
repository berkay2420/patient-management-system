package com.pm.patient_service.mapper;

import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.dto.PatientRequestDTO;
import com.pm.patient_service.model.Patient;

import java.time.LocalDate;

public class PatientMapper {

    //Converting Patient object to DTO
    //In the mapper we can decide what we want  show to the client
    public  static PatientResponseDTO toDTO(Patient patient){
        PatientResponseDTO patientDTO = new PatientResponseDTO();

        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        patientDTO.setAddress(patient.getAddress());

        return patientDTO;
    }

    //request to patient object
    public static Patient toModel(PatientRequestDTO patientRequestDTO){
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));

        return patient;
    }
}
