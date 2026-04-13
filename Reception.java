package Smart_Hospital_Management_System;

public class Reception extends Handler {

    public void handle(String request) {
        if (!isProcessRequest(request)) {
            System.out.println("Reception: Unknown request ignored");
            return;
        }

        System.out.println("Reception: Patient registration completed");
        if (next != null) {
            next.handle(request);
        }
    }
}
