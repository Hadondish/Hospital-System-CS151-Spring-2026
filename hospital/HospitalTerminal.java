// Kevin Tran
// Hospital Management System User Interface

package hospital;
 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Scanner;
 
/**
 * Text-based UI driver for the Hospital Management System.
 * Uses Java Scanner for all user input.
 */
public class HospitalTerminal {
 
    private static final Scanner scanner = new Scanner(System.in);
    private static final HospitalSystem system = new HospitalSystem();
    private static final DateTimeFormatter DT_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
 
    // Starting Main Menu
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   Hospital Management System         ║");
        System.out.println("╚══════════════════════════════════════╝");
 
        seedDemoData();   // pre-load sample departments so the system is usable immediately
 
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1  -> departmentMenu();
                case 2  -> patientMenu();
                case 3  -> staffMenu();
                case 4  -> appointmentMenu();
                case 5  -> pharmacyMenu();
                case 6  -> system.getSystemReport();
                case 0  -> { running = false; System.out.println("Goodbye!"); }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }
 
    // Menus
    private static void printMainMenu() {
        System.out.println("\n══════════════ MAIN MENU ══════════════");
        System.out.println(" 1. Department Management");
        System.out.println(" 2. Patient Management");
        System.out.println(" 3. Staff Management");
        System.out.println(" 4. Appointment Management");
        System.out.println(" 5. Pharmacy Management");
        System.out.println(" 6. System Report");
        System.out.println(" 0. Exit");
        System.out.println("═══════════════════════════════════════");
    }
 
    // Departments
    private static void departmentMenu() {
        System.out.println("\n─── Department Management ───");
        System.out.println(" 1. Add Department");
        System.out.println(" 2. View Department Info");
        System.out.println(" 3. List Appointments for Department");
        System.out.println(" 0. Back");
        int choice = readInt("Enter choice: ");
        switch (choice) {
            case 1 -> addDepartment();
            case 2 -> viewDepartment();
            case 3 -> listAppointmentsForDepartment();
            case 0 -> {}
            default -> System.out.println("Invalid option.");
        }
    }
    
    // Add Department
    private static void addDepartment() {
        System.out.println("\n-- Add Department --");
        System.out.println(" 1. Surgery Department");
        System.out.println(" 2. Family Care Department");
        System.out.println(" 3. Pharmacy");
        int type = readInt("Select type: ");
 
        String deptId   = readString("Department ID: ");
        String deptName = readString("Department name: ");
        int capacity    = readInt("Patient capacity: ");
 
        switch (type) {
            case 1 -> {
                int rooms = readInt("Number of operating rooms: ");
                system.addDepartment(new SurgeryDepartment(deptId, deptName, capacity, rooms));
                System.out.println("Surgery department added.");
            }
            case 2 -> {
                int limit = readInt("Daily appointment limit: ");
                system.addDepartment(new FamilyCareDepartment(deptId, deptName, capacity, limit));
                System.out.println("Family care department added.");
            }
            case 3 -> {
                system.addDepartment(new Pharmacy(deptId, deptName, capacity));
                System.out.println("Pharmacy added.");
            }
            default -> System.out.println("Invalid department type.");
        }
    }
 
    private static void viewDepartment() {
        String name = readString("Department name: ");
        try {
            Department dept = system.getDepartment(name);
            System.out.println("\n" + dept);
            System.out.println("Type: " + dept.getDepartmentType());
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
 
    private static void listAppointmentsForDepartment() {
        String name = readString("Department name: ");
        try {
            system.listAppointmentsForDepartment(name);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
 
    //Patients
    private static void patientMenu() {
        System.out.println("\n─── Patient Management ───");
        System.out.println(" 1. Register Patient");
        System.out.println(" 2. Find Patient");
        System.out.println(" 3. Admit Patient to Department");
        System.out.println(" 4. Discharge Patient");
        System.out.println(" 5. Transfer Patient");
        System.out.println(" 6. Add Visit Summary");
        System.out.println(" 7. View Visit Summaries");
        System.out.println(" 8. List Patient Appointments");
        System.out.println(" 0. Back");
        int choice = readInt("Enter choice: ");
        switch (choice) {
            case 1 -> registerPatient();
            case 2 -> findPatient();
            case 3 -> admitPatient();
            case 4 -> dischargePatient(); 
            case 5 -> transferPatient();
            case 6 -> addVisitSummary();
            case 7 -> viewVisitSummaries();
            case 8 -> listPatientAppointments();
            case 0 -> {}
            default -> System.out.println("Invalid option.");
        }
    }
 
    private static void registerPatient() {
        String id  = readString("Patient ID: ");
        String dob = readString("Date of birth (YYYY-MM-DD): ");
        Patient p = new Patient(id, dob);
        try {
            system.registerPatient(p);
            System.out.println("Patient registered: " + p);
        } catch (FullCapacityException e) {
            System.out.println(e.getMessage());
        }
    }
 
    private static void findPatient() {
        String id = readString("Patient ID: ");
        try {
            System.out.println(system.findPatient(id));
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
 
    private static void admitPatient() {
        String patientId = readString("Patient ID: ");
        String deptName  = readString("Department name: ");
        try {
            Patient p    = system.findPatient(patientId);
            Department d = system.getDepartment(deptName);
            p.admitTo(d);
            System.out.println("Patient admitted to " + deptName + ".");
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
 
    private static void dischargePatient() {
        String patientId = readString("Patient ID: ");
        try {
            Patient p = system.findPatient(patientId);
            p.discharge();
            System.out.println("Patient discharged.");
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
 
    private static void transferPatient() {
        String patientId = readString("Patient ID: ");
        String deptName  = readString("Destination department name: ");
        try {
            Patient    p    = system.findPatient(patientId);
            Department dest = system.getDepartment(deptName);
            p.transferTo(dest);
            System.out.println("Patient transferred to " + deptName + ".");
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        } catch (FullCapacityException e) {
            System.out.println(e.getMessage());
        }
    }
 
    private static void addVisitSummary() {
        String patientId = readString("Patient ID: ");
        String visitId   = readString("Visit ID: ");
        try {
            Patient p = system.findPatient(patientId);
            VisitSummary vs = new VisitSummary(visitId, patientId);
 
            // Optionally add billable charges
            boolean addingCharges = true;
            while (addingCharges) {
                System.out.println(" Add charge?  1=Medication  2=Surgery  0=Done");
                int ch = readInt("Choice: ");
                switch (ch) {
                    case 1 -> {
                        String medName  = readString("  Medication name: ");
                        int unitPrice   = readInt("  Unit price ($): ");
                        int qty         = readInt("  Quantity: ");
                        vs.addCharge(new Medication(medName, unitPrice, qty));
                        System.out.println("  Medication charge added.");
                    }
                    case 2 -> {
                        String proc      = readString("  Procedure name: ");
                        int surgFee      = readInt("  Surgeon fee ($): ");
                        boolean anest    = readYesNo("  Anesthesia used? (y/n): ");
                        int anestFee     = anest ? readInt("  Anesthesia fee ($): ") : 0;
                        vs.addCharge(new SurgeryBill(new HashMap<>(), proc, surgFee, anestFee, anest));
                        System.out.println("  Surgery charge added.");
                    }
                    case 0 -> addingCharges = false;
                    default -> System.out.println("  Invalid option.");
                }
            }
 
            p.addVisitSummary(vs);
            System.out.println("Visit summary added (total: $" + vs.getTotalCost() + ").");
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
 
    private static void viewVisitSummaries() {
        String patientId = readString("Patient ID: ");
        try {
            Patient p = system.findPatient(patientId);
            System.out.println(p.viewVisitSummaries());
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
 
    private static void listPatientAppointments() {
        String id = readString("Patient ID: ");
        try {
            system.listAppointmentsForPatient(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
 
    // Staff
    private static void staffMenu() {
        System.out.println("\n─── Staff Management ───");
        System.out.println(" 1. Hire Staff Member");
        System.out.println(" 2. Find Staff Member");
        System.out.println(" 3. Assign Staff to Department");
        System.out.println(" 4. Remove Staff from Department");
        System.out.println(" 5. Move Staff to Another Department");
        System.out.println(" 6. Change Staff Role");
        System.out.println(" 7. Terminate Employment");
        System.out.println(" 8. List Staff Appointments");
        System.out.println(" 0. Back");
        int choice = readInt("Enter choice: ");
        switch (choice) {
            case 1 -> hireStaff();
            case 2 -> findStaff();
            case 3 -> assignStaffToDept();
            case 4 -> removeStaffFromDept();
            case 5 -> moveStaff();
            case 6 -> changeStaffRole();
            case 7 -> terminateStaff();
            case 8 -> listStaffAppointments();
            case 0 -> {}
            default -> System.out.println("Invalid option.");
        }
    }
 
    private static void hireStaff() {
        String id   = readString("Employee ID: ");
        String role = readString("Role: ");
        String name = readString("Name: ");
        StaffMember s = new StaffMember(id, role);
        s.setName(name);
        try {
            system.hireStaff(s);
            System.out.println("Staff hired: " + s);
        } catch (FullCapacityException e) {
            System.out.println(e.getMessage());
        }
    }
 
    private static void findStaff() {
        String id = readString("Employee ID: ");
        try {
            System.out.println(system.findStaff(id));
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
 
    private static void assignStaffToDept() {
        String empId    = readString("Employee ID: ");
        String deptName = readString("Department name: ");
        try {
            StaffMember s = system.findStaff(empId);
            Department  d = system.getDepartment(deptName);
            s.assignToDepartment(d);
            System.out.println(s.getName() + " assigned to " + deptName + ".");
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        } catch (FullCapacityException e) {
            System.out.println(e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
 
    private static void removeStaffFromDept() {
        String empId = readString("Employee ID: ");
        try {
            StaffMember s = system.findStaff(empId);
            s.removeFromDepartment();
            System.out.println(s.getName() + " removed from department.");
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
 
    private static void moveStaff() {
        String empId    = readString("Employee ID: ");
        String srcName  = readString("Source department name: ");
        String destName = readString("Destination department name: ");
        try {
            StaffMember s = system.findStaff(empId);
            Department src  = system.getDepartment(srcName);
            Department dest = system.getDepartment(destName);
            src.moveStaff(s, dest);
            System.out.println("Staff moved to " + destName + ".");
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
 
    private static void changeStaffRole() {
        String empId   = readString("Employee ID: ");
        String newRole = readString("New role: ");
        try {
            StaffMember s = system.findStaff(empId);
            s.changeRole(newRole);
            System.out.println("Role updated to: " + newRole);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
 
    private static void terminateStaff() {
        String empId = readString("Employee ID: ");
        try {
            StaffMember s = system.findStaff(empId);
            s.terminateEmployment();
            System.out.println(s.getName() + " employment terminated.");
            system.fireStaff(empId);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
 
    private static void listStaffAppointments() {
        String empId = readString("Employee ID: ");
        try {
            system.listAppointmentsForStaff(empId);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
 
    // Appointments
    private static void appointmentMenu() {
        System.out.println("\n─── Appointment Management ───");
        System.out.println(" 1. Schedule Appointment");
        System.out.println(" 2. Find Appointment");
        System.out.println(" 3. Cancel Appointment");
        System.out.println(" 4. Complete Appointment");
        System.out.println(" 5. Reschedule Appointment");
        System.out.println(" 0. Back");
        int choice = readInt("Enter choice: ");
        switch (choice) {
            case 1 -> scheduleAppointment();
            case 2 -> findAppointment();
            case 3 -> cancelAppointment();
            case 4 -> completeAppointment();
            case 5 -> rescheduleAppointment();
            case 0 -> {}
            default -> System.out.println("Invalid option.");
        }
    }
 
    private static void scheduleAppointment() {
        String apptId   = readString("Appointment ID: ");
        String apptType = readString("Appointment type (e.g. Checkup, Surgery): ");
        String dtStr    = readString("Date & time (yyyy-MM-dd HH:mm): ");
        String patId    = readString("Patient ID: ");
        String empId    = readString("Provider (employee) ID: ");
        String deptName = readString("Department name: ");
 
        try {
            LocalDateTime dt = LocalDateTime.parse(dtStr, DT_FORMAT);
            Patient     p = system.findPatient(patId);
            StaffMember s = system.findStaff(empId);
            Department  d = system.getDepartment(deptName);
 
            Appointment a = new Appointment(apptId, apptType, dt, p, s, d);
            system.scheduleAppointment(a);
            System.out.println("Appointment scheduled for " + dtStr + ".");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd HH:mm.");
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SchedulingConflictException e) {
            System.out.println("Scheduling conflict: " + e.getMessage());
        }
    }
 
    private static void findAppointment() {
        String id = readString("Appointment ID: ");
        try {
            Appointment a = system.findAppointment(id);
            System.out.println("Appointment ID : " + a.getAppointmentId());
            System.out.println("Patient        : " + a.getPatient().getPatientId());
            System.out.println("Provider       : " + a.getProvider().getEmployeeId());
            System.out.println("Department     : " + a.getDepartment().getName());
            System.out.println("Date/Time      : " + a.getDateTime().format(DT_FORMAT));
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
 
    private static void cancelAppointment() {
        String id     = readString("Appointment ID: ");
        String reason = readString("Cancellation reason: ");
        system.cancelAppointment(id, reason);
    }
 
    private static void completeAppointment() {
        String id = readString("Appointment ID: ");
        try {
            Appointment a = system.findAppointment(id);
            a.complete();
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
 
    private static void rescheduleAppointment() {
        String id    = readString("Appointment ID: ");
        String dtStr = readString("New date & time (yyyy-MM-dd HH:mm): ");
        try {
            LocalDateTime newDt = LocalDateTime.parse(dtStr, DT_FORMAT);
            Appointment a = system.findAppointment(id);
            a.reschedule(newDt);
            System.out.println("Appointment rescheduled to " + dtStr + ".");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd HH:mm.");
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
 
    // Pharmacy
    private static void pharmacyMenu() {
        System.out.println("\n─── Pharmacy Management ───");
        System.out.println(" 1. Add Medication to Pharmacy");
        System.out.println(" 2. Remove Medication from Pharmacy");
        System.out.println(" 3. List Medications");
        System.out.println(" 4. Fill Prescription");
        System.out.println(" 5. Restock Medication");
        System.out.println(" 0. Back");
        int choice = readInt("Enter choice: ");
        switch (choice) {
            case 1 -> addMedication();
            case 2 -> removeMedication();
            case 3 -> listMedications();
            case 4 -> fillPrescription();
            case 5 -> restockMedication();
            case 0 -> {}
            default -> System.out.println("Invalid option.");
        }
    }
 
    private static Pharmacy getPharmacy() {
        String name = readString("Pharmacy department name: ");
        try {
            Department d = system.getDepartment(name);
            if (d instanceof Pharmacy) return (Pharmacy) d;
            System.out.println("That department is not a pharmacy.");
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
 
    private static void addMedication() {
        Pharmacy ph = getPharmacy();
        if (ph == null) return;
        String medName = readString("Medication name: ");
        int unitPrice  = readInt("Unit price ($): ");
        int qty        = readInt("Quantity: ");
        ph.addMedication(new Medication(medName, unitPrice, qty));
    }
 
    private static void removeMedication() {
        Pharmacy ph = getPharmacy();
        if (ph == null) return;
        System.out.println("Current inventory: " + ph.listMedications());
        String medName = readString("Medication name to remove: ");
        int unitPrice  = readInt("Unit price ($): ");
        int qty        = readInt("Quantity: ");
        ph.removeMedication(new Medication(medName, unitPrice, qty));
    }
 
    private static void listMedications() {
        Pharmacy ph = getPharmacy();
        if (ph == null) return;
        System.out.println("Medications: " + ph.listMedications());
    }
 
    private static void fillPrescription() {
        Pharmacy ph = getPharmacy();
        if (ph == null) return;
        String patientId = readString("Patient ID: ");
        String medName   = readString("Medication name: ");
        int unitPrice    = readInt("Unit price ($): ");
        int qty          = readInt("Quantity: ");
        ph.fillPrescription(patientId, new Medication(medName, unitPrice, qty));
    }
 
    private static void restockMedication() {
        Pharmacy ph = getPharmacy();
        if (ph == null) return;
        String medName = readString("Medication name: ");
        int unitPrice  = readInt("Unit price ($): ");
        int qty        = readInt("Current quantity: ");
        int amount     = readInt("Restock amount: ");
        ph.restockMedication(new Medication(medName, unitPrice, qty), amount);
    }
 
    // Input Helpers
    private static String readString(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine().trim();
        if (line.equalsIgnoreCase("exit")) {
            System.out.println("Exiting. Goodbye!");
            scanner.close();
            System.exit(0);
        }
        return line;
    }
 
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("exit")) {
                System.out.println("Exiting. Goodbye!");
                scanner.close();
                System.exit(0);
            }
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }
 
    private static boolean readYesNo(String prompt) {
        System.out.print(prompt);
        String answer = scanner.nextLine().trim().toLowerCase();
        return answer.equals("y") || answer.equals("yes");
    }
 
    // Pre seed data
    private static void seedDemoData() {
        try {
            // Departments
            SurgeryDepartment surgery =
                    new SurgeryDepartment("D001", "Surgery", 20, 5);
            FamilyCareDepartment familyCare =
                    new FamilyCareDepartment("D002", "Family Care", 30, 10);
            Pharmacy pharmacy =
                    new Pharmacy("D003", "Pharmacy", 0);
 
            system.addDepartment(surgery);
            system.addDepartment(familyCare);
            system.addDepartment(pharmacy);
 
            // Staff
            StaffMember doc1 = new StaffMember("E001", "Surgeon");
            doc1.setName("Dr. Alice Smith");
            StaffMember doc2 = new StaffMember("E002", "Family Physician");
            doc2.setName("Dr. Bob Jones");
 
            system.hireStaff(doc1);
            system.hireStaff(doc2);
 
            doc1.assignToDepartment(surgery);
            doc2.assignToDepartment(familyCare);
 
            // Patients
            Patient p1 = new Patient("P001", "1985-03-12");
            Patient p2 = new Patient("P002", "1992-07-24");
            system.registerPatient(p1);
            system.registerPatient(p2);
 
            // Medications in pharmacy
            pharmacy.addMedication(new Medication("Ibuprofen", 5, 100));
            pharmacy.addMedication(new Medication("Amoxicillin", 12, 50));
 
            System.out.println("Demo data loaded. Type 6 from the main menu to see the report.");
        } catch (Exception e) {
            System.out.println("Seed data error: " + e.getMessage());
        }
    }
}