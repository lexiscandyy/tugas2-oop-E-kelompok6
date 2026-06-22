package model;

public class Ticket {
    private String id;
    public String eventId;
    public String userId;
    private String category;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    private String purchaseDate;
    private String status;
    private double refundAmount;

    public String getId(){
        return id;
    }

    public String getEventId(){
        return eventId;
    }

    public String getUserId(){
        return userId;
    }

    public String getCategory(){
        return category;
    }

    public int getQuantity(){
        return quantity;
    }

    public double getUnitPrice(){
        return unitPrice;
    }

    public double getTotalPrice(){
        return totalPrice;
    }

    public String getPurchaseDate(){
        return purchaseDate;
    }

    public String getStatus(){
        return status;
    }
}
