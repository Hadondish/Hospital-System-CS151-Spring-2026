package hospital;

public class FamilyCareDepartment extends Department {

    private int dailyAppointmentLimit;

    public FamilyCareDepartment(String deptId, String name, int capacity, int dailyAppointmentLimit) {
        super(deptId, name, capacity);
        this.dailyAppointmentLimit = dailyAppointmentLimit;
    }

    public int getDailyAppointmentLimit() {
        return dailyAppointmentLimit;
    }

    public void setDailyAppointmentLimit(int dailyAppointmentLimit) {
        this.dailyAppointmentLimit = dailyAppointmentLimit;
    }

    public void bookCheckup(Appointment a) {
        if (a == null) {
            System.out.println("Appointment is null.");
            return;
        }

        if (dailyAppointmentLimit <= 0) {
            System.out.println("No appointments can be booked today.");
            return;
        }

        System.out.println("Checkup Booked For Appointment: " + a.getDateTime());
    }

    @Override
    public boolean canAcceptPatient(Patient p) {
        return !isFull();
    }

    @Override
    public String getDepartmentType() {
        return "FamilyCare";
    }
}
