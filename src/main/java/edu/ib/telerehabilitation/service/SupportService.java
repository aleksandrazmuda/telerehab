package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.datatransferobject.ExerciseDTO;
import edu.ib.telerehabilitation.datatransferobject.PatientDTO;
import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.Exercise;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.model.Specialist;
import edu.ib.telerehabilitation.model.User;
import edu.ib.telerehabilitation.persistance.ExerciseRepo;
import edu.ib.telerehabilitation.persistance.PatientRepo;
import edu.ib.telerehabilitation.persistance.SpecialistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SupportService {

    private PatientRepo patientRepo;
    private ExerciseRepo exerciseRepo;
    private UserService userService;

    @Autowired
    public SupportService(PatientRepo patientRepo, ExerciseRepo exerciseRepo, UserService userService) {
        this.patientRepo = patientRepo;
        this.exerciseRepo = exerciseRepo;
        this.userService = userService;
    }

    // PU Wyszukaj pacjentów w zbiorze specjalisty
    public List<PatientDTO> findPatientsOfSpecialist(User userSpecialist) {
        List<PatientDTO> patientDTOList = new ArrayList<>();
        for (Patient p : patientRepo.findBySpecialist((Specialist) userSpecialist)) {
            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setName(p.getName());
            patientDTO.setSurname(p.getSurname());
            patientDTO.setUserName(p.getUserName());
            patientDTOList.add(patientDTO);
        }
        return patientDTOList;
    }


    // Metoda pomocnicza
    public Boolean checkIfPatientIsInCollection(List<PatientDTO> patients, Patient patient) {
        for (PatientDTO p : patients) {
            if (patient.getUserName().equals(p.getUserName())) {
                return true;
            }
        }
        return false;
    }


    // PU Wyszukaj ćwiczenia ze zbioru pacjenta
    public List<ExerciseDTO> findExercisesOfPatient(Patient patient) {
        List<ExerciseDTO> exerciseDTOList = new ArrayList<>();
        for (Exercise e : patient.getExercises()) {
            ExerciseDTO exerciseDTO = new ExerciseDTO(e.getName(), e.getVisualRepresentation());
            exerciseDTOList.add(exerciseDTO);
        }
        return exerciseDTOList;
    }


    // Metoda pomocnicza
    public List<ExerciseDTO> getExercisesAll(Patient patient) {
        List<ExerciseDTO> exercisesAll = new ArrayList<>();
        for (Exercise e : exerciseRepo.findAll()) {
            if (!patient.getExercises().contains(e)) {
                ExerciseDTO exerciseDTO = new ExerciseDTO(e.getName(), e.getVisualRepresentation());
                exercisesAll.add(exerciseDTO);
            }
        }
        return exercisesAll;
    }


    //    PU Operacje na specjalistach i ich pacjentach
    public Patient getPatientIfIsInCollection(Authentication authentication, String username) {
        Specialist specialist = (Specialist) userService.getCurrentUser(authentication); //PU Wyszukaj specjalistę
        Patient patient = new Patient();

        if (specialist != null)
            patient = findPatientByUserName(username); //PU Wyszukaj pacjenta
        if (patient != null && checkIfPatientIsInCollection(findPatientsOfSpecialist(specialist), patient))    //PU Wyszukaj pacjentów w zbiorze specjalisty
            return patient;
        return null;
    }


    // PU Wyszukaj pacjenta
    public Patient findPatientByUserName(String username) {
        return patientRepo.findByUserName(username);
    }


    // PU Wyszukaj ćwiczenie
    public Exercise findExerciseByName(String exerciseName) {
        return exerciseRepo.findByName(exerciseName);
    }

}
