package edu.ib.telerehabilitation;

import edu.ib.telerehabilitation.model.Exercise;
import edu.ib.telerehabilitation.model.Frequency;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.model.Specialist;
import edu.ib.telerehabilitation.persistance.ExerciseRepo;
import edu.ib.telerehabilitation.persistance.PatientRepo;
import edu.ib.telerehabilitation.persistance.SpecialistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DBMockData {

    private PatientRepo patientRepo;
    private SpecialistRepo specialistRepo;
    private ExerciseRepo exerciseRepo;

    @Autowired
    public DBMockData(PatientRepo patientRepo, SpecialistRepo specialistRepo, ExerciseRepo exerciseRepo) {
        this.exerciseRepo = exerciseRepo;
        this.patientRepo = patientRepo;
        this.specialistRepo = specialistRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void dbOperations() {
        if (patientRepo.findAll().iterator().hasNext()) {
            System.out.println("Database has already filled");
            return;
        }
// nalezy dodać plik sql ktory automatycznie wypelni przykladowe dane (szczegolnie cwiczenia!)
        
//        Specialist specialist1 = new Specialist("doktor1@gmail.com", "doc1",
//                "Andrzej", "Nowak", "123456789");
//        specialistRepo.save(specialist1);
//
//        Exercise exercise1 = new Exercise("Sitting trunk rotation",
//                "The person performing the exercise needs a chair etc etc",
//                "photo1");
//        Exercise exercise2 = new Exercise("Supported mini squads",
//                "The patient performs an incomplete squat supporting himself with.. ",
//                "photo2");
//        exerciseRepo.save(exercise1);
//        exerciseRepo.save(exercise2);
//
//        Set<Exercise> exerciseSet1 = new HashSet<>() {
//            {
//                add(exercise1);
//                add(exercise2);
//            }
//        };
//
//        Patient patient1 = new Patient(specialist1, exerciseSet1, "aleksandrazmuda1998@gmail.com", "maleficent314",
//                "Aleksandra", "Zmuda", "69200784",
//                Frequency.EVERY_DAY, List.of(LocalDate.now(), LocalDate.now().minusDays(1)), "Cool results, you know");
//        patientRepo.save(patient1);

    }
}
