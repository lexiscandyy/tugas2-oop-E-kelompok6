package model;

public class SportMatch extends Event {

    public SportMatch() {}

    public SportMatch(String id, String name, String venueId, String organizerId,
                      String date, double basePrice, String createdAt) {
        super(id, "sport_match", name, venueId, organizerId, date, basePrice, createdAt);
    }

    @Override
    public double calculateTicketPrice(String category) {
        switch (category.toLowerCase()) {
            case "vip":  return getBasePrice() * 2.5;
            case "vvip": return getBasePrice() * 5.0;
            case "tribune":
            default:     return getBasePrice() * 1.0;
        }
    }
}