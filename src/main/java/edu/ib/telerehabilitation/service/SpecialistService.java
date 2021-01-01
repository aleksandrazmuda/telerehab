package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.datatransferobject.PatientDTO;
import edu.ib.telerehabilitation.datatransferobject.Role;
import edu.ib.telerehabilitation.datatransferobject.SpecialistDTO;
import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.Exercise;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.model.Specialist;
import edu.ib.telerehabilitation.persistance.PatientRepo;
import edu.ib.telerehabilitation.persistance.SpecialistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialistService implements UserService {

    private SpecialistRepo specialistRepo;
    private PatientRepo patientRepo;

    public SpecialistService(SpecialistRepo specialistRepo, PatientRepo patientRepo) {
        this.specialistRepo = specialistRepo;
        this.patientRepo = patientRepo;
    }

    @Override
    public Boolean addUser(UserDTO userDTO) {
        if (!checkIfUserExists(userDTO)) {
            Specialist specialist = new Specialist();
            specialist.setEmail(userDTO.getEmail());
            specialist.setUserName(userDTO.getUserName());
            specialist.setName(userDTO.getName());
            specialist.setSurname(userDTO.getSurname());
            specialist.setPhoneNumber(userDTO.getPhoneNumber());
            specialist.setPassword(userDTO.getPassword());
            specialistRepo.save(specialist);
            return true;
        }
        return false;

    }

    @Override
    public Boolean checkIfUserExists(UserDTO userDTO) {
        Specialist specialist = specialistRepo.findByEmail(userDTO.getEmail());
        boolean userExists;
        userExists = specialist != null && userDTO.getPassword().equals(specialist.getPassword());
        return userExists;
    }

    public SpecialistDTO getSpecialistIfExists(UserDTO userDTO) {
        Specialist specialistOptional = specialistRepo.findByEmail(userDTO.getEmail());
        SpecialistDTO specialist = new SpecialistDTO();
        specialist.setEmail(specialistOptional.getEmail());
        specialist.setName(specialistOptional.getName());
        specialist.setSurname(specialistOptional.getSurname());
        specialist.setUserName(specialistOptional.getUserName());
        specialist.setName(specialistOptional.getUserName());
        specialist.setPhoneNumber(specialistOptional.getPhoneNumber());
        return specialist;
    }


    public List<Patient> findPatientsOfSpecialist(UserDTO userDTOSpecialist) {
        Specialist specialist = specialistRepo.findByEmail(userDTOSpecialist.getEmail());
        List<Patient> patients = new ArrayList<>();
        for (Patient p : patientRepo.findAll()) {

            if (p.getSpecialist() == specialist) {
                patients.add(p);
            }
        }
        return patients;
    }

    public Boolean checkIfUserExistsByEmail(UserDTO userDTO) {
        Specialist specialist = specialistRepo.findByEmail(userDTO.getEmail());
        boolean userExists;
        userExists = (specialist != null);
        return userExists;
    }

}




