package edu.ib.telerehabilitation.controller;

import edu.ib.telerehabilitation.datatransferobject.*;
import edu.ib.telerehabilitation.model.User;
import edu.ib.telerehabilitation.securityServices.SecurityService;
import edu.ib.telerehabilitation.service.PatientProfileService;
import edu.ib.telerehabilitation.service.SpecialistProfileService;
import edu.ib.telerehabilitation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class AuthenticationController {

    private UserService userService;
    private SecurityService securityService;
    private UserValidator userValidator;
    private SpecialistProfileService specialistProfileService;
    private PatientProfileService patientProfileService;


    @Autowired
    public AuthenticationController(UserService userService, SecurityService securityService, SpecialistProfileService specialistProfileService, PatientProfileService patientProfileService, UserValidator userValidator) {
        this.userService = userService;
        this.securityService = securityService;
        this.specialistProfileService = specialistProfileService;
        this.patientProfileService = patientProfileService;
        this.userValidator = userValidator;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        model.addAttribute("userForm", new UserDTO());
        return "registration";
    }


    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") UserDTO userForm,
                               BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors())
            return "registration";
        if (!userService.addUser(userForm)) {
            model.addAttribute("usernameError", "This username is not available.");
            return "registration";
        }
        return "login";
    }


    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }


    @GetMapping({"/", "/welcome"})
    public String welcome(Model model, Authentication authentication) {
        if (authentication.getAuthorities().toString().equals("[PATIENT]")) {
            List<ExerciseDTO> exercises = patientProfileService.getTrainingPlan(authentication);
            model.addAttribute("exercises", exercises);
            return "profilePatient";
        } else {
            SpecialistDTO specialistDTO = new SpecialistDTO();
            specialistProfileService.getDataToProfileSpecialist(specialistDTO, authentication);
            model.addAttribute("nameSpecialist", specialistDTO.getName());
            model.addAttribute("patients", specialistDTO.getPatientDTOList());
            return "profileSpecialist";
        }

    }


}
