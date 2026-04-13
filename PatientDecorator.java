package Smart_Hospital_Management_System;

public abstract class PatientDecorator implements Patient {
    protected Patient patient;

    public PatientDecorator(Patient patient) {
        this.patient = patient;
    }

    public String getType() {
        return patient.getType();
    }

    public int getCost() {
        return patient.getCost();
    }
}
