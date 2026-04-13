package Smart_Hospital_Management_System;

public abstract class Handler {
    protected Handler next;

    public void setNext(Handler next) {
        this.next = next;
    }

    protected boolean isProcessRequest(String request) {
        return "Process Patient".equalsIgnoreCase(request);
    }

    public abstract void handle(String request);
}
