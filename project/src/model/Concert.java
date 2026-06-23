package model;

public class Concert extends Event implements Refundable {

    public Concert() {}

    public Concert(String id, String name, String venueId, String organizerId,
                   String date, double basePrice, String createdAt) {
        super(id, "concert", name, venueId, organizerId, date, basePrice, createdAt);
    }

    @Override
    public double calculateTicketPrice(String category) {
        switch (category.toLowerCase()) {
            case "vip":      return getBasePrice() * 3.0;
            case "festival": return getBasePrice() * 0.7;
            case "regular":
            default:         return getBasePrice() * 1.0;
        }
    }

    @Override
    public double calculateRefund(int daysBeforeEvent) {
        if (daysBeforeEvent > 14)      return 1.0;  // 100%
        else if (daysBeforeEvent >= 7) return 0.5;  // 50%
        else                           return 0.0;  // 0%
    }

    @Override
    public boolean isRefundable() {
        return true;
    }
}