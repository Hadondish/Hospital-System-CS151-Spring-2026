package hospital;

import java.util.*;

public class HospitalSystem {
    public static final int MAX_INSTANCES = 100;

    private ArrayList<Department> departments;
    private Map<String, Patient> patientsById;
    private Map<String, StaffMember> staffById;
    private Map<String, Appointment> appointmentsById;
    private static int patientCount = 0;

    public HospitalSystem() {
        this.departments = new ArrayList<>();
        this.patientsById = new HashMap<>();
        this.staffById = new HashMap<>();
        this.appointmentsById = new HashMap<>();
        this.patientCount = 0;
    }

    public HospitalSystem(ArrayList<Department> departments, Map<String, Patient> patientsById, Map<String, StaffMember> staffById, Map<String, Appointment> appointmentsById, int patientCount) {
        this.departments = departments;
        this.patientsById = patientsById;
        this.staffById = staffById;
        this.appointmentsById = appointmentsById;
        this.patientCount = patientCount;
    }

    public void addDepartment(Department dept) {
        this.departments.add(dept);
    }

    public Department getDepartment(String name) throws NotFoundException { 
        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).getName().equals(name)) {
                return departments.get(i);
            }
        }

        throw new NotFoundException("Department not found in System: " + name);
    }

    public void registerPatient() throws FullCapacityException {
        if (patientCount >= MAX_INSTANCES) {
            throw new FullCapacityException("Cannot register patient, system at full capacity");
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Registering new patient...");

        System.out.println("Enter patient details:");

        System.out.println("Name: ");
        String name = scanner.nextLine();
        System.out.println("Age: ");

        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Gender: ");
        String gender = scanner.nextLine();

        System.out.println("New Patient Id: ");
        String id = scanner.nextLine();

        Patient newPatient = new Patient(name, age, gender, id);    // TODO: Add more details to patient constructor and registration process
        patientsById.put(newPatient.getId(), newPatient);
    }

    public void hireStaff(StaffMember s) throws FullCapacityException {
        if (staffById.containsKey(s.getId())) {
            System.out.println("Staff member with ID " + s.getId() + " already exists. Cannot hire.");
            return;
        } else if (staffById.size() >= MAX_INSTANCES) {
            throw new FullCapacityException("Cannot hire staff, system at full capacity.");
        } else {
            // create a new staff member and add to system
            staffById.put(s.getId(), s);
        }
    }

    public void scheduleAppointment(Appointment a) throws SchedulingConflictException {
        if (appointmentsById.containsKey(a.getId())) {
            throw new SchedulingConflictException("Appointment with ID " + a.getId() + " already exists. Cannot schedule.");
        }

        appointmentsById.put(a.getId(), a);
        a.setStatus(Appointment.Status.SCHEDULED);
    }


    public void cancelAppointment(String appointmentId, String reason) {
        if (!appointmentsById.containsKey(appointmentId)) {
            System.out.println("Appointment with ID " + appointmentId + " not found. Cannot cancel.");
            return;
        }
        Appointment a = appointmentsById.get(appointmentId);
        a.cancel(reason);
    }


    public Patient findPatient(String id) throws NotFoundException { 
        if (!patientsById.containsKey(id)) {
            throw new NotFoundException("Patient with ID " + id + " not found.");
        }
        return patientsById.get(id);
     }
    public StaffMember findStaff(String id) throws NotFoundException { 
        if (!staffById.containsKey(id)) {
            throw new NotFoundException("Staff member with ID " + id + " not found.");
        }
        return staffById.get(id);
        }
     
    public Appointment findAppointment(String id) throws NotFoundException { 
        if (!appointmentsById.containsKey(id)) {
            throw new NotFoundException("Appointment with ID " + id + " not found.");
        }
        return appointmentsById.get(id);
     }
    public void getSystemReport() {
        System.out.println("===============================");
        System.out.println("Hospital System Report");
        System.out.println("===============================");
        System.out.println("Total Departments: " + this.departments.size());
        System.out.println("Total Patients: " + patientsById.size());
        System.out.println("Total Staff Members: " + staffById.size());
        System.out.println("Total Appointments: " + appointmentsById.size());
        System.out.println("===============================");
    }
    public void listAppointmentsForPatient(String patientId) throws NotFoundException {
        if (!patientsById.containsKey(patientId)) {
            throw new NotFoundException("Patient with ID " + patientId + " not found.");
        } else {
            patientsById.get(patientId).listAppointments(); // TODO: Implement method to list appointments for patient
        }
    }
    public void listAppointmentsForStaff(String employeeId) throws NotFoundException {
        if (!staffById.containsKey(employeeId)) {
            throw new NotFoundException("Staff member with ID " + employeeId + " not found.");
        } else {
            staffById.get(employeeId).listAppointments(); // TODO: Implement method to list appointments for staff member
        }
    }
    public void listAppointmentsForDepartment(String deptId) throws NotFoundException {
        for (Department dept : departments) {
            if (dept.getId().equals(deptId)) {
                dept.listAppointments(); // TODO: Implement method to list appointments for department
                return;
            }
        }
        throw new NotFoundException("Department with ID " + deptId + " not found.");
    }

}

