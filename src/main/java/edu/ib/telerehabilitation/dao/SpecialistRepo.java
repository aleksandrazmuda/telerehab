package edu.ib.telerehabilitation.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialistRepo extends CrudRepository<Specialist, Long> {
}
