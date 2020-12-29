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

//        Exercise exercise1 = new Exercise("Strengthen your Knee",
//                " ",
//                "/images/ex1.PNG");
//
//        Exercise exercise2 = new Exercise("Balance while Sitting", "", "/images/ex2.PNG");
//        Exercise exercise3 = new Exercise("Rolling onto your Side in Bed", "", "/images/ex3.PNG");
//        Exercise exercise4 = new Exercise("Strengthen your Ankle", "", "/images/ex4.PNG");
//        Exercise exercise5 = new Exercise("Moving from Lying to Sitting on the Side of the Bed", "", "/images/ex5.PNG");
//        Exercise exercise6 = new Exercise("Moving From Sitting on the Side of the Bed to Lying Down", "", "/images/ex6.PNG");
//        Exercise exercise7 = new Exercise("Exercise while Sitting 1", "", "/images/ex7.PNG");
//        Exercise exercise8 = new Exercise("Exercise while Sitting 2", "", "/images/ex8.PNG");
//        Exercise exercise9 = new Exercise("Stand Up from a Bed or Chair without help", "", "/images/ex9.PNG");
//        Exercise exercise10 = new Exercise("Stand Up from a Bed or Chair with help", "", "/images/ex10.PNG");
//
//        exerciseRepo.save(exercise1);
//        exerciseRepo.save(exercise2);
//        exerciseRepo.save(exercise3);
//        exerciseRepo.save(exercise4);
//        exerciseRepo.save(exercise5);
//        exerciseRepo.save(exercise6);
//        exerciseRepo.save(exercise7);
//        exerciseRepo.save(exercise8);
//        exerciseRepo.save(exercise9);
//        exerciseRepo.save(exercise10);

//
//        Set<Exercise> exerciseSet2 = new HashSet<>() {
//            {
//                add(exercise3);
//                add(exercise4);
//            }
//        };
//
//        Specialist specialist2 = new Specialist("doktor2@gmail.com","doktor1234","nameDok2","surnameDo2k","123123123","pass2");
//        specialistRepo.save(specialist2);
//
//
//
//
//        Patient patient2 = new Patient(specialist2, exerciseSet2, "ola@ola3.com", "user23", "name23",
//                "sur23", "456456456", "pass123", Frequency.EVERY_DAY,
//                List.of(LocalDate.now(), LocalDate.now().minusDays(1)), "Result are here." );
//
//        patientRepo.save(patient2);

//        if (patientRepo.findAll().iterator().hasNext()) {
//            System.out.println("Database has already filled");
//            return;
//        }

    }
}
