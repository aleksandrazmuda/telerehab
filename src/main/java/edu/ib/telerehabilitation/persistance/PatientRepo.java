package edu.ib.telerehabilitation.persistance;

import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.model.Specialist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepo extends CrudRepository<Patient, Long> {
    Patient findByUserName (String username);
    List<Patient> findBySpecialist (Specialist specialist);

}
