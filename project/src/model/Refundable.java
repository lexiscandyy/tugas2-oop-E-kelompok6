package model;

public interface Refundable {
    public double calculateRefund(int daysBeforeEvent);
    public boolean isRefundable();
}
