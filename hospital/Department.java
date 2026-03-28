package hospital;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hospital department that manages patients and staff members.
 * This abstract class stores shared fields and behavior for all department types.
 */
public abstract class Department {

    private String deptId;
    private String name;
    private int capacity;
    private List<Patient> patients;
    private List<StaffMember> staff;

    /**
     * Constructs a department with an ID, name, and maximum patient capacity.
     *
     * @param deptId unique department ID
     * @param name department name
     * @param capacity maximum number of patients this department can hold
     */
    public Department(String deptId, String name, int capacity) {
        this.deptId = deptId;
        this.name = name;
        this.capacity = capacity;
        this.patients = new ArrayList<>();
        this.staff = new ArrayList<>();
    }

    /**
     * Returns the department ID.
     *
     * @return department ID
     */
    public String getDeptId() {
        return deptId;
    }

    /**
     * Updates the department ID.
     *
     * @param deptId new department ID
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    /**
     * Returns the department name.
     *
     * @return department name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the department name.
     *
     * @param name new department name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the patient capacity of this department.
     *
     * @return maximum patient capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Updates the department capacity if the value is non-negative.
     *
     * @param capacity new maximum patient capacity
     */
    public void setCapacity(int capacity) {
        if (capacity >= 0) {
            this.capacity = capacity;
        }
    }

    /**
     * Returns the list of patients currently assigned to this department.
     *
     * @return patient list
     */
    public List<Patient> getPatients() {
        return patients;
    }

    /**
     * Returns the list of staff currently assigned to this department.
     *
     * @return staff list
     */
    public List<StaffMember> getStaff() {
        return staff;
    }

    /**
     * Adds a patient to this department if the department is not full and the
     * patient is not already present.
     *
     * @param p patient to add
     */
    public void addPatient(Patient p) {
        if (p == null) {
            System.out.println("Patient cannot be null.");
            return;
        }

        if (isFull()) {
            System.out.println("Department is full. Cannot add patient.");
            return;
        }

        if (!patients.contains(p)) {
            patients.add(p);

            // Keep the patient object's department reference consistent.
            p.setAssignedDepartment(this);
        }
    }

    /**
     * Removes a patient from this department if present.
     *
     * @param p patient to remove
     */
    public void removePatient(Patient p) {
        if (p == null) {
            System.out.println("Patient cannot be null.");
            return;
        }

        if (!patients.contains(p)) {
            System.out.println("Patient not found in this department.");
            return;
        }

        patients.remove(p);

        // Clear the patient's reference only if it still points here.
        if (p.getAssignedDepartment() == this) {
            p.setAssignedDepartment(null);
        }
    }

    /**
     * Moves a patient from this department to another department if the patient
     * exists here and the destination can accept them.
     *
     * @param p patient to move
     * @param d destination department
     */
    public void movePatient(Patient p, Department d) {
        if (p == null || d == null) {
            System.out.println("Patient and destination department cannot be null.");
            return;
        }

        if (!patients.contains(p)) {
            System.out.println("Patient not found in this department.");
            return;
        }

        if (!d.canAcceptPatient(p)) {
            System.out.println("Target department cannot accept this patient.");
            return;
        }

        // Remove from the current department first.
        patients.remove(p);

        // Add to the destination department.
        if (!d.getPatients().contains(p)) {
            d.getPatients().add(p);
        }

        // Update the patient's assigned department reference.
        p.setAssignedDepartment(d);
    }

    /**
     * Adds a staff member to this department if they are not already present.
     *
     * @param s staff member to add
     */
    public void hireStaff(StaffMember s) {
        if (s == null) {
            System.out.println("Staff member cannot be null.");
            return;
        }

        if (!staff.contains(s)) {
            staff.add(s);
            s.setDepartment(this);
        }
    }

    /**
     * Removes a staff member from this department if present.
     *
     * @param s staff member to remove
     */
    public void removeStaff(StaffMember s) {
        if (s == null) {
            System.out.println("Staff member cannot be null.");
            return;
        }

        if (!staff.contains(s)) {
            System.out.println("Staff member not found.");
            return;
        }

        staff.remove(s);
    }

    /**
     * Moves a staff member from this department to another department.
     * This method only updates department lists and does not assume anything
     * about the internal implementation of StaffMember.
     *
     * @param s staff member to move
     * @param d destination department
     */
    public void moveStaff(StaffMember s, Department d) {
        if (s == null || d == null) {
            System.out.println("Staff member and destination department cannot be null.");
            return;
        }

        if (!staff.contains(s)) {
            System.out.println("Staff member not found.");
            return;
        }

        removeStaff(s);
        d.hireStaff(s);
    }

    /**
     * Returns whether this department has reached patient capacity.
     *
     * @return true if full, false otherwise
     */
    public boolean isFull() {
        return patients.size() >= capacity;
    }

    /**
     * Returns whether this department can accept the given patient.
     * The default rule only checks capacity.
     *
     * @param p patient being evaluated
     * @return true if the patient can be accepted
     */
    public boolean canAcceptPatient(Patient p) {
        return !isFull();
    }

    /**
     * Returns the specific subtype name of the department.
     *
     * @return department type
     */
    public abstract String getDepartmentType();


    /**
     * Returns a readable summary of this department's current state.
     *
     * @return formatted department information
     */
    @Override
    public String toString() {
        return "Department Information:\n"
                + "ID: " + deptId + "\n"
                + "Name: " + name + "\n"
                + "Capacity: " + capacity + "\n"
                + "Current Patients: " + patients.size() + "\n"
                + "Staff Count: " + staff.size();
    }
}