package edu.ib.telerehabilitation.controller;

import edu.ib.telerehabilitation.datatransferobject.*;

import edu.ib.telerehabilitation.model.Frequency;
import edu.ib.telerehabilitation.service.SpecialistProfileService;
import edu.ib.telerehabilitation.service.SupportService;
import edu.ib.telerehabilitation.service.UserService;
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
    public SpecialistController(SpecialistProfileService specialistProfileService, SupportService supportService, UserService userService) {
        this.specialistProfileService = specialistProfileService;
    }


    @RequestMapping("/findPatient")
    public String findAndAddPatient(@RequestParam(value = "username") String username, Model model, Authentication authentication) {
        SpecialistDTO specialistDTO = new SpecialistDTO();
        if (specialistProfileService.addPatientToTheCollectionOfSpecialist(specialistDTO, username, authentication)) {
            model.addAttribute("nameSpecialist", specialistDTO.getName());
            model.addAttribute("patients", specialistDTO.getPatientDTOList());
            model.addAttribute("success", "You have successfully added a new patient.");
        } else {
            model.addAttribute("nameSpecialist", specialistDTO.getName());
            model.addAttribute("patients", specialistDTO.getPatientDTOList());
            model.addAttribute("error", "There's no such patient as " + username + ". Make sure you have" +
                    " a valid username or the patient is not already in your dataset.");
        }

        return "profileSpecialist";
    }


    @RequestMapping("/{username}")
    public String showPatient(@PathVariable String username, Model model, Authentication authentication) {
        PatientDTO patientDTO = new PatientDTO();
        SupportProfileDTO supportProfileDTO = new SupportProfileDTO();
        specialistProfileService.getDataOfPatient(supportProfileDTO, patientDTO,
                authentication, username);
        return setModelPatientOperations(model, patientDTO, supportProfileDTO);
    }


    @RequestMapping("/updateFrequency")
    public String changeFreq(@ModelAttribute("username") String username,
                             @RequestParam(value = "frequencyChosen") Frequency newFrequency,
                             Model model, Authentication authentication) {

        PatientDTO patientDTO = new PatientDTO();
        SupportProfileDTO supportProfileDTO = new SupportProfileDTO();
        specialistProfileService.updateFrequency(patientDTO, supportProfileDTO, username, newFrequency, authentication);
        return setModelPatientOperations(model, patientDTO, supportProfileDTO);
    }


    @RequestMapping("/opinion")
    public String updateOpinion(@ModelAttribute("username") String username,
                                @RequestParam(value = "opinion") String opinion,
                                Model model,
                                Authentication authentication) {
        PatientDTO patientDTO = new PatientDTO();
        SupportProfileDTO supportProfileDTO = new SupportProfileDTO();
        specialistProfileService.updateResultsDescription(patientDTO, supportProfileDTO, username, opinion, authentication);
        return setModelPatientOperations(model, patientDTO, supportProfileDTO);
    }


    @RequestMapping("/delete/{exerciseName}")
    public String deleteExercise(@ModelAttribute("username") String username,
                                 @PathVariable String exerciseName, Model model,
                                 Authentication authentication) {
        PatientDTO patientDTO = new PatientDTO();
        SupportProfileDTO supportProfileDTO = new SupportProfileDTO();
        specialistProfileService.deleteExercise(patientDTO, supportProfileDTO, username, exerciseName, authentication);
        return setModelPatientOperations(model, patientDTO, supportProfileDTO);
    }


    @RequestMapping("/addNewExercise")
    public String addExercise(@ModelAttribute("username") String username,
                              @RequestParam(value = "exerciseChosen") String newExerciseName,
                              Model model, Authentication authentication) {
        PatientDTO patientDTO = new PatientDTO();
        SupportProfileDTO supportProfileDTO = new SupportProfileDTO();
        specialistProfileService.addExercise(patientDTO, supportProfileDTO, username, newExerciseName, authentication);
        return setModelPatientOperations(model, patientDTO, supportProfileDTO);
    }


    @RequestMapping("/aboutSpecialist")
    public String seeAboutSpecialist(Model model, Authentication authentication) {
        model.addAttribute("currentSpecialist", specialistProfileService.getDataToAboutSpecialist(authentication));
        return "aboutSpecialist";
    }


    @RequestMapping("/callPatient")
    public String call(@ModelAttribute("username") String username,
                       Model model, Authentication authentication) {
        //supportService.getPatientIfIsInCollection(userService.getCurrentUser(authentication), username);
        return "rtc";
    }


    public String setModelPatientOperations(Model model, PatientDTO patientDTO, SupportProfileDTO supportProfileDTO) {
        model.addAttribute("patientClicked", patientDTO);
        model.addAttribute("frequenciesAll", supportProfileDTO.getFrequencies());
        model.addAttribute("exercises", supportProfileDTO.getExercises());
        model.addAttribute("exercisesAll", supportProfileDTO.getExercisesAll());
        return "patientOperations";
    }


}
