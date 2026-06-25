package exception;

public class TicketSoldOutException extends RuntimeException{
    public TicketSoldOutException(String msg){
        super(msg);
    }
}
