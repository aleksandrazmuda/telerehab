package edu.ib.telerehabilitation.model;

import javax.persistence.*;

@Entity
public class Specialist extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Specialist() {
    }

    public Specialist(String email, String userName, String name, String surname, String phoneNumber, String password, String passwordConfirm, String role) {
        super(email, userName, name, surname, phoneNumber, password, passwordConfirm, role);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Specialist{" +
                "id=" + id + super.toString() + '}';
    }
}
