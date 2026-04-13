package Smart_Hospital_Management_System;

public final class PatientFactory {

    private PatientFactory() {
    }

    public static Patient getPatient(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient type cannot be empty.");
        }

        if (type.equalsIgnoreCase("regular")) {
            return new RegularPatient();
        } else if (type.equalsIgnoreCase("emergency")) {
            return new EmergencyPatient();
        }

        throw new IllegalArgumentException("Unsupported patient type: " + type);
    }
}