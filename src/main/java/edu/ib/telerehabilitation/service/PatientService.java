package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.dao.Patient;
import edu.ib.telerehabilitation.dao.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {

    private PatientRepo patientRepo;

    @Autowired
    public PatientService(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }

    public Optional<Patient> findById(Long id) {
        return patientRepo.findById(id);
    }
}
