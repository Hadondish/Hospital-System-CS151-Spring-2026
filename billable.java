// Kevin Tran
// Billable Interface.

/* Contract for items billed to hospital patients */

public interface billable {
    // Total cost 
    int getCost();

    // base amount before total cost
    String getBaseCost();

    // True if covered by insurance, False if not covered by insurance, 
    boolean isCoveredByInsurance();
}
