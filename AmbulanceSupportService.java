package Smart_Hospital_Management_System;

public class AmbulanceSupportService extends PatientDecorator {

    public AmbulanceSupportService(Patient patient) {
        super(patient);
    }

    public String getType() {
        return patient.getType() + " + Ambulance Support";
    }

    public int getCost() {
        return patient.getCost() + 1200;
    }
}