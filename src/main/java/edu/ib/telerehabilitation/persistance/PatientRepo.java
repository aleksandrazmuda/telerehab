package edu.ib.telerehabilitation.persistance;

import edu.ib.telerehabilitation.model.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepo extends CrudRepository<Patient, Long> {
    Patient findByEmail(String email);
    Patient findByUserName (String username);

}
