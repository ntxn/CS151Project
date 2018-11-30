import java.util.ArrayList;
/**
 * Strategy pattern
 * concrete strategy for a simple version of formatter
 * which display only reservation booked on the current date
 * @author Sarah Mai, Ngan Nguyen
 */
public class SimpleFormatter extends Formatter
{
	/**
	 * The reservation is sent in as an ArrayList but only
	 * has 1 element, if there's a booking
	 */
    public String formatReservation(ArrayList<Reservation> reservations)
    {
    	if(reservations.size() == 0)
    		return "\nNo Booking Today\n";
    	
		Reservation r = reservations.get(0);
		total = r.getTotal();
		return r.toString();
    }
}
