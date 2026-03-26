package hospital;

import java.util.List;

public class SurgeryDepartment extends Department {

    private int operatingRooms;

    public SurgeryDepartment(String deptId, String name, int capacity, int operatingRooms) {
        super(deptId, name, capacity);
        this.operatingRooms = operatingRooms;
    }

    public void administerDrugs(Patient p, Medication m) {}

    public void generateBill(Patient p) {}

    public void completeSurgery(Patient p) {}

    public void assignOperatingRoom(Appointment a) {}

    @Override
    public String getDepartmentType() {
        return "Surgery";
    }
}
