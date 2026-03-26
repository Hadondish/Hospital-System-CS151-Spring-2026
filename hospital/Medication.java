// Kevin Tran
// Medication.java
package hospital;

/* Medication class is used to get information about medications such as medication names, medication costs, and whether insurance is provided. */
public class Medication implements Billable {
    // Declare variables
    private String medicationName;

    private int unitPrice;

    private int quantity;

    //Constructor for a new Medication
    public Medication(String medicationName, int unitPrice, int quantity) {
        this.medicationName = medicationName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    // Get the total cost
    public int getCost() {
        return unitPrice * quantity;
    }

    // Get the Description
    public String getDescription() {
        return "Medication " + medicationName + " | Quantity: "  + quantity;
    }
    // From billable.java contract
    public String getCode() {
        return medicationName.toUpperCase();
    }

    public String getBaseCost() {
        return "Medication: " + medicationName + "| Total: $" + getCost();
    }

    public boolean isCoveredByInsurance() {
        return true;
    }

}