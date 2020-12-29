package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.model.Exercise;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.persistance.ExerciseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExerciseService {

    private ExerciseRepo exerciseRepo;

    @Autowired
    public ExerciseService(ExerciseRepo exerciseRepo) {
        this.exerciseRepo = exerciseRepo;
    }

    public Iterable<Exercise> findAll() {
        return exerciseRepo.findAll();
    }

    public Exercise findByName(String name) {
        return exerciseRepo.findByName(name);
    }

    public Optional<Exercise> findById(Long id) {
        return exerciseRepo.findById(id);
    }
}
