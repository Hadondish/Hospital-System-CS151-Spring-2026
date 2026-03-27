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

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public Map<String, Integer> getMedPriceByName() {
        return medPriceByName;
    }

    public void setMedPriceByName(Map<String, Integer> medPriceByName) {
        this.medPriceByName = medPriceByName;
    }

    public void addMedication(Medication m) {
        if (m == null) {
            System.out.println("Medication is null");
            return;
        }

        medications.add(m);
        medPriceByName.put(m.getDescription(), m.getCost());
        System.out.println("Medication Added: " + m.getDescription());
    }

    public void removeMedication(Medication m) {
        if (m == null) {
            System.out.println("Medication is null");
            return;
        }

        medications.remove(m);
        medPriceByName.remove(m.getDescription(), m.getCost());
        System.out.println("Medication Removed: " + m.getDescription());
    }

    public String listMedications() {
        if (medications.isEmpty()) {
            return "No Medications Available";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < medications.size(); i++) {
            Medication m = medications.get(i);
            sb.append(m.getDescription())
                    .append(" ($")
                    .append(m.getCost())
                    .append(")");
            // Add comma and space for all but the last item
            if (i < medications.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public void fillPrescription(String patientId, Medication medName) {
        if (patientId == null || medName == null) {
            System.out.println("Patient ID or medication is null.");
            return;
        }

        if (medications.contains(medName)) {
            System.out.println("Filling prescription: " + medName.getDescription() + " --> " + patientId);
        } else {
            System.out.println("Medication not available: " + medName.getDescription());
        }
    }

    public void restockMedication(Medication medName, int amount) {
        if (medName == null) {
            System.out.println("Medication is null.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Invalid restock amount.");
            return;
        }

        if (!medications.contains(medName)) {
            medications.add(medName);
        }

        medPriceByName.put(medName.getDescription(), medName.getCost());
        System.out.println("Restocked: " + medName.getDescription() + " by " + amount + " amount");
    }

    @Override
    public String getDepartmentType() {
        return "Pharmacy";
    }
}
