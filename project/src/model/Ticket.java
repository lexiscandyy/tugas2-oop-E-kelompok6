package model;

public class Ticket {
    private String id;
    private String category;
    private int quantity;
    private String unitPrice;
    private String totalPrice;
    private String purchaseDate;
    private String status;

    public String getId(){
        return id;
    }

    public String getCategory(){
        return category;
    }

    public int getQuantity(){
        return quantity;
    }

    public String getUnitPrice(){
        return unitPrice;
    }

    public String getTotalPrice(){
        return totalPrice;
    }

    public String getPurchaseDate(){
        return purchaseDate;
    }

    public String getStatus(){
        return status;
    }
}
