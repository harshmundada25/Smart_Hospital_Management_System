package Smart_Hospital_Management_System;

public class InsuranceService extends PatientDecorator {

    public InsuranceService(Patient patient) {
        super(patient);
    }

    public String getType() {
        return patient.getType() + " + Insurance Assistance";
    }

    public int getCost() {
        return patient.getCost() + 700;
    }
}