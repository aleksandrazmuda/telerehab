package edu.ib.telerehabilitation.datatransferobject;

import edu.ib.telerehabilitation.model.Exercise;

public class ExerciseDTO {

    private String name;
    private String visualRepresentation;

    public ExerciseDTO(String name, String visualRepresentation) {
        this.name = name;
        this.visualRepresentation = visualRepresentation;
    }

    public ExerciseDTO(Exercise exercise) {
        this.name = exercise.getName();
        this.visualRepresentation = exercise.getVisualRepresentation();
    }

    public ExerciseDTO() {
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
}
