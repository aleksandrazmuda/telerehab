package edu.ib.telerehabilitation.dao;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinTable(name = "patient_specialist", joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "specialist_id"))
    private Specialist specialist;

    private String email;
    private String userName;
    private String name;
    private String surname;
    private String phoneNumber;
    private Frequency frequency;
    @ElementCollection
    private List<LocalDate> trainingDates = new ArrayList<LocalDate>();
    private String resultsDescription;

    public Patient(Specialist specialist, String email, String userName, String name, String surname, String phoneNumber, Frequency frequency, List<LocalDate> trainingDates, String resultsDescription) {
        this.specialist = specialist;
        this.email = email;
        this.userName = userName;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", frequency=" + frequency +
                ", trainingDates=" + trainingDates +
                ", resultsDescription='" + resultsDescription + '\'' +
                '}';
    }
}
