package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.datatransferobject.PatientDTO;
import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.Exercise;
import edu.ib.telerehabilitation.model.Frequency;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.model.Specialist;
import edu.ib.telerehabilitation.persistance.PatientRepo;
import edu.ib.telerehabilitation.persistance.SpecialistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService implements UserService {

    private PatientRepo patientRepo;
    private SpecialistRepo specialistRepo;
    private SpecialistService specialistService;

    @Autowired
    public PatientService(PatientRepo patientRepo, SpecialistRepo specialistRepo, SpecialistService specialistService) {
        this.patientRepo = patientRepo;
        this.specialistRepo = specialistRepo;
        this.specialistService = specialistService;
    }

    public Patient findByEmail(String email) {
        return patientRepo.findByEmail(email);
    }

    public Patient findByUsername(String username) {
        return patientRepo.findByUserName(username);
    }

    @Override
    public Boolean addUser(UserDTO userDTO) {
        if (!checkIfUserExists(userDTO)) {
            Patient patient = new Patient();
            patient.setEmail(userDTO.getEmail());
            patient.setUserName(userDTO.getUserName());
            patient.setName(userDTO.getName());
            patient.setSurname(userDTO.getSurname());
            patient.setPhoneNumber(userDTO.getPhoneNumber());
            patient.setPassword(userDTO.getPassword());
            patientRepo.save(patient);
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkIfUserExists(UserDTO userDTO) {
        Patient patient = patientRepo.findByEmail(userDTO.getEmail());
        boolean userExists;
        userExists = (patient != null)
                && userDTO.getPassword().equals(patient.getPassword());
        return userExists;
    }

    public Boolean checkIfUserExistsByEmail(UserDTO userDTO) {
        Patient patient = patientRepo.findByEmail(userDTO.getEmail());
        boolean userExists;
        userExists = (patient != null);
        return userExists;
    }

    public Boolean updateSpecialist(UserDTO userDTOPatient, UserDTO userDTOSpecialist) {

        if (specialistService.checkIfUserExists(userDTOSpecialist) && checkIfUserExistsByEmail(userDTOPatient)) {
            Specialist specialist = specialistRepo.findByEmail(userDTOSpecialist.getEmail());
            Long id = patientRepo.findByEmail(userDTOPatient.getEmail()).getId();
            patientRepo.findById(id)
                    .map(patient -> {
                        patient.setSpecialist(specialist);
                        patientRepo.save(patient);
                        return patient;
                    });
            return true;
        }
        return false;
    }

    public Boolean updateFrequency(UserDTO userDTOPatient, Frequency frequency) {
        if (checkIfUserExistsByEmail(userDTOPatient)) {
            Long id = patientRepo.findByEmail(userDTOPatient.getEmail()).getId();
            patientRepo.findById(id)
                    .map(patient -> {
                        patient.setFrequency(frequency);
                        patientRepo.save(patient);
                        return patient;
                    });
            return true;
        }
        return false;
    }

    public Boolean updateResultsDescription(UserDTO userDTOPatient, String opinion) {
        if (checkIfUserExistsByEmail(userDTOPatient)) {
            Long id = patientRepo.findByEmail(userDTOPatient.getEmail()).getId();
            patientRepo.findById(id)
                    .map(patient -> {
                        patient.setResultsDescription(opinion);
                        patientRepo.save(patient);
                        return patient;
                    });
            return true;
        }
        return false;
    }

    public Boolean updateTrainingDates(UserDTO userDTO, LocalDate date) {
        if (checkIfUserExistsByEmail(userDTO)) {
            Long id = patientRepo.findByEmail(userDTO.getEmail()).getId();
            patientRepo.findById(id)
                    .map(patient -> {
                        patient.getTrainingDates().add(date);
                        patientRepo.save(patient);
                        return patient;
                    });
            return true;
        }
        return false;
    }


    public Iterable<Patient> findAll() {
        return patientRepo.findAll();
    }

    public Optional<Patient> findById(Long id) {
        return patientRepo.findById(id);
    }

    public PatientDTO getPatientIfExists(UserDTO userDTO) {
        Patient patientOptional = patientRepo.findByEmail(userDTO.getEmail());
        PatientDTO patient = new PatientDTO();
        patient.setEmail(patientOptional.getEmail());
        patient.setName(patientOptional.getName());
        patient.setSurname(patientOptional.getSurname());
        patient.setUserName(patientOptional.getUserName());
        patient.setName(patientOptional.getUserName());
        patient.setPhoneNumber(patientOptional.getPhoneNumber());
        patient.setFrequency(patientOptional.getFrequency());
        patient.setTrainingDates(patientOptional.getTrainingDates());
        patient.setResultsDescription(patientOptional.getResultsDescription());
        patient.setExercises(patientOptional.getExercises());
        patient.setSpecialist(patientOptional.getSpecialist());
        return patient;
    }

}
