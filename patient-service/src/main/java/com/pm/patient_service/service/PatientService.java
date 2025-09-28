package com.pm.patient_service.service;

import com.pm.patient_service.dto.PatientRequestDTO;
import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.exception.EMailAlreadyExistsException;
import com.pm.patient_service.exception.PatientNotFoundException;
import com.pm.patient_service.mapper.PatientMapper;
import com.pm.patient_service.model.Patient;
import com.pm.patient_service.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    //dependency injection Constructor Injection
    private PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    //For using the DTO we need convert patient object to DTO
    //This process in handled In the mapper package
    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();

        //stream() is used for functions like map,filter,forEach etc.
        //loop over the "patients" list
        //List<PatientResponseDTO> patientResponseDTOs = patients.stream()
                //.map(PatientMapper::toDTO).toList();

        return patients.stream()
                .map(PatientMapper::toDTO).toList();
    }


    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){

        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EMailAlreadyExistsException("A patient with this email" +
                    "already exists. Please choose another email addres");
        }
        Patient newPatient = patientRepository.save(
                PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO){

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Couldn't find patient in the system with id: " + id));

        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)){
            throw new EMailAlreadyExistsException("A patient with this email" +
                    patientRequestDTO.getEmail() +
                    "already exists. Please choose another email address");
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);

        return PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatient(UUID id){
        patientRepository.deleteById(id);
    }

}
