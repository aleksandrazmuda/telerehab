package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.Exercise;
import edu.ib.telerehabilitation.model.Frequency;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.model.Specialist;
import edu.ib.telerehabilitation.persistance.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PatientService implements UserService {

    private PatientRepo patientRepo;

    @Autowired
    public PatientService(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }

    public Patient findByEmail(String email) {
        return patientRepo.findByEmail(email);
    }

    public Patient findByUsername(String username) {
        return patientRepo.findByUserName(username);
    }

    @Override
    public void addUser(UserDTO userDTO) {
        Patient patient = new Patient();
        patient.setEmail(userDTO.getEmail());
        patient.setUserName(userDTO.getUserName());
        patient.setName(userDTO.getName());
        patient.setSurname(userDTO.getSurname());
        patient.setPhoneNumber(userDTO.getPhoneNumber());
        patient.setPassword(userDTO.getPassword());
        patientRepo.save(patient);
    }

    @Override
    public Boolean checkIfUserExists(String email, String password, String role) {
        Patient patient = patientRepo.findByEmail(email);
        boolean userExists;
        userExists = (patient != null && password.equals(patient.getPassword()) && role.equals("PATIENT"));
        return userExists;
    }

    public void updateSpecialist(Long id, Specialist specialist) {
        patientRepo.findById(id)
                .map(patient -> {
                    patient.setSpecialist(specialist);
                    patientRepo.save(patient);
                    return patient;
                });
    }

    public void updateExercises(Long id, Exercise exercise) {
        patientRepo.findById(id)
                .map(patient -> {
                    patient.getExercises().add(exercise);
                    patientRepo.save(patient);
                    return patient;
                });
    }

    public void updateExercisesDelete(Long id, Exercise exercise) {
        patientRepo.findById(id)
                .map(patient -> {
                    patient.getExercises().remove(exercise);
                    patientRepo.save(patient);
                    return patient;
                });
    }

    public void updateFrequency(Long id, Frequency frequency) {
        patientRepo.findById(id)
                .map(patient -> {
                    patient.setFrequency(frequency);
                    patientRepo.save(patient);
                    return patient;
                });
    }

    public void updateTrainingDates(Long id, LocalDate date) {
        patientRepo.findById(id)
                .map(patient -> {
                    patient.getTrainingDates().add(date);
                    patientRepo.save(patient);
                    return patient;
                });
    }

    public void updateResultsDescription(Long id, String opinion) {
        patientRepo.findById(id)
                .map(patient -> {
                    patient.setResultsDescription(opinion);
                    patientRepo.save(patient);
                    return patient;
                });
    }

    public Iterable<Patient> findAll() {
        return patientRepo.findAll();
    }

    public Optional<Patient> findById(Long id) {
        return patientRepo.findById(id);
    }

}
