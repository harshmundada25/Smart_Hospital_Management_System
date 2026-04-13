package Smart_Hospital_Management_System;

public class RegularPatient implements Patient {

    public String getType() {
        return "Regular Patient";
    }

    public int getCost() {
        return 500;
    }
}
