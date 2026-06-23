package model;

public abstract class Event {
    private String id;
    private String type;
    private String name;
    private String venueId;
    private String organizerId;
    private String date;
    private double basePrice;
    private String createdAt;

    public Event() {}

    public Event(String id, String type, String name, String venueId,
                 String organizerId, String date, double basePrice, String createdAt) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.venueId = venueId;
        this.organizerId = organizerId;
        this.date = date;
        this.basePrice = basePrice;
        this.createdAt = createdAt;
    }

    // Abstract method - wajib di-override tiap subclass
    public abstract double calculateTicketPrice(String category);

    // Getters
    public String getId() { return id; }
    public String getType() { return type; }
    public String getName() { return name; }
    public String getVenueId() { return venueId; }
    public String getOrganizerId() { return organizerId; }
    public String getDate() { return date; }
    public double getBasePrice() { return basePrice; }
    public String getCreatedAt() { return createdAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setType(String type) { this.type = type; }
    public void setName(String name) { this.name = name; }
    public void setVenueId(String venueId) { this.venueId = venueId; }
    public void setOrganizerId(String organizerId) { this.organizerId = organizerId; }
    public void setDate(String date) { this.date = date; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}