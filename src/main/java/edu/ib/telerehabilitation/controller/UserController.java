package edu.ib.telerehabilitation.controller;

import edu.ib.telerehabilitation.datatransferobject.Role;
import edu.ib.telerehabilitation.model.Exercise;
import edu.ib.telerehabilitation.model.Frequency;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private SpecialistService specialistService;
    private PatientService patientService;
    private ExerciseService exerciseService;
    Specialist currentSpecialist;
    Patient currentPatient;
    Long idClicked;

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
            if (patientService.checkIfUserExists(email, password, role.toString())) {
                return getDataProfilePatient(email, model);
            }
        } else {
            if (specialistService.checkIfUserExists(email, password, role.toString())) {
                return getDataProfileSpecialist(email, model, new Patient());
            }
        }
        redirAttrs.addFlashAttribute("error", "Try again! You provided wrong e-mail, password or role.");
        return "redirect:/";
    }


    @RequestMapping("/findPatient")
    public String findAndAddPatient(@RequestParam(value = "email") String email, Model model) {
        Patient patient = patientService.findByEmail(email);
        if (patient != null && patient.getSpecialist() == null) {
            patientService.updateSpecialist(patient.getId(), currentSpecialist);
            model.addAttribute("success", "You have successfully added a new patient.");
        } else {
            model.addAttribute("error", "There's no such patient as " + email + ". Make sure you have a valid e-mail or the patient has no other doctor.");
        }
        return getDataProfileSpecialist(currentSpecialist.getEmail(), model, new Patient());
    }


    @RequestMapping("/{id}")
    public String showPatient(@PathVariable Long id, Model model) {
        idClicked = id;
        Patient patient = getPatientIfExists(idClicked);
        return getDataProfileSpecialist(currentSpecialist.getEmail(), model, patient);
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
        return getDataProfileSpecialist(currentSpecialist.getEmail(), model, new Patient());
    }


    @RequestMapping("/profilePatient")
    public String seeProfilePatient(Model model) {
        return getDataProfilePatient(currentPatient.getEmail(), model);
    }

    @RequestMapping("/addNewExercise")
    public String addExercise(@RequestParam(value = "exerciseChosen") String newExercise, Model model) {
        Exercise exercise = exerciseService.findByName(newExercise);
        patientService.updateExercises(idClicked, exercise);
        Patient patient = getPatientIfExists(idClicked);
        return getDataProfileSpecialist(currentSpecialist.getEmail(), model, patient);
    }


    @RequestMapping("/deleteExercise/{id}")
    public String deleteExercise(@PathVariable Long id, Model model) {

        Optional<Exercise> exerciseOptional = exerciseService.findById(id);
        Exercise exercise = null;
        if (exerciseOptional.isPresent()) {
            exercise = exerciseOptional.get();

        } else {
            System.out.println("No exercise with that id");
        }

        patientService.updateExercisesDelete(idClicked, exercise);
        Patient patient = getPatientIfExists(idClicked);
        return getDataProfileSpecialist(currentSpecialist.getEmail(), model, patient);
    }


    @RequestMapping("/updateFrequency")
    public String changeFreq(@RequestParam(value = "frequencyChosen") Frequency newFrequency, Model model) {
        patientService.updateFrequency(idClicked, newFrequency);
        Patient patient = getPatientIfExists(idClicked);
        return getDataProfileSpecialist(currentSpecialist.getEmail(), model, patient);
    }

    @RequestMapping("/call")
    public String goToWebRTC(Model model) {
        return "rtc";
    }


    @RequestMapping("/trainingDone")
    public String trainingDoneAddDate(Model model) {
        patientService.updateTrainingDates(currentPatient.getId(), LocalDate.now());
        model.addAttribute("success", "You successfully informed about the training done.");
        return getDataProfilePatient(currentPatient.getEmail(), model);
    }

    @RequestMapping("/opinion")
    public String updateOpinion(@RequestParam(value = "opinion") String opinion, Model model) {
        patientService.updateResultsDescription(idClicked, opinion);
        Patient patient = getPatientIfExists(idClicked);
        return getDataProfileSpecialist(currentSpecialist.getEmail(), model, patient);
    }


    public String getDataProfilePatient(String email, Model model) {
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


    public String getDataProfileSpecialist(String email, Model model, Patient patientClicked) {
        currentSpecialist = specialistService.findByEmail(email);
        List<Patient> patients = new ArrayList<>();
        for (Patient p : patientService.findAll()) {
            if (p.getSpecialist() == currentSpecialist) {
                patients.add(p);
            }
        }

        List<Frequency> frequenciesAll = new ArrayList<>(Arrays.asList(Frequency.values()));

        List<Exercise> exercisesAll = new ArrayList<>();
        List<Exercise> exercises = new ArrayList<>();
        if (patientClicked != null && patientClicked.getExercises() != null) {
            exercisesAll = (List<Exercise>) exerciseService.findAll();
            patientClicked = patientService.findByEmail(patientClicked.getEmail());
            for (Exercise e : exerciseService.findAll()) {
                if (patientClicked.getExercises().contains(e)) {
                    exercises.add(e);
                    exercisesAll.remove(e);
                }
            }
        }

        model.addAttribute("frequencyList", frequenciesAll);
        model.addAttribute("exerciseList", exercisesAll);
        model.addAttribute("exercises", exercises);
        model.addAttribute("patients", patients);
        model.addAttribute("patient", patientClicked);
        model.addAttribute("currentSpecialist", currentSpecialist);
        return "profileSpecialist";
    }


    public Patient getPatientIfExists(Long id) {
        Optional<Patient> patientOptional = patientService.findById(id);
        Patient patient = null;
        if (patientOptional.isPresent()) {
            patient = patientOptional.get();
        } else {
            System.out.println("No patient with that id");
        }
        return patient;
    }
}
