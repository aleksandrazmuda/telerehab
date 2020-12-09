package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.dao.Specialist;
import edu.ib.telerehabilitation.dao.SpecialistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpecialistService {

    private SpecialistRepo specialistRepo;

    @Autowired
    public SpecialistService(SpecialistRepo specialistRepo) {
        this.specialistRepo = specialistRepo;
    }

    public Optional<Specialist> findById(Long id) {
        return specialistRepo.findById(id);
    }

}
