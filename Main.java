package Smart_Hospital_Management_System;

import java.util.Scanner;

public class Main {

    private static final String PROCESS_REQUEST = "Process Patient";
    private static final int MIN_AGE = 1;
    private static final int MAX_AGE = 120;
    private static final double TAX_RATE = 0.05;
    private static final int MAX_SERVICES = 10;
    private static int tokenCounter = 1001;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printHeader();

        int totalPatients = 0;
        int overallRevenue = 0;
        boolean processAnother = true;

        while (processAnother) {

            String patientName = readPatientName(scanner);
            int patientAge     = readPatientAge(scanner);
            String patientType = readPatientType(scanner);

            Patient patient;
            try {
                patient = PatientFactory.getPatient(patientType);   
            } catch (IllegalArgumentException exception) {
                System.out.println("Error: " + exception.getMessage());
                scanner.close();
                return;
            }

            String baseType = patientType.equalsIgnoreCase("emergency") ? "Emergency" : "Regular";
            int baseCost    = patient.getCost();

            String[] serviceNames = new String[MAX_SERVICES];
            int[]    serviceCosts = new int[MAX_SERVICES];
            int      serviceCount = 0;

            if (askYesNo(scanner, "Add ICU Service")) {
                int previousCost = patient.getCost();
                patient = new ICUService(patient);                  
                int addedCost = patient.getCost() - previousCost;
                serviceCount = addServiceToBill(serviceNames, serviceCosts, serviceCount,
                        "ICU Service", addedCost);
            }

            if (askYesNo(scanner, "Add Lab Tests")) {
                int previousCost = patient.getCost();
                patient = new LabTestService(patient);              
                int addedCost = patient.getCost() - previousCost;
                serviceCount = addServiceToBill(serviceNames, serviceCosts, serviceCount,
                        "Lab Tests", addedCost);
            }

            if (askYesNo(scanner, "Add Insurance Assistance")) {
                int previousCost = patient.getCost();
                patient = new InsuranceService(patient);            
                int addedCost = patient.getCost() - previousCost;
                serviceCount = addServiceToBill(serviceNames, serviceCosts, serviceCount,
                        "Insurance Assistance", addedCost);
            }

            if (askYesNo(scanner, "Add Ambulance Support")) {
                int previousCost = patient.getCost();
                patient = new AmbulanceSupportService(patient);     
                int addedCost = patient.getCost() - previousCost;
                serviceCount = addServiceToBill(serviceNames, serviceCosts, serviceCount,
                        "Ambulance Support", addedCost);
            }

            String roomServiceType = readRoomServiceType(scanner);
            if (roomServiceType.equalsIgnoreCase("regular")) {
                int previousCost = patient.getCost();
                patient = new RegularRoomService(patient);          
                int addedCost = patient.getCost() - previousCost;
                serviceCount = addServiceToBill(serviceNames, serviceCosts, serviceCount,
                        "Regular Room Service", addedCost);
            } else if (roomServiceType.equalsIgnoreCase("private")) {
                int previousCost = patient.getCost();
                patient = new PrivateRoomService(patient);          
                int addedCost = patient.getCost() - previousCost;
                serviceCount = addServiceToBill(serviceNames, serviceCosts, serviceCount,
                        "Private Room Service", addedCost);
            }

            int tokenNumber = tokenCounter++;
            int subtotal    = patient.getCost();
            int taxAmount   = (int) Math.round(subtotal * TAX_RATE);
            int finalAmount = subtotal + taxAmount;

            printBill(tokenNumber, patientName, patientAge, baseType, baseCost,
                    serviceNames, serviceCosts, serviceCount, subtotal, taxAmount, finalAmount);

            runWorkflow();

            totalPatients++;
            overallRevenue += finalAmount;

            processAnother = askYesNo(scanner, "Process another patient");
            if (processAnother) {
                printDivider();
            }
        }

        printSessionSummary(totalPatients, overallRevenue);
        scanner.close();
    }

    private static void runWorkflow() {
        System.out.println();

        Handler reception   = new Reception();
        Handler doctor      = new Doctor();
        Handler pharmacy    = new Pharmacy();
        Handler billingDesk = new BillingDesk();

        reception.setNext(doctor);
        doctor.setNext(pharmacy);
        pharmacy.setNext(billingDesk);

        reception.handle(PROCESS_REQUEST);

        System.out.println("\nProcess completed successfully.");
    }

    private static String readPatientName(Scanner scanner) {
        while (true) {
            System.out.print("\nEnter patient name: ");
            String name = scanner.nextLine().trim();
            if (isValidName(name)) {
                return name;
            }
            System.out.println("Invalid input. Name should contain only letters and spaces.");
        }
    }

    private static int readPatientAge(Scanner scanner) {
        while (true) {
            System.out.print("Enter patient age: ");
            String input = scanner.nextLine().trim();
            try {
                int age = Integer.parseInt(input);
                if (age >= MIN_AGE && age <= MAX_AGE) {
                    return age;
                }
            } catch (NumberFormatException exception) {
            }
            System.out.println("Invalid age. Enter a valid age between " + MIN_AGE + " and " + MAX_AGE + ".");
        }
    }

    private static String readPatientType(Scanner scanner) {
        while (true) {
            System.out.print("\nEnter patient type (regular/emergency): ");
            String type = scanner.nextLine().trim();
            if (type.equalsIgnoreCase("regular") || type.equalsIgnoreCase("emergency")) {
                return type;
            }
            System.out.println("Invalid input. Please enter only 'regular' or 'emergency'.");
        }
    }

    private static boolean askYesNo(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + "? (y/n): ");
            String choice = scanner.nextLine().trim();
            if (choice.equalsIgnoreCase("y")) {
                return true;
            }
            if (choice.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Invalid choice. Please enter 'y' or 'n'.");
        }
    }

    private static String readRoomServiceType(Scanner scanner) {
        while (true) {
            System.out.print("Choose room service (regular/private/none): ");
            String roomType = scanner.nextLine().trim();
            if (roomType.equalsIgnoreCase("regular")
                    || roomType.equalsIgnoreCase("private")
                    || roomType.equalsIgnoreCase("none")) {
                return roomType;
            }
            System.out.println("Invalid input. Please enter only 'regular', 'private' or 'none'.");
        }
    }

    private static void printHeader() {
        printDivider();
        System.out.println(" SMART HOSPITAL MANAGEMENT SYSTEM");
        System.out.println(" Patient Billing Console");
        printDivider();
    }

    private static void printBill(int tokenNumber, String patientName, int patientAge,
                                   String baseType, int baseCost,
                                   String[] serviceNames, int[] serviceCosts, int serviceCount,
                                   int subtotal, int taxAmount, int finalAmount) {
        System.out.println();
        printDivider();
        System.out.println(" HOSPITAL INVOICE");
        printDivider();
        System.out.println("Token No : " + tokenNumber);
        System.out.println("Patient Name : " + patientName);
        System.out.println("Patient Age : " + patientAge);
        printDivider();
        printBillLine("Base Consultation (" + baseType + ")", baseCost);
        for (int index = 0; index < serviceCount; index++) {
            printBillLine(serviceNames[index], serviceCosts[index]);
        }
        printDivider();
        printBillLine("Subtotal", subtotal);
        printBillLine("Tax (5%)", taxAmount);
        printDivider();
        printBillLine("Grand Total", finalAmount);
        printDivider();
    }

    private static void printSessionSummary(int totalPatients, int overallRevenue) {
        System.out.println("\nSESSION SUMMARY");
        printDivider();
        System.out.println("Total Patients Processed : " + totalPatients);
        System.out.println("Total Revenue Collected : INR " + overallRevenue);
        printDivider();
    }

    private static void printDivider() {
        System.out.println("----------------------------------------");
    }

    private static void printBillLine(String label, int amount) {
        String line = padRight(label, 28) + " INR " + padLeft(String.valueOf(amount), 6);
        System.out.println(line);
    }

    private static int addServiceToBill(String[] serviceNames, int[] serviceCosts,
                                         int serviceCount, String serviceName, int serviceCost) {
        serviceNames[serviceCount] = serviceName;
        serviceCosts[serviceCount] = serviceCost;
        return serviceCount + 1;
    }

    private static boolean isValidName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            boolean isLetter = (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
            boolean isSpace  = ch == ' ';
            if (!isLetter && !isSpace) {
                return false;
            }
        }
        return true;
    }

    private static String padRight(String text, int totalLength) {
        String result = text;
        while (result.length() < totalLength) {
            result = result + " ";
        }
        return result;
    }

    private static String padLeft(String text, int totalLength) {
        String result = text;
        while (result.length() < totalLength) {
            result = " " + result;
        }
        return result;
    }
}