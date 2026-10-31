package parking.model;

import java.time.LocalDate;

public class Staff extends Customer {
    private final LocalDate monthlyPassExpiry;
    public Staff(String id, String name, LocalDate monthlyPassExpiry) {
        super(id, name); this.monthlyPassExpiry = monthlyPassExpiry;
    }
    @Override public boolean hasFreeParking() {
        return monthlyPassExpiry != null && !monthlyPassExpiry.isBefore(LocalDate.now());
    }
}
