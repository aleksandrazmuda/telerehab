package edu.ib.telerehabilitation.controller;

import edu.ib.telerehabilitation.dao.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PatientController {

    private PatientRepo patientRepo;

    @Autowired
    public PatientController(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }
}
