package Smart_Hospital_Management_System;

public class LabTestService extends PatientDecorator {

    public LabTestService(Patient patient) {
        super(patient);
    }

    public String getType() {
        return patient.getType() + " + Lab Tests";
    }

    public int getCost() {
        return patient.getCost() + 1500;
    }
}
