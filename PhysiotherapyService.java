package Smart_Hospital_Management_System;

public class PhysiotherapyService extends PatientDecorator {

    public PhysiotherapyService(Patient patient) {
        super(patient);
    }

    public String getType() {
        return patient.getType() + " + Physiotherapy";
    }

    public int getCost() {
        return patient.getCost() + 900;
    }
}