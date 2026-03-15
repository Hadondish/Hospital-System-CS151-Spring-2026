package hospital;

import java.util.*;

public class HospitalSystem {
    private List<Department> departments;
    private Map<String, Patient> patientsById;
    private Map<String, StaffMember> staffById;
    private Map<String, Appointment> appointmentsById;

    public HospitalSystem() {
        this.departments = new ArrayList<>();
        this.patientsById = new HashMap<>();
        this.staffById = new HashMap<>();
        this.appointmentsById = new HashMap<>();
    }

    public void addDepartment(Department dept) {}
    public Department getDepartment(String name) { return null; }
    public void registerPatient(Patient p) throws FullCapacityException {}
    public void hireStaff(StaffMember s) {}
    public void scheduleAppointment(Appointment a) throws SchedulingConflictException {}
    public void cancelAppointment(String appointmentId, String reason) {}
    public Patient findPatient(String id) throws NotFoundException { return null; }
    public StaffMember findStaff(String id) throws NotFoundException { return null; }
    public Appointment findAppointment(String id) throws NotFoundException { return null; }
    public String getSystemReport() { return ""; }
    public String listAppointmentsForPatient(String patientId) { return ""; }
    public String listAppointmentsForStaff(String employeeId) { return ""; }
    public String listAppointmentsForDepartment(String deptId) { return ""; }
}
