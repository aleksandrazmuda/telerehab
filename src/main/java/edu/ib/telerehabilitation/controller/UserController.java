package edu.ib.telerehabilitation.controller;

import edu.ib.telerehabilitation.datatransferobject.PatientDTO;
import edu.ib.telerehabilitation.datatransferobject.Role;
import edu.ib.telerehabilitation.datatransferobject.SpecialistDTO;
import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.*;
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
    UserDTO currentSpecialist;
    UserDTO currentPatient;
    UserDTO patientClicked;

    @Autowired
    public UserController(SpecialistService specialistService, PatientService patientService, ExerciseService exerciseService) {
        this.specialistService = specialistService;
        this.patientService = patientService;
        this.exerciseService = exerciseService;
    }


    @RequestMapping("/log")
    public String loginUser(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password,
                            @RequestParam(value = "role") Role role, Model model, RedirectAttributes attributes) {

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setPassword(password);
        if (role == Role.PATIENT) {
            if (patientService.checkIfUserExists(userDTO)) {
                currentPatient = userDTO;
                return getDataProfilePatient(currentPatient, model);
            }
        } else {
            if (specialistService.checkIfUserExists(userDTO)) {
                currentSpecialist = userDTO;
                return getDataProfileSpecialist(currentSpecialist, model, patientClicked);
            }
        }

        attributes.addFlashAttribute("error", "Try again! You provided wrong e-mail, password or role.");
        return "redirect:/";
    }

    @RequestMapping("/findPatient")
    public String findAndAddPatient(@RequestParam(value = "email") String email, Model model) {
        UserDTO patientDTO = new UserDTO();
        patientDTO.setEmail(email);
        if (patientService.updateSpecialist(patientDTO, currentSpecialist)) {
            model.addAttribute("success", "You have successfully added a new patient.");
        } else {
            model.addAttribute("error", "There's no such patient as " + email + ". Make sure you have a valid e-mail or the patient has no other doctor.");
        }
        return getDataProfileSpecialist(currentSpecialist, model, patientClicked);
    }


    @RequestMapping("/{email}")
    public String showPatient(@PathVariable String email, Model model) {
        UserDTO patient = new UserDTO();
        patient.setEmail(email);
        if (patientService.checkIfUserExistsByEmail(patient)) {
            patientClicked = patient;
            return getDataProfileSpecialist(currentSpecialist, model, patientClicked);
        } else
            return "error";
    }

    @RequestMapping("/updateFrequency")
    public String changeFreq(@RequestParam(value = "frequencyChosen") Frequency newFrequency, Model model) {
        if (patientService.updateFrequency(patientClicked, newFrequency)) {
            return getDataProfileSpecialist(currentSpecialist, model, patientClicked);
        }
        return "error";
    }

    @RequestMapping("/opinion")
    public String updateOpinion(@RequestParam(value = "opinion") String opinion, Model model) {
        if (patientService.updateResultsDescription(patientClicked, opinion)) {
            return getDataProfileSpecialist(currentSpecialist, model, patientClicked);
        }
        return "error";
    }

    @RequestMapping("/addNewExercise")
    public String addExercise(@RequestParam(value = "exerciseChosen") String newExercise, Model model) {
        if (exerciseService.updateExercises(patientClicked, newExercise)) {
            return getDataProfileSpecialist(currentSpecialist, model, patientClicked);
        }
        return "error";
    }

    @RequestMapping("/deleteExercise/{name}")
    public String deleteExercise(@PathVariable String name, Model model) {
        if (exerciseService.updateExercisesDelete(patientClicked, name)) {
            return getDataProfileSpecialist(currentSpecialist, model, patientClicked);
        }
        return "error";
    }

    @RequestMapping("/aboutPatient")
    public String seeAboutPatient(Model model) {
        if (patientService.checkIfUserExistsByEmail(currentPatient)) {
            PatientDTO patientDTO = patientService.getPatientIfExists(currentPatient);
            model.addAttribute("currentPatient", patientDTO);
            return "aboutPatient";
        }
        return "error";
    }


    @RequestMapping("/aboutSpecialist")
    public String seeAboutSpecialist(Model model) {
        if (specialistService.checkIfUserExistsByEmail(currentSpecialist)) {
            SpecialistDTO specialistDTO = specialistService.getSpecialistIfExists(currentSpecialist);
            model.addAttribute("currentSpecialist", specialistDTO);
            return "aboutSpecialist";
        }
        return "error";
    }

    @RequestMapping("/profileSpecialist")
    public String seeProfileSpecialist(Model model) {
        return getDataProfileSpecialist(currentSpecialist, model, patientClicked);
    }

    @RequestMapping("/profilePatient")
    public String seeProfilePatient(Model model) {
        return getDataProfilePatient(currentPatient, model);
    }


    @RequestMapping("/call")
    public String goToWebRTC(Model model) {
        return "rtc";
    }

    @RequestMapping("/trainingDone")
    public String trainingDoneAddDate(Model model) {
        if (patientService.updateTrainingDates(currentPatient, LocalDate.now())) {
            model.addAttribute("success", "You successfully informed about the training done.");
            return getDataProfilePatient(currentPatient, model);
        }
        return "error";
    }


    public String getDataProfilePatient(UserDTO userDTO, Model model) {
        if (patientService.checkIfUserExistsByEmail(currentPatient)) {
            PatientDTO patientDTO = patientService.getPatientIfExists(userDTO);
            List<Exercise> exercises = exerciseService.findExercisesOfPatient(userDTO);
            model.addAttribute("exercises", exercises);
            model.addAttribute("currentPatient", patientDTO);
            return "profilePatient";
        }
        return "error";
    }

    public String getDataProfileSpecialist(UserDTO userDTOSpecialist, Model model, UserDTO patientClicked) {
        SpecialistDTO specialist = specialistService.getSpecialistIfExists(userDTOSpecialist);
        List<Patient> patients = specialistService.findPatientsOfSpecialist(userDTOSpecialist);
        List<Exercise> exercisesAll = new ArrayList<>();
        List<Exercise> exercises = new ArrayList<>();
        PatientDTO patient = new PatientDTO();

        if (patientClicked != null) {
            patient = patientService.getPatientIfExists(patientClicked);
            exercisesAll = exerciseService.getExercisesAll(patientClicked);
            exercises = exerciseService.findExercisesOfPatient(patientClicked);
        }

        List<Frequency> frequenciesAll = new ArrayList<>(Arrays.asList(Frequency.values()));

        model.addAttribute("frequencyList", frequenciesAll);
        model.addAttribute("exerciseList", exercisesAll);
        model.addAttribute("exercises", exercises);
        model.addAttribute("patients", patients);
        model.addAttribute("patient", patient);
        model.addAttribute("currentSpecialist", specialist);
        return "profileSpecialist";
    }

}
