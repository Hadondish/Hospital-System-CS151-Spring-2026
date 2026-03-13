package hospital;

public class FamilyCareDepartment extends Department {
    private int dailyAppointmentLimit;

    public FamilyCareDepartment(String deptId, String name, int capacity, int dailyAppointmentLimit) {
        super(deptId, name, capacity);
        this.dailyAppointmentLimit = dailyAppointmentLimit;
    }

    public void bookCheckup(Appointment a) {}

    @Override
    public boolean canAcceptPatient(Patient p) { return true; }

    @Override
    public String getDepartmentType() { return "FamilyCare"; }
}
