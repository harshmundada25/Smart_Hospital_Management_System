package Smart_Hospital_Management_System;

public class PrivateRoomService extends PatientDecorator {

    public PrivateRoomService(Patient patient) {
        super(patient);
    }

    public String getType() {
        return patient.getType() + " + Private Room Service";
    }

    public int getCost() {
        return patient.getCost() + 2200;
    }
}