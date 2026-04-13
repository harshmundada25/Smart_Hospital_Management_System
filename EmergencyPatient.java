package Smart_Hospital_Management_System;

public class EmergencyPatient implements Patient {

    public String getType() {
        return "Emergency Patient";
    }

    public int getCost() {
        return 1000;
    }
}
