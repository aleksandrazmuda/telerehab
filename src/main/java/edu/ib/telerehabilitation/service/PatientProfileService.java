package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.Exercise;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.model.User;
import edu.ib.telerehabilitation.persistance.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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
    public void getDataToProfilePatient(Model model, Authentication authentication) {
        User userPatient= userService.getCurrentUser(authentication);
        Patient patient = supportService.findPatientByUserName(userPatient.getUserName());    // PU Wyszukaj pacjenta
        List<Exercise> exercises = supportService.findExercisesOfPatient(patient);    // PU Wyszukaj ćwiczenia ze zbioru pacjenta
        model.addAttribute("currentPatient", patient);
        model.addAttribute("exercises", exercises);
    }

    // PU Zobacz informacje o sobie (nie tylko wyniki rehabilitacji)
    public void getDataToAboutPatient(Model model, Authentication authentication) {
        User userPatient = userService.getCurrentUser(authentication);
        Patient patient = supportService.findPatientByUserName(userPatient.getUserName()); // PU Wyszukaj pacjenta
        model.addAttribute("currentPatient", patient);
    }

    // PU Dodaj informację o wykonanym treningu do historii
    public Boolean updateTrainingDates(Authentication authentication, LocalDate date) {
        User userPatient = userService.getCurrentUser(authentication);
        if (userPatient.getUserName() != null) {
            Patient patient = supportService.findPatientByUserName(userPatient.getUserName());  //PU Wyszukaj pacjenta
            patient.getTrainingDates().add(date);
            patientRepo.save(patient);
            return true;
        }
        return false;
    }
}
