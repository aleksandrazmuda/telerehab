package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.model.Specialist;
import edu.ib.telerehabilitation.model.User;
import edu.ib.telerehabilitation.persistance.PatientRepo;
import edu.ib.telerehabilitation.persistance.SpecialistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.ValidationUtils;

@Service
public class UserServiceImpl implements UserService {

    private PatientRepo patientRepo;
    private SpecialistRepo specialistRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(PatientRepo patientRepo, SpecialistRepo specialistRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.patientRepo = patientRepo;
        this.specialistRepo = specialistRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // PU Zarejestruj się
    @Override
    public Boolean addUser(UserDTO userDTO) {

        if (findByUsername(userDTO.getUserName()) != null) {
            return false;
        } else {
            if (userDTO.getRole().equals("PATIENT")) {
                Patient patient = new Patient();
                patient.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
                patient.setRole(userDTO.getRole());
                patient.setUserName(userDTO.getUserName());
                patient.setPhoneNumber(userDTO.getPhoneNumber());
                patient.setEmail(userDTO.getEmail());
                patient.setName(userDTO.getName());
                patient.setSurname(userDTO.getSurname());
                patientRepo.save(patient);

            } else if (userDTO.getRole().equals("SPECIALIST")) {
                Specialist specialist = new Specialist();
                specialist.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
                specialist.setRole(userDTO.getRole());
                specialist.setUserName(userDTO.getUserName());
                specialist.setPhoneNumber(userDTO.getPhoneNumber());
                specialist.setEmail(userDTO.getEmail());
                specialist.setName(userDTO.getName());
                specialist.setSurname(userDTO.getSurname());
                specialistRepo.save(specialist);
            }
            return true;
        }
    }


    @Override
    public User getCurrentUser(Authentication authentication) {
        UserDetails currentUserDetails = (authentication == null) ? null : (UserDetails) authentication.getPrincipal();
        User currentUser = new User();
        if (currentUserDetails != null) {
            currentUser = findByUsername(currentUserDetails.getUsername());
        }
        return currentUser;
    }


    @Override
    public User findByUsername(String username) {
        User user = patientRepo.findByUserName(username);
        if (user == null) {
            user = specialistRepo.findByUserName(username);
        }
        return user;
    }

}
