package edu.ib.telerehabilitation.controller;

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
        if (patientProfileService.updateTrainingDates(authentication, LocalDate.now())) {
            patientProfileService.getDataToProfilePatient(model, authentication);
            model.addAttribute("success", "You successfully informed about the training done.");
            return "profilePatient";
        }
        return "error";
    }


    @RequestMapping("/aboutPatient")
    public String seeAboutPatient(Model model, Authentication authentication) {
        patientProfileService.getDataToAboutPatient(model, authentication);
        return "aboutPatient";
    }
}
