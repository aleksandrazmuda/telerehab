package edu.ib.telerehabilitation.controller;

import edu.ib.telerehabilitation.datatransferobject.Role;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.model.Specialist;
import edu.ib.telerehabilitation.service.PatientService;
import edu.ib.telerehabilitation.service.SpecialistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private SpecialistService specialistService;
    private PatientService patientService;
    Specialist currentSpecialist;

    @Autowired
    public UserController(SpecialistService specialistService, PatientService patientService) {
        this.specialistService = specialistService;
        this.patientService = patientService;
    }

    @RequestMapping("/log")
    public String loginUser(@RequestParam(value = "email") String email,
                            @RequestParam(value = "password") String password,
                            @RequestParam(value = "role") Role role,
                            Model model) {

        if (role == Role.PATIENT) {
            if (patientService.checkIfUserExists(email, password)) {
                Patient currentPatient = patientService.findByEmail(email);
                model.addAttribute("currentPatient", currentPatient);
                return "profilePatient";
            }

        } else {
            if (specialistService.checkIfUserExists(email, password)) {
                currentSpecialist = specialistService.findByEmail(email);
                model.addAttribute("currentSpecialist", currentSpecialist);
                return "profileSpecialist";
            }
        }

        // powiadomienie o failure
        return "index";
    }

    @RequestMapping("/findPatient")
    public String findAndAddPatient(@RequestParam(value = "email") String email, Model model) {
        Patient patient = patientService.findByEmail(email);
        if (patient != null && patient.getSpecialist() == null) {
            patientService.updateSpecialist(patient.getId(), currentSpecialist);
            // komunikat o dodaniu
        } else {
            System.out.println("Fail");
            // komunikat o niepowodzeniu
        }

        model.addAttribute("currentSpecialist", currentSpecialist);
        return "profileSpecialist";
    }

}
