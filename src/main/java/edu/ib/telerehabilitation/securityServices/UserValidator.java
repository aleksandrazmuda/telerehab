package edu.ib.telerehabilitation.securityServices;

import edu.ib.telerehabilitation.datatransferobject.UserDTO;
import edu.ib.telerehabilitation.model.User;
import edu.ib.telerehabilitation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDTO userDTO = (UserDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty");
        if (userDTO.getUserName().length() < 6 || userDTO.getUserName().length() > 32) {
            errors.rejectValue("userName", "Size.userForm.userName");
        }

        if (userService.findByUsername(userDTO.getUserName()) != null) {
            errors.rejectValue("userName", "Duplicate.userForm.userName");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (userDTO.getPassword().length() < 8 || userDTO.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!userDTO.getPasswordConfirm().equals(userDTO.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
    
}
