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

//        Exercise exercise3 = new Exercise("Strengthen your Knee",
//                "1. Put a rolled towel under you thigh so that your foot is off the floor. Straighten your knee and then slowly\n" +
//                        "allow it to bend. As you straighten\n",
//                "/images/ex1.png");
//
//        Exercise exercise4 = new Exercise("Balance while Sitting",
//                "1. Sit on the side of the bed\n" +
//                        "with your feet on the\n" ,
//                "/images/ex2.png");
//        exerciseRepo.save(exercise3);
//        exerciseRepo.save(exercise4);
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
// nalezy dodać plik sql ktory automatycznie wypelni przykladowe dane (szczegolnie cwiczenia!)



    }
}
