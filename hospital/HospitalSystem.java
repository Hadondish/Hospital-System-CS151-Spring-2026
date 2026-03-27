package hospital;

import java.util.*;

public class HospitalSystem {
    public static final int MAX_INSTANCES = 100;

    // Attributes

    private ArrayList<Department> departments;
    private Map<String, Patient> patientsById;
    private Map<String, StaffMember> staffById;
    private Map<String, Appointment> appointmentsById;

    // Constructors

    public HospitalSystem() {
        this.departments = new ArrayList<>();
        this.patientsById = new HashMap<>();
        this.staffById = new HashMap<>();
        this.appointmentsById = new HashMap<>();
    }

    public HospitalSystem(ArrayList<Department> departments, Map<String, Patient> patientsById, Map<String, StaffMember> staffById, Map<String, Appointment> appointmentsById) {
        this.departments = departments;
        this.patientsById = patientsById;
        this.staffById = staffById;
        this.appointmentsById = appointmentsById;
    }

    // Methods

    // Adds a new department to the hospital system
    public void addDepartment(Department dept) {
        this.departments.add(dept);
    }

    // Retrieves a department by name, throwing an exception if not found
    public Department getDepartment(String name) throws NotFoundException { 
        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).getName().equals(name)) {
                return departments.get(i);
            }
        }

        throw new NotFoundException("Department not found in System: " + name);
    }

    // Registers a new patient in the hospital system, ensuring the system is not at full capacity and that the patient ID is unique
    public void registerPatient(Patient p) throws FullCapacityException {
        if (patientsById.size() >= MAX_INSTANCES) {
            throw new FullCapacityException("Cannot register patient, system at full capacity.");
        }

        if (patientsById.containsKey(p.getPatientId())) {
            System.out.println("Patient with ID " + p.getPatientId() + " already exists.");
            return;
        }

        patientsById.put(p.getPatientId(), p);
    }

    // Hires a new staff member, ensuring the system is not at full capacity and that the employee ID is unique
    public void hireStaff(StaffMember s) throws FullCapacityException {
        if (staffById.containsKey(s.getEmployeeId())) {
            System.out.println("Staff member with ID " + s.getEmployeeId() + " already exists. Cannot hire.");
            return;
        } else if (staffById.size() >= MAX_INSTANCES) {
            throw new FullCapacityException("Cannot hire staff, system at full capacity.");
        } else {
            // create a new staff member and add to system
            System.out.println("Hiring staff member: " + s.getEmployeeId() + " - " + s.getRole() + "... Success!");
            staffById.put(s.getEmployeeId(), s);
        }
    }

    // Schedules a new appointment, ensuring there are no scheduling conflicts for the provider and that the appointment ID is unique
    public void scheduleAppointment(Appointment a) throws SchedulingConflictException {
        for (Appointment existing : appointmentsById.values()) {
            if (existing.getProvider().equals(a.getProvider()) &&
                existing.getDateTime().equals(a.getDateTime())) {
                throw new SchedulingConflictException("Provider already booked at that time.");
            }
        }
        appointmentsById.put(a.getAppointmentId(), a);
    }

    // Cancels an existing appointment by ID, ensuring the appointment exists before attempting to cancel
    public void cancelAppointment(String appointmentId, String reason) {
        if (!appointmentsById.containsKey(appointmentId)) {
            System.out.println("Appointment with ID " + appointmentId + " not found. Cannot cancel.");
            return;
        }
        Appointment a = appointmentsById.get(appointmentId);
        a.cancel(reason);
    }

    // Finds and returns a patient by ID, throwing an exception if the patient is not found
    public Patient findPatient(String id) throws NotFoundException { 
        if (!patientsById.containsKey(id)) {
            throw new NotFoundException("Patient with ID " + id + " not found.");
        }
        return patientsById.get(id);
     }

    // Finds and returns a staff member by ID, throwing an exception if the staff member is not found
    public StaffMember findStaff(String id) throws NotFoundException { 
        if (!staffById.containsKey(id)) {
            throw new NotFoundException("Staff member with ID " + id + " not found.");
        }
        return staffById.get(id);
        }
    
    // Finds and returns an appointment by ID, throwing an exception if the appointment is not found
    public Appointment findAppointment(String id) throws NotFoundException { 
        if (!appointmentsById.containsKey(id)) {
            throw new NotFoundException("Appointment with ID " + id + " not found.");
        }
        return appointmentsById.get(id);
     }

    // Generates and prints a report of the hospital system, including total counts of departments, patients, staff members, and appointments
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

    // Lists all appointments for a given patient ID, throwing an exception if the patient is not found
    public void listAppointmentsForPatient(String patientId) throws NotFoundException {
        if (!patientsById.containsKey(patientId)) {
            throw new NotFoundException("Patient with ID " + patientId + " not found.");
        } else {
            for (Appointment a : appointmentsById.values()) {
                if (a.matches(patientId)) {
                    System.out.println(a);
                }
            }
        }
    }

    // Lists all appointments for a given staff member ID, throwing an exception if the staff member is not found
    public void listAppointmentsForStaff(String employeeId) throws NotFoundException {
        if (!staffById.containsKey(employeeId)) {
            throw new NotFoundException("Staff member with ID " + employeeId + " not found.");
        } else {
            for (Appointment a : appointmentsById.values()) {
                if (a.matches(employeeId)) {
                    System.out.println(a);
                }
            }
        }
    }

    // Lists all appointments for a given department name, throwing an exception if the department is not found
    public void listAppointmentsForDepartment(String deptName) throws NotFoundException {
        Department dept = getDepartment(deptName);

        for (Appointment a : appointmentsById.values()) {
            if (a.getDepartment().equals(dept)) {
                System.out.println(a);
            }
        }

        }

}

