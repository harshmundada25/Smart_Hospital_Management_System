package Smart_Hospital_Management_System;

public class RegularRoomService extends PatientDecorator {

    public RegularRoomService(Patient patient) {
        super(patient);
    }

    public String getType() {
        return patient.getType() + " + Regular Room Service";
    }

    public int getCost() {
        return patient.getCost() + 900;
    }
}