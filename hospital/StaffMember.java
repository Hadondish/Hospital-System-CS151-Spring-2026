package hospital;

public class StaffMember {
    private String employeeId;
    private String role;
    private Department department;
    private boolean isEmployed;

    public StaffMember(String employeeId, String role) {
        this.employeeId = employeeId;
        this.role = role;
        this.isEmployed = true;
    }

    public void assignToDepartment(Department dept) throws FullCapacityException {}
    public void removeFromDepartment() {}
    public void terminateEmployment() {}
    public void changeRole(String newRole) {}

    @Override
    public String toString() { return employeeId; }
}
