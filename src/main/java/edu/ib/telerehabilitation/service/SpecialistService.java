package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.datatransferobject.Role;
import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.model.Specialist;
import edu.ib.telerehabilitation.persistance.SpecialistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpecialistService implements UserService {

    private SpecialistRepo specialistRepo;

    @Autowired
    public SpecialistService(SpecialistRepo specialistRepo) {
        this.specialistRepo = specialistRepo;
    }

    public Optional<Specialist> findById(Long id) {
        return specialistRepo.findById(id);
    }

    public Specialist findByEmail(String email) {
        return specialistRepo.findByEmail(email);
    }

    @Override
    public void addUser(UserDTO userDTO) {
        Specialist specialist = new Specialist();
        specialist.setEmail(userDTO.getEmail());
        specialist.setUserName(userDTO.getUserName());
        specialist.setName(userDTO.getName());
        specialist.setSurname(userDTO.getSurname());
        specialist.setPhoneNumber(userDTO.getPhoneNumber());
        specialist.setPassword(userDTO.getPassword());
        specialistRepo.save(specialist);
    }

    @Override
    public Boolean checkIfUserExists(String email, String password) {
        Specialist specialist = specialistRepo.findByEmail(email);
        boolean userExists;
        userExists = (specialist != null && password.equals(specialist.getPassword()));
        return userExists;
    }
}
