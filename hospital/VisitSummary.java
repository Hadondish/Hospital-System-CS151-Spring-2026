package hospital;

import java.util.*;

public class VisitSummary {
    private String visitId;
    private List<Billable> charges;
    private String patientId;

    public VisitSummary(String visitId, String patientId) {
        this.visitId = visitId;
        this.patientId = patientId;
        this.charges = new ArrayList<>();
    }

    public void addCharge(Billable item) {}
    public boolean removeCharge(Billable item) { return false; }
    public String printReceipt() { return ""; }
    public int getTotalCost() { return 0; }
    public void finalizeSummary() {}

    @Override
    public String toString() { return visitId; }
}


