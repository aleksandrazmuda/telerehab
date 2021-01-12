package edu.ib.telerehabilitation.securityServices;

import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.model.Specialist;
import edu.ib.telerehabilitation.persistance.PatientRepo;
import edu.ib.telerehabilitation.persistance.SpecialistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private PatientRepo patientRepo;
    private SpecialistRepo specialistRepo;


    public UserDetailsServiceImpl(PatientRepo patientRepo, SpecialistRepo specialistRepo) {
        this.patientRepo = patientRepo;
        this.specialistRepo = specialistRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Patient patient = patientRepo.findByUserName(username);
        Specialist specialist = specialistRepo.findByUserName(username);
        if (patient == null && specialist == null) {
            throw new UsernameNotFoundException(username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(Objects.requireNonNullElse(patient, specialist).getRole()));

        return new org.springframework.security.core.userdetails.User(Objects.requireNonNullElse(patient, specialist).getUserName(),
                Objects.requireNonNullElse(patient, specialist).getPassword(), grantedAuthorities);
    }
}
