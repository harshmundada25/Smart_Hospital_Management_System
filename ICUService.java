package Smart_Hospital_Management_System;

public class ICUService extends PatientDecorator {

    public ICUService(Patient patient) {
        super(patient);
    }

    public String getType() {
        return patient.getType() + " + ICU Service";
    }

    public int getCost() {
        return patient.getCost() + 3000;
    }
}