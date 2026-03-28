package hospital;

public class SurgeryDepartment extends Department {

    private int operatingRooms;

    public SurgeryDepartment(String deptId, String name, int capacity, int operatingRooms) {
        super(deptId, name, capacity);
        this.operatingRooms = operatingRooms;
    }

    public int getOperatingRooms() {
        return operatingRooms;
    }

    public void setOperatingRooms(int operatingRooms) {
        this.operatingRooms = operatingRooms;
    }

    public void administerDrugs(Patient p, Medication m) {
        if (p == null || m == null) {
            System.out.println("Patient or medication is null.");
            return;
        }

        System.out.println("Administering " + m.getDescription() + " to patient " + p.getPatientId());
    }

    public void generateBill(Patient p) {
        if (p == null) {
            System.out.println("Patient is null.");
            return;
        }

        System.out.println("Generating surgery bill for patient " + p.getPatientId());
    }

    public void completeSurgery(Patient p) {
        if (p == null) {
            System.out.println("Patient is null.");
            return;
        }

        System.out.println("Surgery completed for patient " + p.getPatientId());
    }

    public void assignOperatingRoom(Appointment a) {
        if (a == null) {
            System.out.println("Appointment is null.");
            return;
        }

        if (operatingRooms <= 0) {
            System.out.println("No operating rooms available.");
            return;
        }

        System.out.println("Assigned operating room for appointment at " + a.getDateTime());
    }

    @Override
    public String getDepartmentType() {
        return "Surgery";
    }
}
