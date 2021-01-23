package edu.ib.telerehabilitation.persistance;

import edu.ib.telerehabilitation.model.Exercise;
import edu.ib.telerehabilitation.model.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ExerciseRepo extends CrudRepository<Exercise, Long> {
    Exercise findByName(String name);
}
