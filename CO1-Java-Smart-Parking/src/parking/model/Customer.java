package parking.model;

public abstract class Customer {
    private final String customerId;
    private final String name;

    protected Customer(String customerId, String name) {
        if (customerId == null || customerId.isBlank() || name == null || name.isBlank())
            throw new IllegalArgumentException("Customer ID and name are required.");
        this.customerId = customerId.trim();
        this.name = name.trim();
    }
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public boolean hasFreeParking() { return false; }
}
