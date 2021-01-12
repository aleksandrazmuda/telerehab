package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.Exercise;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.model.Specialist;
import edu.ib.telerehabilitation.model.User;
import edu.ib.telerehabilitation.persistance.ExerciseRepo;
import edu.ib.telerehabilitation.persistance.PatientRepo;
import edu.ib.telerehabilitation.persistance.SpecialistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupportService {

    private PatientRepo patientRepo;
    private ExerciseRepo exerciseRepo;
    private SpecialistRepo specialistRepo;

    @Autowired
    public SupportService(PatientRepo patientRepo, ExerciseRepo exerciseRepo, SpecialistRepo specialistRepo) {
        this.patientRepo = patientRepo;
        this.exerciseRepo = exerciseRepo;
        this.specialistRepo = specialistRepo;
    }

    // PU Wyszukaj pacjentów w zbiorze specjalisty
    public List<Patient> findPatientsOfSpecialist(User userSpecialist) {
        List<Patient> patients = new ArrayList<>();
        for (Patient p : patientRepo.findAll()) {
            if (p.getSpecialist() != null) {
                if (p.getSpecialist().getUserName().equals(userSpecialist.getUserName())) {
                    patients.add(p);
                }
            }
        }
        return patients;
    }

    // Metoda pomocnicza
    public Boolean checkIfPatientIsInCollection(User userSpecialist, Patient patient) {
        List<Patient> patients = findPatientsOfSpecialist(userSpecialist);  // PU Wyszukaj pacjentów w zbiorze specjalisty
        return patients.contains(patient);
    }


    // PU Wyszukaj ćwiczenia ze zbioru pacjenta
    public List<Exercise> findExercisesOfPatient(Patient patient) {
        List<Exercise> exercises = new ArrayList<>();
        for (Exercise e : exerciseRepo.findAll()
        ) {
            if (patient.getExercises().contains(e)) {
                exercises.add(e);
            }
        }
        return exercises;
    }


    // Metoda pomocnicza
    public Boolean checkIfExerciseIsInCollection(User userPatient, Exercise exercise) {
        List<Exercise> exercises = findExercisesOfPatient((Patient) userPatient);
        return exercises.contains(exercise);
    }


    // PU Wyszukaj pacjenta
    public Patient findPatientByUserName(String username) {
        return patientRepo.findByUserName(username);
    }


    // PU Wyszukaj specjalistę
    public Specialist findSpecialistByUserName(String username) {
        return specialistRepo.findByUserName(username);
    }


    // PU Wyszukaj ćwiczenie
    public Exercise findExerciseByName(String exerciseName) {
        return exerciseRepo.findByName(exerciseName);
    }


    // Metoda pomocnicza
    public List<Exercise> getExercisesAll(Patient patient) {
        List<Exercise> exercisesAll;
        exercisesAll = (List<Exercise>) exerciseRepo.findAll();
        exercisesAll.removeIf(e -> patient.getExercises().contains(e));
        return exercisesAll;
    }

    // PU Operacje na specjalistach i ich pacjentach
    public Patient getPatientIfIsInCollection(User userSpecialist, String username) {

        Specialist specialist = findSpecialistByUserName(userSpecialist.getUserName()); //PU Wyszukaj specjalistę
        Patient patient = new Patient();

        if (specialist != null){
            patient = findPatientByUserName(username);}  //PU Wyszukaj pacjenta

        if (patient != null && checkIfPatientIsInCollection(userSpecialist,patient)) {    //PU Wyszukaj pacjentów w zbiorze specjalisty
            return patient;
        }

        return null;
    }

}
