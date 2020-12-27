package edu.ib.telerehabilitation.controller;

import edu.ib.telerehabilitation.datatransferobject.Role;
import edu.ib.telerehabilitation.model.Exercise;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.model.Specialist;
import edu.ib.telerehabilitation.service.ExerciseService;
import edu.ib.telerehabilitation.service.PatientService;
import edu.ib.telerehabilitation.service.SpecialistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private SpecialistService specialistService;
    private PatientService patientService;
    private ExerciseService exerciseService;
    Specialist currentSpecialist;
    Patient currentPatient;

    @Autowired
    public UserController(SpecialistService specialistService, PatientService patientService, ExerciseService exerciseService) {
        this.specialistService = specialistService;
        this.patientService = patientService;
        this.exerciseService = exerciseService;
    }

    @RequestMapping("/log")
    public String loginUser(@RequestParam(value = "email") String email,
                            @RequestParam(value = "password") String password,
                            @RequestParam(value = "role") Role role,
                            Model model,
                            RedirectAttributes redirAttrs) {

        if (role == Role.PATIENT) {
            if (patientService.checkIfUserExists(email, password)) {
                currentPatient = patientService.findByEmail(email);
                List<Exercise> exercises = new ArrayList<>();
                for (Exercise e : exerciseService.findAll()
                ) {
                    if (currentPatient.getExercises().contains(e)) {
                        exercises.add(e);
                    }
                }

                model.addAttribute("exercises", exercises);
                model.addAttribute("currentPatient", currentPatient);
                return "profilePatient";
            }

        } else {
            if (specialistService.checkIfUserExists(email, password)) {
                currentSpecialist = specialistService.findByEmail(email);
                model.addAttribute("currentSpecialist", currentSpecialist);
                List<Patient> patients = new ArrayList<>();
                for (Patient p : patientService.findAll()
                ) {
                    if (p.getSpecialist() == currentSpecialist) {
                        patients.add(p);
                    }
                }
                Patient patientClicked = new Patient();
                model.addAttribute("patients", patients);
                model.addAttribute("patient", patientClicked);
                return "profileSpecialist";
            }
        }

        redirAttrs.addFlashAttribute("error", "Try again! You provided wrong e-mail or password.");
        return "redirect:/";
    }

    @RequestMapping("/findPatient")
    public String findAndAddPatient(@RequestParam(value = "email") String email, Model model) {
        Patient patient = patientService.findByEmail(email);
        if (patient != null && patient.getSpecialist() == null) {
            patientService.updateSpecialist(patient.getId(), currentSpecialist);
            model.addAttribute("success", "You have successfully added a new patient.");
        } else {
            model.addAttribute("error", "There's no such patient as " + email + ". Make sure you have a valid e-mail.");
        }

        List<Patient> patients = new ArrayList<>();
        for (Patient p : patientService.findAll()) {
            if (p.getSpecialist().getId().equals(currentSpecialist.getId())) {
                patients.add(p);
            }
        }
        Patient patientClicked = new Patient();
        model.addAttribute("patient", patientClicked);
        model.addAttribute("patients", patients); //redundant
        model.addAttribute("currentSpecialist", currentSpecialist);
        return "profileSpecialist";
    }

    @RequestMapping("/{id}")
    public String showPatient(@PathVariable Long id, Model model) {
        Optional<Patient> patientOptional = patientService.findById(id);
        Patient patient = null;
        if (patientOptional.isPresent()) {
            patient = patientOptional.get();
        } else {
            System.out.println("No patient with that id"); //error page?
        }

        List<Patient> patients = new ArrayList<>();
        for (Patient p : patientService.findAll()) {
            if (p.getSpecialist().getId().equals(currentSpecialist.getId())) {
                patients.add(p);
            }
        }


        model.addAttribute("currentSpecialist", currentSpecialist);
        model.addAttribute("patients", patients); //redundant lista - może metoda w service? findAllInSpecialistZbior
        model.addAttribute("patient", patient);

        return "profileSpecialist";
    }

    @RequestMapping("/aboutPatient")
    public String seeAboutPatient(Model model) {
        model.addAttribute("currentPatient", currentPatient);
        return "aboutPatient";
    }

    @RequestMapping("/aboutSpecialist")
    public String seeAboutSpecialist(Model model) {
        model.addAttribute("currentSpecialist", currentSpecialist);
        return "aboutSpecialist";
    }

    @RequestMapping("/profileSpecialist")
    public String seeProfileSpecialist(Model model) {

        List<Patient> patients = new ArrayList<>();
        for (Patient p : patientService.findAll()) {
            if (p.getSpecialist().getId().equals(currentSpecialist.getId())) {
                patients.add(p);
            }
        }

        Patient patientClicked = new Patient();
        model.addAttribute("patient", patientClicked);
        model.addAttribute("patients", patients); //redundant lista - może metoda w service? findAllInSpecialistZbior
        model.addAttribute("currentSpecialist", currentSpecialist);
        return "profileSpecialist";
    }

    @RequestMapping("/profilePatient")
    public String seeProfilePatient(Model model) {
        currentPatient = patientService.findByEmail(currentPatient.getEmail());
        List<Exercise> exercises = new ArrayList<>();
        for (Exercise e : exerciseService.findAll()
        ) {
            if (currentPatient.getExercises().contains(e)) {
                exercises.add(e);
            }
        }

        model.addAttribute("exercises", exercises);
        model.addAttribute("currentPatient", currentPatient);
        return "profilePatient";
    }
}
