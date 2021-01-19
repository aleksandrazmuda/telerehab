package edu.ib.telerehabilitation.service;

import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    Boolean addUser(UserDTO userDTO);
    User getCurrentUser(Authentication authentication);
    User findByUsername(String username);
}
