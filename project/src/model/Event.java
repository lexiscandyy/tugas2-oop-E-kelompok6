package model;

import java.time.LocalDate;
import java.util.Date;

public abstract class Event {
    private int id;
    private String type;
    private String name;
    private String venueId;
    private String organizerId;
    private String date;
    private double basePrice;

    public abstract double calculateTicketPrice(String category);

}
