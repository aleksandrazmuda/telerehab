package edu.ib.telerehabilitation.datatransferobject;

import edu.ib.telerehabilitation.model.Frequency;

import java.util.List;

public class SupportProfileDTO {

    private List<ExerciseDTO> exercisesAll;
    private List<Frequency> frequencies;
    private List<ExerciseDTO> exercises;

    public SupportProfileDTO(List<ExerciseDTO> exercisesAll, List<Frequency> frequencies, List<ExerciseDTO> exercises) {
        this.exercisesAll = exercisesAll;
        this.frequencies = frequencies;
        this.exercises = exercises;
    }

    public SupportProfileDTO() {

    }

    public List<ExerciseDTO> getExercisesAll() {
        return exercisesAll;
    }

    public void setExercisesAll(List<ExerciseDTO> exercisesAll) {
        this.exercisesAll = exercisesAll;
    }

    public List<Frequency> getFrequencies() {
        return frequencies;
    }

    public void setFrequencies(List<Frequency> frequencies) {
        this.frequencies = frequencies;
    }

    public List<ExerciseDTO> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseDTO> exercises) {
        this.exercises = exercises;
    }
}
