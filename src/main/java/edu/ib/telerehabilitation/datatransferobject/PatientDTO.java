package edu.ib.telerehabilitation.datatransferobject;

import edu.ib.telerehabilitation.model.Exercise;
import edu.ib.telerehabilitation.model.Frequency;
import edu.ib.telerehabilitation.model.Specialist;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PatientDTO extends UserDTO{

    private Frequency frequency;
    private List<LocalDate> trainingDates = new ArrayList<LocalDate>();
    private String resultsDescription;
    private Set<Exercise> exercises;
    private Specialist specialist;

    public PatientDTO(String email, String userName, String name, String surname, String phoneNumber, String password, Role role, Frequency frequency, List<LocalDate> trainingDates, String resultsDescription, Set<Exercise> exercises, Specialist specialist) {
        super(email, userName, name, surname, phoneNumber, password, role);
        this.frequency = frequency;
        this.trainingDates = trainingDates;
        this.resultsDescription = resultsDescription;
        this.exercises = exercises;
        this.specialist = specialist;
    }

    public PatientDTO() {
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

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    @Override
    public String toString() {
        return "PatientDTO{" +
                "frequency=" + frequency +
                ", trainingDates=" + trainingDates +
                ", resultsDescription='" + resultsDescription + '\'' +
                ", exercises=" + exercises +
                ", specialist=" + specialist +
                '}';
    }
}
