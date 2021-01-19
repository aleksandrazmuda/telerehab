package edu.ib.telerehabilitation.controller;


import edu.ib.telerehabilitation.datatransferobject.PatientDTO;
import edu.ib.telerehabilitation.service.PatientProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
public class PatientController {

    private PatientProfileService patientProfileService;

    @Autowired
    public PatientController(PatientProfileService patientProfileService) {
        this.patientProfileService = patientProfileService;
    }

    @RequestMapping("/trainingDone")
    public String trainingDoneAddDate(Model model, Authentication authentication) {
        PatientDTO patientDTO = patientProfileService.updateTrainingDates(authentication, LocalDate.now());
        model.addAttribute("patientName", patientDTO.getName());
        model.addAttribute("exercises", patientDTO.getExercises());
        model.addAttribute("success", "You successfully informed about the training done.");
        return "profilePatient";
    }


    @RequestMapping("/aboutPatient")
    public String seeAboutPatient(Model model, Authentication authentication) {
        model.addAttribute("patient", patientProfileService.getDataToAboutPatient(authentication));
        return "aboutPatient";
    }
}
