package hospital;

public class StaffMember {

    // Attributes

    private String name;
    private String employeeId;
    private String role;
    private Department department;
    private boolean isEmployed;

    // Constructors

    public StaffMember(String employeeId, String role) {
        this.employeeId = employeeId;
        this.role = role;
        this.isEmployed = true;
    }

    // Getters and Setters

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public String getRole() { return role; }
    public String getDepartment() { return department == null ? "" : department.getDepartmentType(); }
    public void setDepartment(Department department) { this.department = department; }
    public boolean isEmployed() { return isEmployed; }
    public void setIsEmployed(boolean isEmployed) { this.isEmployed = isEmployed; }

    // Methods
    
    // Assigns the staff member to a department, ensuring they are not already assigned and that the department has capacity
    public void assignToDepartment(Department dept) throws NotFoundException, FullCapacityException {
        if (this.department != null) {
            throw new IllegalStateException("Staff member is already assigned to a department.");
        }
        dept.hireStaff(this);
        setDepartment(dept);
    }

    // Removes the staff member from their current department, ensuring they are currently assigned to one
    public void removeFromDepartment() {
        if (this.department == null) {
            throw new IllegalStateException("Staff member is not currently assigned to any department.");
        }
        this.department.removeStaff(this);
        setDepartment(null);
    }

    // Terminates the staff member's employment, setting their employment status to false
    public void terminateEmployment() {
        this.isEmployed = false;
    }

    // Changes the staff member's role to a new role
    public void changeRole(String newRole) {
        this.role = newRole;
    }

    // Returns a string representation of the staff member, including their employee ID, name, and role
    @Override
    public String toString() { return employeeId + " - " + name + " - " + role; }
}
