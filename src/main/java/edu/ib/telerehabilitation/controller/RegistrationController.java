package edu.ib.telerehabilitation.controller;

import edu.ib.telerehabilitation.datatransferobject.Role;
import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.model.Specialist;
import edu.ib.telerehabilitation.service.PatientService;
import edu.ib.telerehabilitation.service.SpecialistService;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    private SpecialistService specialistService;
    private PatientService patientService;

    @Autowired
    public RegistrationController(SpecialistService specialistService, PatientService patientService) {
        this.specialistService = specialistService;
        this.patientService = patientService;
    }


    @RequestMapping("/registration")
    public String goToReg(Model model) {
        model.addAttribute("newUserDTO", new UserDTO());
        return "registration";
    }

    @RequestMapping("/addUser")
    public String addUserDTO(@RequestBody @ModelAttribute UserDTO userDTO) {

        if (userDTO.getRole() == Role.PATIENT) {
            if ((!patientService.checkIfUserExists(userDTO.getEmail(), userDTO.getPassword())) && (patientService.findByEmail(userDTO.getEmail()) == null))
                patientService.addUser(userDTO);
        } else {
            if ((!specialistService.checkIfUserExists(userDTO.getEmail(), userDTO.getPassword())) && (specialistService.findByEmail(userDTO.getEmail()) == null))
                specialistService.addUser(userDTO);
        }
        // powiadomienie success lub failure
        return "index";
    }

}