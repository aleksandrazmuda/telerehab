package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.datatransferobject.ExerciseDTO;
import edu.ib.telerehabilitation.datatransferobject.PatientDTO;
import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.Exercise;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.model.Specialist;
import edu.ib.telerehabilitation.model.User;
import edu.ib.telerehabilitation.persistance.ExerciseRepo;
import edu.ib.telerehabilitation.persistance.PatientRepo;
import edu.ib.telerehabilitation.persistance.SpecialistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SupportService {

    private PatientRepo patientRepo;
    private ExerciseRepo exerciseRepo;
    private UserService userService;

    @Autowired
    public SupportService(PatientRepo patientRepo, ExerciseRepo exerciseRepo, UserService userService) {
        this.patientRepo = patientRepo;
        this.exerciseRepo = exerciseRepo;
        this.userService = userService;
    }

    // PU Wyszukaj pacjentów w zbiorze specjalisty
    public List<Patient> findPatientsOfSpecialist(Specialist specialist) {
        return patientRepo.findBySpecialist(specialist);
    }


    // PU Wyszukaj ćwiczenia ze zbioru pacjenta
    public Set<Exercise> findExercisesOfPatient(Patient patient) {
        return patient.getExercises();
    }


    // Metoda pomocnicza
    public List<Exercise> getExercisesAll(Patient patient) {
        List<Exercise> exercisesAll = (List<Exercise>) exerciseRepo.findAll();
        exercisesAll.removeAll(patient.getExercises());
        return exercisesAll;
    }


    //    PU Operacje na specjalistach i ich pacjentach
    public Patient getPatientIfIsInCollection(Authentication authentication, String username) {
        Specialist specialist = (Specialist) userService.getCurrentUser(authentication);             //PU Wyszukaj specjalistę
        Patient patient = new Patient();
        if (specialist != null) {
            try {
                patient = findPatientsOfSpecialist(specialist)                                          //PU Wyszukaj pacjenta w zbiorze specjalisty
                        .stream()
                        .filter(x -> x.getUserName().equals(username))
                        .findFirst()
                        .orElseThrow(NullPointerException::new);
            } catch (Exception e) {
                patient = null;
            }
        }
        return patient;
    }


    // PU Wyszukaj pacjenta
    public Patient findPatientByUserName(String username) {
        return patientRepo.findByUserName(username);
    }


    // PU Wyszukaj ćwiczenie
    public Exercise findExerciseByName(String exerciseName) {
        return exerciseRepo.findByName(exerciseName);
    }

}
