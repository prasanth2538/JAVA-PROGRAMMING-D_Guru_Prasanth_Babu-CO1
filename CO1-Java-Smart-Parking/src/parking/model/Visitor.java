package parking.model;

import java.time.LocalDate;

public class Visitor extends Customer {
    private final LocalDate oneDayPassDate;
    public Visitor(String id, String name, LocalDate oneDayPassDate) {
        super(id, name); this.oneDayPassDate = oneDayPassDate;
    }
    public boolean hasValidOneDayPass(LocalDate date) {
        return oneDayPassDate != null && oneDayPassDate.equals(date);
    }
}
