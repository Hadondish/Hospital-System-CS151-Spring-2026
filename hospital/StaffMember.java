package hospital;

public class StaffMember {
    private String name;
    private String employeeId;
    private String role;
    private Department department;
    private boolean isEmployed;

    public StaffMember(String employeeId, String role) {
        this.employeeId = employeeId;
        this.role = role;
        this.isEmployed = true;
    }

    public String getName() { return name; }
    public String getEmployeeId() { return employeeId; }
    public String getRole() { return role; }
    public String getDepartment() { return department == null ? "" : department.getDepartmentType(); }
    public boolean isEmployed() { return isEmployed; }
    public void setName(String name) { this.name = name; }
    public void setRole(String role) { this.role = role; }
    public void setDepartment(Department department) { this.department = department; }
    

    public void assignToDepartment(Department dept) throws NotFoundException, FullCapacityException {
        if (this.department != null) {
            throw new IllegalStateException("Staff member is already assigned to a department.");
        }
        dept.hireStaff(this);
        this.department = dept;
    }

    public void removeFromDepartment() {
        if (this.department == null) {
            throw new IllegalStateException("Staff member is not currently assigned to any department.");
        }
        this.department.removeStaff(this);
        this.department = null;
    }

    public void terminateEmployment() {
        this.isEmployed = false;
        getDepartment().removeStaff(this);
    }

    public void changeRole(String newRole) {
        this.role = newRole;
    }

    @Override
    public String toString() { return employeeId + " - " + name + " - " + role; }
}
