package edu.ib.telerehabilitation.persistance;

import edu.ib.telerehabilitation.model.Specialist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialistRepo extends CrudRepository<Specialist, Long> {
    Specialist findByEmail(String email);
}
