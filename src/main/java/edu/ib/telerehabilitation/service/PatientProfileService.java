package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.datatransferobject.ExerciseDTO;
import edu.ib.telerehabilitation.datatransferobject.PatientDTO;
import edu.ib.telerehabilitation.model.Exercise;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.persistance.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public List<ExerciseDTO> getTrainingPlan(Authentication authentication) {
        Patient userPatient = (Patient) userService.getCurrentUser(authentication);
        if(userPatient==null)
            return null;
        Set<Exercise> exercises = supportService.findExercisesOfPatient(userPatient);
        return exercises.stream()
                .map(ExerciseDTO::new)
                .collect(Collectors.toList());
    }


    // PU Zobacz informacje przypisane pacjentowi
    public PatientDTO getDataToAboutPatient(Authentication authentication) {
        Patient userPatient = (Patient) userService.getCurrentUser(authentication); // PU Wyszukaj pacjenta
        if(userPatient==null)
            return null;

        return new PatientDTO(userPatient.getEmail(), userPatient.getName(),
                userPatient.getPhoneNumber(), userPatient.getResultsDescription(),
                userPatient.getFrequency(), userPatient.getTrainingDates());
    }


    // PU Dodaj informację o wykonanym treningu do historii
    public Boolean updateTrainingDates(Authentication authentication, LocalDate date) {
        Patient userPatient = (Patient) userService.getCurrentUser(authentication); // PU Wyszukaj pacjenta
        if (userPatient == null)
            return false;

        userPatient.getTrainingDates().add(date);
        patientRepo.save(userPatient);
        return true;
    }


}
