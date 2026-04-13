package Smart_Hospital_Management_System;

public class BillingDesk extends Handler {

    public void handle(String request) {
        if (!isProcessRequest(request)) {
            System.out.println("Billing Desk: Unknown request ignored");
            return;
        }

        System.out.println("Billing Desk: Bill generated and payment recorded");
        if (next != null) {
            next.handle(request);
        }
    }
}