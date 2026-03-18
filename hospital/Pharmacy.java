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

    public void addMedication(Medication m) {
        medications.add(m);
        medPriceByName.put(m.getDescription(), m.getCost());
        System.out.println("Medication Added: " + m.getDescription());
    }

    public void removeMedication(Medication m) {
        medications.remove(m);
        medPriceByName.remove(m.getDescription(), m.getCost());
        System.out.println("Medication Removed: " + m.getDescription());
    }

    public String listMedications() {
        if (medications.isEmpty()) {
            return "No Medication Available";
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
        System.out.println("Filling prescription: " + medName.getDescription() + " to -> " + patientId);
    }

    public void restockMedication(Medication medName, int amount) {
        System.out.println("Restocked: " + medName.getDescription() + " by " + amount + " amount");
    }

    @Override
    public String getDepartmentType() {
        return "Pharmacy";
    }
}
