package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.*;
import edu.ib.telerehabilitation.persistance.ExerciseRepo;
import edu.ib.telerehabilitation.persistance.PatientRepo;
import edu.ib.telerehabilitation.persistance.SpecialistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SpecialistProfileService {

    private PatientRepo patientRepo;
    private ExerciseRepo exerciseRepo;
    private UserService userService;
    private SupportService supportService;

    @Autowired
    public SpecialistProfileService(PatientRepo patientRepo, ExerciseRepo exerciseRepo, UserService userService, SupportService supportService) {
        this.patientRepo = patientRepo;
        this.exerciseRepo = exerciseRepo;
        this.userService = userService;
        this.supportService = supportService;
    }


    //PU Dodaj pacjenta
    public Boolean addPatientToTheCollectionOfSpecialist(UserDTO userDTOPatient, Authentication authentication) {
        User userSpecialist = userService.getCurrentUser(authentication);
        Specialist specialist = supportService.findSpecialistByUserName(userSpecialist.getUserName()); //PU Wyszukaj specjalistę

        if (userDTOPatient != null) {
            Patient patient = supportService.findPatientByUserName(userDTOPatient.getUserName()); //PU Wyszukaj pacjenta
            if (patient != null && !supportService.checkIfPatientIsInCollection(userSpecialist, patient)) {   //PU Wyszukaj pacjentów ze zbioru specjalisty
                patient.setSpecialist(specialist);
                patientRepo.save(patient);
                return true;
            }
        }
        return false;
    }


    // PU Sprawdź dane dotyczące pacjenta
    public void getDataToProfileSpecialist(Model model, Authentication authentication, String username) {
        User userSpecialist = userService.getCurrentUser(authentication);
        Patient patientClicked = supportService.getPatientIfIsInCollection(userSpecialist, username);   //PU operacje na specjalistach i ich pacjentach
        List<Exercise> exercises = new ArrayList<>();

        if (patientClicked != null) {
            List<Exercise> exercisesAll = supportService.getExercisesAll(patientClicked);
            List<Frequency> frequenciesAll = new ArrayList<>(Arrays.asList(Frequency.values()));
            exercises = supportService.findExercisesOfPatient(patientClicked);
            model.addAttribute("patientClicked", patientClicked);
            model.addAttribute("frequencyList", frequenciesAll);
            model.addAttribute("exerciseList", exercisesAll);
        }

        List<Patient> patients = supportService.findPatientsOfSpecialist(userSpecialist);
        model.addAttribute("currentSpecialist", userSpecialist);
        model.addAttribute("patients", patients);
        model.addAttribute("exercises", exercises);
    }


    // PU zmień częstotliwość
    public Boolean updateFrequency(String username, Frequency frequency, Authentication authentication) {
        User userSpecialist = userService.getCurrentUser(authentication);
        Patient patient = supportService.getPatientIfIsInCollection(userSpecialist, username);  //PU Operacje na specjalistach i ich pacjentach

        if (patient != null) {
            patient.setFrequency(frequency);
            patientRepo.save(patient);
            return true;
        }
        return false;
    }


    // PU Opisz wyniki rehabilitacji pacjenta
    public Boolean updateResultsDescription(String username, String opinion, Authentication authentication) {
        User userSpecialist = userService.getCurrentUser(authentication);
        Patient patient = supportService.getPatientIfIsInCollection(userSpecialist, username); //PU Operacje na specjalistach i ich pacjentach

        if (patient != null) {
            patient.setResultsDescription(opinion);
            patientRepo.save(patient);
            return true;

        }
        return false;
    }


    // PU usuń cwiczenie z planu pacjenta
    public Boolean deleteExercise(String username, String exerciseName, Authentication authentication) {
        User userSpecialist = userService.getCurrentUser(authentication);
        Patient patient = supportService.getPatientIfIsInCollection(userSpecialist, username); //PU Operacje na specjalistach i ich pacjentach
        Exercise exercise = exerciseRepo.findByName(exerciseName);  //PU Wyszukaj ćwiczenie

        if (patient != null) {
            if (supportService.checkIfExerciseIsInCollection(patient, exercise)) { //PU Wyszukaj ćwiczenia ze zbioru pacjenta
                patient.getExercises().remove(exercise);
                patientRepo.save(patient);
                return true;
            }
        }
        return false;
    }


    // PU Dodaj ćwiczenie do planu pacjenta
    public Boolean addExercise(String username, String exerciseName, Authentication authentication) {
        User userSpecialist = userService.getCurrentUser(authentication);
        Patient patient = supportService.getPatientIfIsInCollection(userSpecialist, username); //PU Operacje na specjalistach i ich pacjentach
        Exercise exercise = supportService.findExerciseByName(exerciseName); // PU Wyszukaj ćwiczenie

        if (patient != null) {
            if (!supportService.checkIfExerciseIsInCollection(patient, exercise)) { //PU Wyszukaj ćwiczenia ze zbioru pacjenta
                patient.getExercises().add(exercise);
                patientRepo.save(patient);
                return true;
            }
        }
        return false;
    }


}
