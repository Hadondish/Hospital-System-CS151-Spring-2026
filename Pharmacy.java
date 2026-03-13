package hospital;

import java.util.*;

public class Pharmacy extends Department {
    private List<Medication> medications;
    private Map<String, Integer> medPriceByName;

    public Pharmacy(String deptId, String name, int capacity) {
        super(deptId, name, capacity);
        this.medications = new ArrayList<>();
        this.medPriceByName = new HashMap<>();
    }

    public void addMedication(Medication m) {}
    public void removeMedication(Medication m) {}
    public String listMedications() { return ""; }
    public void fillPrescription(String patientId, Medication medName) {}
    public void restockMedication(Medication medName, int amount) {}

    @Override
    public String getDepartmentType() { return "Pharmacy"; }
}
