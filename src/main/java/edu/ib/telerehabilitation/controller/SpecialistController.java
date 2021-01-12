package edu.ib.telerehabilitation.controller;

import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.Frequency;
import edu.ib.telerehabilitation.service.SpecialistProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SpecialistController {


    private SpecialistProfileService specialistProfileService;

    @Autowired
    public SpecialistController(SpecialistProfileService specialistProfileService) {
        this.specialistProfileService = specialistProfileService;
    }

    @RequestMapping("/findPatient")
    public String findAndAddPatient(@RequestParam(value = "username") String username, Model model, Authentication authentication) {

        UserDTO patientDTO = new UserDTO();
        patientDTO.setUserName(username);
        if (specialistProfileService.addPatientToTheCollectionOfSpecialist(patientDTO, authentication)) {
            model.addAttribute("success", "You have successfully added a new patient.");
        } else {
            model.addAttribute("error", "There's no such patient as " + username + ". Make sure you have" +
                    " a valid username or the patient is not already in your dataset.");
        }

        specialistProfileService.getDataToProfileSpecialist(model, authentication, null);
        return "profileSpecialist";
    }

    @RequestMapping("/{username}")
    public String showPatient(@PathVariable String username, Model model, Authentication authentication) {
        specialistProfileService.getDataToProfileSpecialist(model, authentication, username);
        return "profileSpecialist";
    }


    @RequestMapping("/updateFrequency")
    public String changeFreq(@ModelAttribute("username") String username,
                             @RequestParam(value = "frequencyChosen") Frequency newFrequency,
                             Model model, Authentication authentication) {

        if (specialistProfileService.updateFrequency(username, newFrequency, authentication)) {
            specialistProfileService.getDataToProfileSpecialist(model, authentication, username);
            return "profileSpecialist";
        } else
            return "error";
    }


    @RequestMapping("/opinion")
    public String updateOpinion(@ModelAttribute("username") String username,
                                @RequestParam(value = "opinion") String opinion,
                                Model model,
                                Authentication authentication) {

        if (specialistProfileService.updateResultsDescription(username, opinion, authentication)) {
            specialistProfileService.getDataToProfileSpecialist(model, authentication, username);
            return "profileSpecialist";
        } else {
            return "error";
        }
    }


    @RequestMapping("/deleteExercise/{exerciseName}")
    public String deleteExercise(@ModelAttribute("username") String username,
                                 @PathVariable String exerciseName, Model model,
                                 Authentication authentication) {
        if (specialistProfileService.deleteExercise(username, exerciseName, authentication)) {
            specialistProfileService.getDataToProfileSpecialist(model, authentication, username);
            return "profileSpecialist";
        } else {
            return "error";
        }
    }


    @RequestMapping("/addNewExercise")
    public String addExercise(@ModelAttribute("username") String username,
                              @RequestParam(value = "exerciseChosen") String newExerciseName,
                              Model model, Authentication authentication) {
        if (specialistProfileService.addExercise(username, newExerciseName, authentication)) {
            specialistProfileService.getDataToProfileSpecialist(model, authentication, username);
            return "profileSpecialist";
        } else {
            return "error";
        }
    }


    @RequestMapping("/aboutSpecialist")
    public String seeAboutSpecialist(Model model, Authentication authentication) {
        specialistProfileService.getDataToProfileSpecialist(model, authentication, null);
        return "aboutSpecialist";
    }

}
