package edu.ib.telerehabilitation.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Patient extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinTable(name = "patient_specialist", joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "specialist_id"))
    private Specialist specialist;
    @ManyToMany
    @JoinTable(name = "patient_exercise", joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id"))
    private Set<Exercise> exercises;
    private Frequency frequency;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<LocalDate> trainingDates = new ArrayList<LocalDate>();
    private String resultsDescription;

    public Patient(Specialist specialist, Set<Exercise> exercises, String email, String userName, String name, String surname,
                   String phoneNumber, String password, Frequency frequency, List<LocalDate> trainingDates, String resultsDescription) {
        super(email, userName, name, surname, phoneNumber, password);
        this.specialist = specialist;
        this.exercises = exercises;
        this.frequency = frequency;
        this.trainingDates = trainingDates;
        this.resultsDescription = resultsDescription;
    }

    public Patient() {
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", specialist=" + specialist +
                ", exercises=" + exercises +
                super.toString() +
                ", frequency=" + frequency +
                ", trainingDates=" + trainingDates +
                ", resultsDescription='" + resultsDescription + '\'' +
                '}';
    }

}
