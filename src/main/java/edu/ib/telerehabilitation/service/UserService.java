package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.datatransferobject.Role;
import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.Patient;
import edu.ib.telerehabilitation.model.Specialist;

public interface UserService {

    void addUser(UserDTO userDTO);

    Boolean checkIfUserExists(String email, String password, String role);
}
