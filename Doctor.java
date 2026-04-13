package Smart_Hospital_Management_System;

public class Doctor extends Handler {

    public void handle(String request) {
        if (!isProcessRequest(request)) {
            System.out.println("Doctor: Unknown request ignored");
            return;
        }

        System.out.println("Doctor: Consultation and diagnosis completed");
        if (next != null) {
            next.handle(request);
        }
    }
}
