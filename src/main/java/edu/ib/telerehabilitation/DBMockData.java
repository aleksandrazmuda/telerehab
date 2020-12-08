package edu.ib.telerehabilitation;

import edu.ib.telerehabilitation.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DBMockData {

    private PatientRepo patientRepo;
    private SpecialistRepo specialistRepo;

    @Autowired
    public DBMockData(PatientRepo patientRepo, SpecialistRepo specialistRepo) {
        this.patientRepo = patientRepo;
        this.specialistRepo = specialistRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void dbOperations() {
        if (patientRepo.findAll().iterator().hasNext()) {
            System.out.println("Database has already filled");
            return;
        }

        Specialist specialist1 = new Specialist("doktor1@gmail.com", "doc1",
                "Andrzej", "Nowak", "123456789");
        specialistRepo.save(specialist1);

        Patient patient1 = new Patient(specialist1, "aleksandrazmuda1998@gmail.com", "maleficent314",
                "Aleksandra", "Zmuda", "69200784",
                Frequency.EVERY_DAY, List.of(LocalDate.now(), LocalDate.now().minusDays(1)), "Cool results, you know");
         patientRepo.save(patient1);

    }
}
