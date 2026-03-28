// Kevin Tran
// Billable Interface.
package hospital;
/* Contract for items billed to hospital patients */

public interface Billable {
    // Total cost 
    int getCost();

    // base amount before total cost
    String getBaseCost();

    String getCode();

    // True if covered by insurance, False if not covered by insurance, 
    boolean isCoveredByInsurance();
}
