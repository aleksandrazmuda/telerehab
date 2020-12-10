package edu.ib.telerehabilitation.controller;

import edu.ib.telerehabilitation.dao.Specialist;
import edu.ib.telerehabilitation.service.SpecialistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RegistrationController {

    private SpecialistService specialistService;

    @Autowired
    public RegistrationController(SpecialistService specialistService) {
        this.specialistService = specialistService;
    }

    @GetMapping("/registration")
    public String goToReg() {
        return "registration";
    }
}
