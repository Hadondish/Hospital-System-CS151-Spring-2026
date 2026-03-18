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

    // Getters and Setters

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity >= 0) {
            this.capacity = capacity;
        }
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<StaffMember> getStaff() {
        return staff;
    }

    // Business Logic Methods

    public void addPatient(Patient p) {
        if (isFull()) {
            System.out.println("Department is full. Cannot add patient.");
            return;
        }

        if (!patients.contains(p)) {
            patients.add(p);
        }
    }

    public void removePatient(Patient p) {
        patients.remove(p);
    }



    //this department  ----move staff---->  d department
    //this department = source department
    //d = destination department

    public void movePatient(Patient p, Department d) {
        if (!patients.contains(p)) {
            System.out.println("Patient not found in this department.");
            return;
        }

        if (!d.canAcceptPatient(p)) {
            System.out.println("Target department cannot accept this patient.");
            return;
        }

        removePatient(p);
        d.addPatient(p);
    }

    public void hireStaff(StaffMember s) {
        if (!staff.contains(s)) {
            staff.add(s);
        }
    }

    public void removeStaff(StaffMember s) {
        staff.remove(s);
    }

    public void moveStaff(StaffMember s, Department d) {
        if (!staff.contains(s)) {
            System.out.println("Staff member not found.");
            return;
        }

        removeStaff(s);
        d.hireStaff(s);
    }

    public boolean isFull() {
        return patients.size() >= capacity;
    }

    public boolean canAcceptPatient(Patient p) {
        return !isFull();
    }

    // Abstract method for subclasses
    public abstract String getDepartmentType();

    @Override
    public String toString() {
        return "Department{" +
                "deptId='" + deptId + '\'' +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", patients=" + patients.size() +
                ", staff=" + staff.size() +
                '}';
    }
}