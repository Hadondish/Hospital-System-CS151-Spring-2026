package hospital;

import java.util.*;

public abstract class Department {
    private String deptId;
    private String name;
    private int capacity;
    private List<Patient> patients;
    private List<StaffMember> staff;

    public Department(String deptId, String name, int capacity) {
        this.deptId = deptId;
        this.name = name;
        this.capacity = capacity;
        this.patients = new ArrayList<>();
        this.staff = new ArrayList<>();
    }

    public void addPatient(Patient p) {}
    public void removePatient(Patient p) {}
    public void movePatient(Patient p, Department d) {}
    public void hireStaff(StaffMember s) {}
    public void removeStaff(StaffMember s) {}
    public void moveStaff(StaffMember s, Department d) {}
    public boolean isFull() { return false; }
    public String getCapacity() { return ""; }
    public String getPatients() { return ""; }
    public String getStaff() { return ""; }
    public boolean canAcceptPatient(Patient p) { return true; }
    public abstract String getDepartmentType();
}
