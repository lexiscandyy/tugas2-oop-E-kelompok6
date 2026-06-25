package model;

public class Venue {
    private String id;
    private String name;
    private String address;
    private int maxCapacity;
    private String createdAt;

    public Venue(String id, String name, String address, int maxCapacity, String createdAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.maxCapacity = maxCapacity;
        this.createdAt = createdAt;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public int getMaxCapacity() { return maxCapacity; }
    public String getCreatedAt() { return createdAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString(){
        return "Venue{" +
                "id ='" + id + '\'' +
                ", name ='" + name + '\'' +
                ", address ='" + address + '\'' +
                ", maxCapacity ='" + maxCapacity + '\'' +
                '}';
    }
}