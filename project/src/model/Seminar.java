package model;

public class Seminar extends Event implements Refundable{

    @Override
    public double calculateRefund(int daysBeforeEvent) {
        // rumus dan aturan refund
        return .00;
    }

    @Override
    public double calculateTicketPrice(String category) {
        return 0;
    }
}
