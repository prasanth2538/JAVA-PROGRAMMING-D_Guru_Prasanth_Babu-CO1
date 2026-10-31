package parking.model;

import java.time.LocalDateTime;

public class DeliveryVan extends Vehicle {
    public DeliveryVan(String regNo) { super(regNo); }
    @Override public double ratePerHour() { return 30.0; } // base car rate

    // Double the car rate for each started hour beginning between 09:00 and 16:59.
    @Override public double calculateFee(LocalDateTime in, LocalDateTime out) {
        if (in == null || out == null || out.isBefore(in))
            throw new IllegalArgumentException("Invalid entry/exit time.");
        long minutes = java.time.Duration.between(in, out).toMinutes();
        long hours = (minutes + 59) / 60;
        double total = 0;
        for (long i = 0; i < hours; i++) {
            LocalDateTime billHour = in.plusHours(i);
            int hour = billHour.getHour();
            total += (hour >= 9 && hour < 17) ? 60.0 : 30.0;
        }
        return total;
    }
}
