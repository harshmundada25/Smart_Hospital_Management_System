# Smart Hospital Management System

Console-based Java project that simulates a hospital patient journey from registration to billing and department handoff.

It demonstrates three core design patterns:

1. Factory Pattern for patient creation
2. Decorator Pattern for optional services and room charges
3. Chain of Responsibility Pattern for hospital department workflow

---

## Overview

The program accepts patient details, validates them, creates a base patient type, applies optional service decorators, generates a final invoice, and then sends a request through the hospital chain.

Supported patient types:

1. Regular
2. Emergency

Supported add-ons:

1. ICU Service
2. Lab Tests
3. Insurance Assistance
4. Ambulance Support
5. Room Service: regular, private, or none

---

## Workflow

Main entry point: [Main.java](Main.java)

1. Print the console header.
2. Read and validate patient name, age, and type.
3. Create a base patient using [PatientFactory.java](PatientFactory.java).
4. Ask for optional services one by one.
5. Wrap the patient with decorator classes for each selected service.
6. Ask for room service selection: regular, private, or none.
7. Calculate subtotal, tax at 5%, and grand total.
8. Print the invoice with itemized charges.
9. Run the hospital handler chain: Reception -> Doctor -> Pharmacy -> BillingDesk.
10. Update session totals for patients processed and revenue collected.
11. Continue until the user stops.
12. Print a session summary.

---

## Project Structure

- [Main.java](Main.java): User interaction, billing flow, and orchestration
- [Patient.java](Patient.java): Common interface for patients and decorators
- [RegularPatient.java](RegularPatient.java), [EmergencyPatient.java](EmergencyPatient.java): Base patient types
- [PatientFactory.java](PatientFactory.java): Factory for creating base patient objects
- [PatientDecorator.java](PatientDecorator.java): Abstract base decorator
- [ICUService.java](ICUService.java), [LabTestService.java](LabTestService.java), [InsuranceService.java](InsuranceService.java), [AmbulanceSupportService.java](AmbulanceSupportService.java): Optional service decorators
- [RegularRoomService.java](RegularRoomService.java), [PrivateRoomService.java](PrivateRoomService.java): Room decorators
- [Handler.java](Handler.java), [Reception.java](Reception.java), [Doctor.java](Doctor.java), [Pharmacy.java](Pharmacy.java), [BillingDesk.java](BillingDesk.java): Chain of Responsibility classes

---

## Design Patterns

### Factory Pattern

[PatientFactory.java](PatientFactory.java) centralizes patient creation.

- `regular` returns a `RegularPatient`
- `emergency` returns an `EmergencyPatient`
- Invalid input throws `IllegalArgumentException`

### Decorator Pattern

The `Patient` object is wrapped by service decorators at runtime.

- Each decorator adds its own charge
- Multiple decorators can be stacked in any order selected by the user
- Room selection supports no room charge when `none` is chosen

### Chain of Responsibility

[Handler.java](Handler.java) defines the workflow chain and the concrete handlers process the request in sequence.

- Reception
- Doctor
- Pharmacy
- BillingDesk

---

## Validation Rules

Implemented in [Main.java](Main.java):

1. Name must contain only letters and spaces.
2. Age must be between 1 and 120.
3. Patient type must be `regular` or `emergency`.
4. Yes/no prompts accept only `y` or `n`.
5. Room type must be `regular`, `private`, or `none`.

---

## Billing Logic

1. Base cost comes from the selected patient type.
2. Each chosen decorator adds its charge.
3. Subtotal is the final decorated cost.
4. Tax is calculated as `round(subtotal * 0.05)`.
5. Grand total is `subtotal + tax`.
6. The invoice prints each line with aligned formatting.

---

## Diagrams

This folder includes visual documentation that matches the code structure:

- [SmartHospital_UML.png](SmartHospital_UML.png)
- [SmartHospital_Flowchart.png](SmartHospital_Flowchart.png)

---

## Compile and Run

From the project root:

```powershell
javac -d bin src/Smart_Hospital_Management_System/*.java
java -cp bin Smart_Hospital_Management_System.Main
```

---

## Sample Scenario

Example input:

1. Name: John Doe
2. Age: 30
3. Type: regular
4. Add ICU: y
5. Add Lab Tests: n
6. Add Insurance: y
7. Add Ambulance: n
8. Room: private

Example billing flow:

1. Base consultation
2. ICU Service
3. Insurance Assistance
4. Private Room Service
5. Subtotal
6. Tax
7. Grand Total

---

## Extending the Project

1. Add a new patient type by extending `Patient` and updating [PatientFactory.java](PatientFactory.java).
2. Add a new service by creating another decorator that extends [PatientDecorator.java](PatientDecorator.java).
3. Add a new department by extending [Handler.java](Handler.java) and inserting it into the chain in [Main.java](Main.java).


