package hospital;

import java.util.*;

public class SurgeryBill implements Billable {
    private Map<String, Integer> nameToFee;
    private String procedureName;
    private int surgeonFee;
    private int anesthesiaFee;
    private boolean anesthesia;

    public SurgeryBill(String procedureName, int surgeonFee, int anesthesiaFee, boolean anesthesia) {
        this.procedureName = procedureName;
        this.surgeonFee = surgeonFee;
        this.anesthesiaFee = anesthesiaFee;
        this.anesthesia = anesthesia;
        this.nameToFee = new HashMap<>();
    }

    @Override
    public int getCost() {
        return surgeonFee + (anesthesia ? anesthesiaFee : 0);
    }

    @Override
    public String getDescription() { return procedureName; }

    @Override
    public String getCode() { return procedureName.toUpperCase().replace(" ", "_"); }

    @Override
    public boolean isCoveredByInsurance() { return false; }
}
