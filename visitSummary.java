// Kevin Tran
// visitSummary
import java.util.ArrayList;
import java.util.List;


/*
    Visit Summary shows the details about a patient's specific hospital visit and the billable costs
    
*/

public class visitSummary {
    
    // Declare Variables
    private String visitId;

    private String patientId;
    
    private List<billable> billable_charges;


public visitSummary(String visitId, String patient) {
    this.visitId = visitId;
    this.patientId = patientId;
    this.billable_charges = new ArrayList<>();

    }

public void addCharge(billable item) {
    if(item != null) {
        billable_charges.add(item);
    }
}

public boolean removeCharge(billable bill) {
    return billable_charges.remove(bill);
}

// Gets the total cost
public int getTotalCost() {

    int total_cost = 0;
    for (billable item : billable_charges) {
        total_cost += item.getCost();
    }
    return total_cost;
}

void finalizeSummary() {

}
// String Representation of visit summary
public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("Visit ID: ").append(visitId).append("\n");
    sb.append("Patient ID: ").append(patientId).append("\n");
    sb.append("Billable Charges: \n");

    for(billable b: billable_charges) {
        sb.append(" -").append(b.getBaseCost()).append("\n");

    }

    sb.append("Total Costs: $").append(getTotalCost());

    return sb.toString();
}
}