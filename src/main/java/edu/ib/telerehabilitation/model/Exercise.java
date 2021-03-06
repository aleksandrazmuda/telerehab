package edu.ib.telerehabilitation.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String visualRepresentation;

    public Exercise(String name, String visualRepresentation) {
        this.name = name;
        this.visualRepresentation = visualRepresentation;
    }

    public Exercise() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVisualRepresentation() {
        return visualRepresentation;
    }

    public void setVisualRepresentation(String visualRepresentation) {
        this.visualRepresentation = visualRepresentation;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", visualRepresentation='" + visualRepresentation + '\'' +
                '}';
    }
}
