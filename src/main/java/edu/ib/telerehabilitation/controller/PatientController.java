package edu.ib.telerehabilitation.controller;


import edu.ib.telerehabilitation.datatransferobject.ExerciseDTO;
import edu.ib.telerehabilitation.datatransferobject.PatientDTO;
import edu.ib.telerehabilitation.service.PatientProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class PatientController {

    private PatientProfileService patientProfileService;

    @Autowired
    public PatientController(PatientProfileService patientProfileService) {
        this.patientProfileService = patientProfileService;
    }


    @RequestMapping("/trainingDone")
    public String trainingDoneAddDate(Model model, Authentication authentication) {
        if(patientProfileService.updateTrainingDates(authentication, LocalDate.now()))
            model.addAttribute("success", "You successfully informed about the training done.");
        else
            model.addAttribute("error", "Something went wrong.");

        return "trainingDone";
    }


    @RequestMapping("/aboutPatient")
    public String seeAboutPatient(Model model, Authentication authentication) {
        model.addAttribute("patient", patientProfileService.getDataToAboutPatient(authentication));
        return "aboutPatient";
    }
}
