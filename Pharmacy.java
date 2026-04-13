package Smart_Hospital_Management_System;

public class Pharmacy extends Handler {

    public void handle(String request) {
        if (!isProcessRequest(request)) {
            System.out.println("Pharmacy: Unknown request ignored");
            return;
        }

        System.out.println("Pharmacy: Medicines and discharge guidance provided");
        if (next != null) {
            next.handle(request);
        }
    }
}
