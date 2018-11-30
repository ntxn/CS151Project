import java.util.ArrayList;

/**
 * Strategy pattern
 * Interface strategy for receipt formatter
 * @author Sarah Mai
 */
public interface ReceiptFormatter
{
    String formatHeader();
    String formatFooter();
    String formatReservation(ArrayList<Reservation> reservations);
}
