package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.datatransferobject.PatientDTO;
import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.Exercise;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.persistance.ExerciseRepo;
import edu.ib.telerehabilitation.persistance.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    private ExerciseRepo exerciseRepo;
    private PatientService patientService;
    private PatientRepo patientRepo;

    @Autowired
    public ExerciseService(ExerciseRepo exerciseRepo, PatientService patientService, PatientRepo patientRepo) {
        this.exerciseRepo = exerciseRepo;
        this.patientService = patientService;
        this.patientRepo = patientRepo;
    }

    public List<Exercise> findExercisesOfPatient(UserDTO userDTO) {
        List<Exercise> exercises = new ArrayList<>();
        for (Exercise e : exerciseRepo.findAll()
        ) {
            if (patientService.findByEmail(userDTO.getEmail()).getExercises().contains(e)) {
                exercises.add(e);
            }
        }
        return exercises;
    }

    public List<Exercise> getExercisesAll(UserDTO userDTO) {

        List<Exercise> exercisesAll = new ArrayList<>();
        PatientDTO patientClicked = patientService.getPatientIfExists(userDTO);

        if (patientClicked != null && patientClicked.getExercises() != null) {
            exercisesAll = (List<Exercise>) exerciseRepo.findAll();
            for (Exercise e : exerciseRepo.findAll()) {
                if (patientClicked.getExercises().contains(e)) {
                    exercisesAll.remove(e);
                }
            }
        }
        return exercisesAll;
    }

    public Boolean updateExercises(UserDTO userDTOPatient, String exerciseName) {
        Exercise exercise = exerciseRepo.findByName(exerciseName);
        if (patientService.checkIfUserExistsByEmail(userDTOPatient)) {
            Long id = patientService.findByEmail(userDTOPatient.getEmail()).getId();
            patientService.findById(id)
                    .map(patient -> {
                        patient.getExercises().add(exercise);
                        patientRepo.save(patient);
                        return patient;
                    });
            return true;
        }
        return false;
    }

    public Boolean updateExercisesDelete(UserDTO userDTOPatient, String exerciseName) {
        Exercise exercise = exerciseRepo.findByName(exerciseName);
        if (patientService.checkIfUserExistsByEmail(userDTOPatient)) {
            Long id = patientService.findByEmail(userDTOPatient.getEmail()).getId();
            patientService.findById(id)
                    .map(patient -> {
                        patient.getExercises().remove(exercise);
                        patientRepo.save(patient);
                        return patient;
                    });
            return true;
        }
        return false;
    }

}
