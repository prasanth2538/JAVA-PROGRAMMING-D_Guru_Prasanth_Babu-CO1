package parking.model;

public interface Chargeable {
    double chargingFee(int kWh);
    default String chargingMessage() { return "EV charging service"; }
    static double standardRatePerKWh() { return 12.0; }
}
