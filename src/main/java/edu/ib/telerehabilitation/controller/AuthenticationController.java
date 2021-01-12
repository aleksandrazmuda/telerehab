package edu.ib.telerehabilitation.controller;

import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.model.Specialist;
import edu.ib.telerehabilitation.model.User;
import edu.ib.telerehabilitation.securityServices.SecurityService;
import edu.ib.telerehabilitation.securityServices.UserValidator;
import edu.ib.telerehabilitation.service.PatientProfileService;
import edu.ib.telerehabilitation.service.SpecialistProfileService;
import edu.ib.telerehabilitation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthenticationController {

    private UserService userService;
    private SecurityService securityService;
    private SpecialistProfileService specialistProfileService;
    private PatientProfileService patientProfileService;
    private UserValidator userValidator;

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
        model.addAttribute("userForm", new User());
        return "registration";
    }


    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") UserDTO userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.addUser(userForm);
        securityService.autoLogin(userForm.getUserName(), userForm.getPasswordConfirm());
        return "redirect:/welcome";
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
            patientProfileService.getDataToProfilePatient(model, authentication);
            return "profilePatient";
        } else {
            specialistProfileService.getDataToProfileSpecialist(model, authentication, null);
            return "profileSpecialist";
        }

    }


}
