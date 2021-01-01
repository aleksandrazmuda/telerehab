package edu.ib.telerehabilitation.datatransferobject;

public class SpecialistDTO extends UserDTO{

    public SpecialistDTO() {
    }

    public SpecialistDTO(String email, String userName, String name, String surname, String phoneNumber, String password, Role role) {
        super(email, userName, name, surname, phoneNumber, password, role);
    }


}
