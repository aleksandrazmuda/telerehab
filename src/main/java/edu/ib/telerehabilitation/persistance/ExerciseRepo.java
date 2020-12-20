package edu.ib.telerehabilitation.persistance;

import edu.ib.telerehabilitation.model.Exercise;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepo extends CrudRepository<Exercise, Long> {
}
