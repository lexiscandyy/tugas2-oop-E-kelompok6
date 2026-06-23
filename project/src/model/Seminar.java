package model;

public class Seminar extends Event implements Refundable {

    public Seminar() {}

    public Seminar(String id, String name, String venueId, String organizerId,
                   String date, double basePrice, String createdAt) {
        super(id, "seminar", name, venueId, organizerId, date, basePrice, createdAt);
    }

    @Override
    public double calculateTicketPrice(String category) {
        // Flat price, semua kategori sama
        return getBasePrice();
    }

    @Override
    public double calculateRefund(int daysBeforeEvent) {
        if (daysBeforeEvent > 1) return 1.0;  // 100%
        else                     return 0.0;  // 0%
    }

    @Override
    public boolean isRefundable() {
        return true;
    }
}