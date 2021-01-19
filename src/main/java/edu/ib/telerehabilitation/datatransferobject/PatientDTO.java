package edu.ib.telerehabilitation.datatransferobject;

import edu.ib.telerehabilitation.model.Frequency;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class PatientDTO extends UserDTO{

    private List<ExerciseDTO> exercises;
    private Frequency frequency;
    private List<LocalDate> trainingDates = new ArrayList<LocalDate>();
    private String resultsDescription;

    public PatientDTO(List<ExerciseDTO> exercises, Frequency frequency, List<LocalDate> trainingDates, String resultsDescription) {
        this.exercises = exercises;
        this.frequency = frequency;
        this.trainingDates = trainingDates;
        this.resultsDescription = resultsDescription;
    }

    public PatientDTO(String email, String userName, String name, String surname, String phoneNumber, String password, String passwordConfirm, String role, List<ExerciseDTO> exercises, Frequency frequency, List<LocalDate> trainingDates, String resultsDescription) {
        super(email, userName, name, surname, phoneNumber, password, passwordConfirm, role);
        this.exercises = exercises;
        this.frequency = frequency;
        this.trainingDates = trainingDates;
        this.resultsDescription = resultsDescription;
    }

    public PatientDTO() {
    }

    public List<ExerciseDTO> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseDTO> exercises) {
        this.exercises = exercises;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public List<LocalDate> getTrainingDates() {
        return trainingDates;
    }

    public void setTrainingDates(List<LocalDate> trainingDates) {
        this.trainingDates = trainingDates;
    }

    public String getResultsDescription() {
        return resultsDescription;
    }

    public void setResultsDescription(String resultsDescription) {
        this.resultsDescription = resultsDescription;
    }
}
