package edu.ib.telerehabilitation.datatransferobject;

import java.util.List;

public class SpecialistDTO extends UserDTO{

    private List<PatientDTO> patientDTOList;

    public SpecialistDTO(List<PatientDTO> patientDTOList) {
        this.patientDTOList = patientDTOList;
    }

    public SpecialistDTO(String email, String userName, String name, String surname, String phoneNumber, String password, String passwordConfirm, String role, List<PatientDTO> patientDTOList) {
        super(email, userName, name, surname, phoneNumber, password, passwordConfirm, role);
        this.patientDTOList = patientDTOList;
    }

    public SpecialistDTO() {

    }

    public List<PatientDTO> getPatientDTOList() {
        return patientDTOList;
    }

    public void setPatientDTOList(List<PatientDTO> patientDTOList) {
        this.patientDTOList = patientDTOList;
    }
}
