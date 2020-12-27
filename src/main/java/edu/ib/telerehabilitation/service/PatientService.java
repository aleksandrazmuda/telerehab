package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.model.Specialist;
import edu.ib.telerehabilitation.persistance.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Boolean checkIfUserExists(String email, String password) {
        Patient patient = patientRepo.findByEmail(email);
        boolean userExists;
        userExists = (patient != null && password.equals(patient.getPassword()));
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

    public Iterable<Patient> findAll() {
        return patientRepo.findAll();
    }

    public Optional<Patient> findById(Long id) {
        return patientRepo.findById(id);
    }

}
