package hospital;

public interface Billable {
    int getCost();
    String getDescription();
    String getCode();
    boolean isCoveredByInsurance();
}
