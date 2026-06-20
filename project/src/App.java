import database.DatabaseManager;
import handler.EventHandler;
import handler.TicketHandler;
import handler.UserHandler;
import handler.VenueHandler;
import model.Venue;
import server.Server;

public class App {
    public static void main(String[] args) throws Exception {
        DatabaseManager.initialize();
        Server server = new Server(8080);

        EventHandler eventHandler = new EventHandler();
        TicketHandler ticketHandler = new TicketHandler();
        UserHandler userHandler = new UserHandler();
        VenueHandler venueHandler = new VenueHandler();
    }
}
