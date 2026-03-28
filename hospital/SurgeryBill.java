// Kevin Tran
// surgeryBill.java
package hospital;
/* Surgery bill is connected and is used for calculation for surgery bills and has support for anesthesia */
import java.util.Map;

public class SurgeryBill implements Billable {
    // Declare surgeon names 
    private Map<String, Integer> surgeonNameCost;

    // Names of certain procedures in the surgery room, like heart transplant for example.
    private String procedure;

    private int surgeonFee;

    private boolean anesthesia;

    // If anesthesia was given during the surgery add to the fee
    private int anesthesiaFee;


public SurgeryBill(Map<String, Integer> surgeonNameCost, String procedure, int surgeonFee, int anesthesiaFee, boolean anesthesia) {
    this.procedure = procedure;
    this.surgeonNameCost = surgeonNameCost;
    this.anesthesiaFee = anesthesiaFee;
    this.anesthesia = anesthesia;
    this.surgeonFee = surgeonFee;

}

public void addNameToFee(String name, int fee) {
    surgeonNameCost.put(name, fee);
}

public int getCost() {
    int total = surgeonFee;

    // If the surgery contained anesthesia
    if (anesthesia) {
        total += anesthesiaFee;
    }

    return total;
    }

public String getDescription() {
    return "Type of Procedure: " + procedure + " | Anesthesia: " + anesthesia;
    }

// If they are covered with insurance
public boolean isCoveredByInsurance() {
    // Surgeries costing 15,000 dollars or less are able to get insurance
    return getCost() <= 15000;
    }

// Get Code
public String getCode() {
    return procedure.toUpperCase();
}

// Get the cost before the total amount
public String getBaseCost(){
    return "Surgery: " + procedure + " | Anesthesia: " + (anesthesia ? "Yes ($" + anesthesiaFee + ")" : "No") + " | Total Amount: $" + getCost();
}
}





    
    