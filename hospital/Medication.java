package hospital;

public class Medication implements Billable {
    private String medName;
    private int unitPrice;
    private int quantity;

    public Medication(String medName, int unitPrice, int quantity) {
        this.medName = medName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    @Override
    public int getCost() { return unitPrice * quantity; }

    @Override
    public String getDescription() { return medName; }

    @Override
    public String getCode() { return medName.toUpperCase().replace(" ", "_"); }

    @Override
    public boolean isCoveredByInsurance() { return false; }
}
