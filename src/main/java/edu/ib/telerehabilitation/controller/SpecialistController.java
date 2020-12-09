package edu.ib.telerehabilitation.controller;

import edu.ib.telerehabilitation.dao.Specialist;
import edu.ib.telerehabilitation.service.SpecialistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SpecialistController {

    private SpecialistService specialistService;

    @Autowired
    public SpecialistController(SpecialistService specialistService) {
        this.specialistService = specialistService;
    }

//    @GetMapping("")
//    public String getSpecialists(Model model) {
//
//        return "";
//    }
}
