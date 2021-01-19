package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.datatransferobject.ExerciseDTO;
import edu.ib.telerehabilitation.datatransferobject.PatientDTO;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.persistance.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientProfileService {

    private UserService userService;
    private PatientRepo patientRepo;
    private SupportService supportService;

    @Autowired
    public PatientProfileService(UserService userService, PatientRepo patientRepo, SupportService supportService) {
        this.userService = userService;
        this.patientRepo = patientRepo;
        this.supportService = supportService;
    }

    // PU Wybierz bieżący plan treningowy
    public PatientDTO getDataToProfilePatient(Authentication authentication) {
        Patient userPatient = (Patient) userService.getCurrentUser(authentication); // PU Wyszukaj pacjenta
        List<ExerciseDTO> exercises = supportService.findExercisesOfPatient(userPatient);    // PU Wyszukaj ćwiczenia ze zbioru pacjenta
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setName(userPatient.getName());
        patientDTO.setExercises(exercises);
        return patientDTO;

    }


    // PU Zobacz informacje o sobie (nie tylko wyniki rehabilitacji)
    public PatientDTO getDataToAboutPatient(Authentication authentication) {
        Patient userPatient = (Patient) userService.getCurrentUser(authentication); // PU Wyszukaj pacjenta
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setName(userPatient.getName());
        patientDTO.setEmail(userPatient.getEmail());
        patientDTO.setPhoneNumber(userPatient.getPhoneNumber());
        patientDTO.setFrequency(userPatient.getFrequency());
        patientDTO.setResultsDescription(userPatient.getResultsDescription());
        patientDTO.setTrainingDates(userPatient.getTrainingDates());
        return patientDTO;
    }


    // PU Dodaj informację o wykonanym treningu do historii
    public PatientDTO updateTrainingDates(Authentication authentication, LocalDate date) {
        Patient userPatient = (Patient) userService.getCurrentUser(authentication); // PU Wyszukaj pacjenta
        userPatient.getTrainingDates().add(date);
        patientRepo.save(userPatient);
        return getDataToProfilePatient(authentication);
    }

}
