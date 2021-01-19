package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.datatransferobject.*;
import edu.ib.telerehabilitation.model.*;
import edu.ib.telerehabilitation.persistance.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SpecialistProfileService {

    private PatientRepo patientRepo;
    private UserService userService;
    private SupportService supportService;

    @Autowired
    public SpecialistProfileService(PatientRepo patientRepo, UserService userService, SupportService supportService) {
        this.patientRepo = patientRepo;
        this.userService = userService;
        this.supportService = supportService;
    }


    // PU Dodaj pacjenta
    public Boolean addPatientToTheCollectionOfSpecialist(SpecialistDTO specialistDTO, String patientUsername, Authentication authentication) {
        Specialist userSpecialist = (Specialist) userService.getCurrentUser(authentication); //PU Wyszukaj specjalistę
        List<PatientDTO> patients = supportService.findPatientsOfSpecialist(userSpecialist);   //PU Wyszukaj pacjentów ze zbioru specjalisty
        Patient patient = supportService.findPatientByUserName(patientUsername); //PU Wyszukaj pacjenta
        specialistDTO.setName(userSpecialist.getName());

        if (patient != null && !supportService.checkIfPatientIsInCollection(patients, patient)) {    //metoda pomocnicza
            patient.setSpecialist(userSpecialist);
            patientRepo.save(patient);
            specialistDTO.setPatientDTOList(patients);
            return true;
        }

        specialistDTO.setPatientDTOList(patients);
        return false;
    }


    // PU Sprawdź dane dotyczące pacjenta
    public void getDataOfPatient(SupportProfileDTO supportProfileDTO, PatientDTO patientDTO,
                                 Authentication authentication, String username) {
        //SupportProfileDTO supportProfileDTO = new SupportProfileDTO();
        Patient patientClicked = supportService.getPatientIfIsInCollection(authentication, username);   //PU Operacje na specjalistach i ich pacjentach
        getDataAboutPatient(patientClicked, patientDTO, supportProfileDTO);
    }


    //PU zmień częstotliwość
    public void updateFrequency(PatientDTO patientDTO, SupportProfileDTO supportProfileDTO,
                                String username, Frequency frequency, Authentication authentication) {

        Patient patient = supportService.getPatientIfIsInCollection(authentication, username);  //PU Operacje na specjalistach i ich pacjentach
        if (patient != null) {
            patient.setFrequency(frequency);
            patientRepo.save(patient);
        }
        getDataAboutPatient(patient, patientDTO, supportProfileDTO);
    }


    // PU Opisz wyniki rehabilitacji pacjenta
    public void updateResultsDescription(PatientDTO patientDTO, SupportProfileDTO supportProfileDTO,
                                         String username, String opinion, Authentication authentication) {
        Patient patient = supportService.getPatientIfIsInCollection(authentication, username);  //PU Operacje na specjalistach i ich pacjentach
        if (patient != null) {
            patient.setResultsDescription(opinion);
            patientRepo.save(patient);
        }
        getDataAboutPatient(patient, patientDTO, supportProfileDTO);
    }


    // PU usuń cwiczenie z planu pacjenta
    public void deleteExercise(PatientDTO patientDTO, SupportProfileDTO supportProfileDTO,
                               String username, String exerciseName, Authentication authentication) {

        Exercise exercise = supportService.findExerciseByName(exerciseName);  //PU Wyszukaj ćwiczenie
        Patient patient = supportService.getPatientIfIsInCollection(authentication, username);  //PU Operacje na specjalistach i ich pacjentach

        if (patient != null) {
            patient.getExercises().remove(exercise);
            patientRepo.save(patient);
        }
        getDataAboutPatient(patient, patientDTO, supportProfileDTO);

    }


    // PU Dodaj ćwiczenie do planu pacjenta
    public void addExercise(PatientDTO patientDTO, SupportProfileDTO supportProfileDTO,
                            String username, String exerciseName, Authentication authentication) {
        Exercise exercise = supportService.findExerciseByName(exerciseName);  //PU Wyszukaj ćwiczenie
        Patient patient = supportService.getPatientIfIsInCollection(authentication, username);  //PU Operacje na specjalistach i ich pacjentach
        if (patient != null) {
            patient.getExercises().add(exercise);
            patientRepo.save(patient);
        }
        getDataAboutPatient(patient, patientDTO, supportProfileDTO);
    }


    //pomocnicza
    public void getDataAboutPatient(Patient patient, PatientDTO patientDTO, SupportProfileDTO supportProfileDTO) {
        List<ExerciseDTO> exercisesAll = supportService.getExercisesAll(patient);
        List<ExerciseDTO> exercises = supportService.findExercisesOfPatient(patient);
        List<Frequency> frequenciesAll = new ArrayList<>(Arrays.asList(Frequency.values()));

        patientDTO.setName(patient.getName());
        patientDTO.setSurname(patient.getSurname());
        patientDTO.setUserName(patient.getUserName());
        patientDTO.setPhoneNumber(patient.getPhoneNumber());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setTrainingDates(patient.getTrainingDates());
        patientDTO.setFrequency(patient.getFrequency());
        patientDTO.setResultsDescription(patient.getResultsDescription());

        supportProfileDTO.setExercises(exercises);
        supportProfileDTO.setExercisesAll(exercisesAll);
        supportProfileDTO.setFrequencies(frequenciesAll);
    }


    // pomocnicza
    public void getDataToProfileSpecialist(SpecialistDTO specialistDTO, Authentication authentication) {
        Specialist userSpecialist = (Specialist) userService.getCurrentUser(authentication);
        List<PatientDTO> patients = supportService.findPatientsOfSpecialist(userSpecialist);
        specialistDTO.setName(userSpecialist.getName());
        specialistDTO.setPatientDTOList(patients);
    }


    // dodatkowa
    public SpecialistDTO getDataToAboutSpecialist(Authentication authentication) {
        Specialist userSpecialist = (Specialist) userService.getCurrentUser(authentication);
        SpecialistDTO specialistDTO = new SpecialistDTO();
        specialistDTO.setName(userSpecialist.getName());
        specialistDTO.setSurname(userSpecialist.getSurname());
        specialistDTO.setPhoneNumber(userSpecialist.getPhoneNumber());
        specialistDTO.setEmail(userSpecialist.getEmail());
        specialistDTO.setUserName(userSpecialist.getUserName());
        return specialistDTO;
    }


}
