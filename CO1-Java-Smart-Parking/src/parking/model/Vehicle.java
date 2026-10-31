package parking.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public abstract class Vehicle {
    private final String regNo;

    protected Vehicle(String regNo) {
        if (regNo == null || regNo.isBlank()) throw new IllegalArgumentException("Registration number is required.");
        this.regNo = regNo.trim().toUpperCase();
    }

    public String getRegNo() { return regNo; }
    public abstract double ratePerHour();

    // Every started hour is charged in full. Zero minutes costs zero.
    public double calculateFee(long minutes) {
        if (minutes < 0) throw new IllegalArgumentException("Duration cannot be negative.");
        long hours = (minutes + 59) / 60; // ceiling division using integer arithmetic
        return hours * ratePerHour();
    }

    public double calculateFee(LocalDateTime in, LocalDateTime out) {
        if (in == null || out == null || out.isBefore(in))
            throw new IllegalArgumentException("Invalid entry/exit time.");
        long minutes = ChronoUnit.MINUTES.between(in, out);
        return calculateFee(minutes);
    }

    public boolean needsChargingSlot() { return false; }

    @Override public boolean equals(Object obj) {
        return obj instanceof Vehicle other && regNo.equals(other.regNo);
    }
    @Override public int hashCode() { return Objects.hash(regNo); }
    @Override public String toString() { return getClass().getSimpleName() + "[" + regNo + "]"; }
}
