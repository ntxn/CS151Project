import java.util.ArrayList;
/**
 *
 * @author Sarah Mai, Ngan Nguyen
 */
public class SimpleFormatter extends Formatter
{
    public String formatReservation(ArrayList<Reservation> reservations)
    {
    	if(reservations.size() == 0)
    		return "\nNo Booking Today\n";
    	
		Reservation r = reservations.get(0);
		total = r.getTotal();
		return r.toString();
    }
}
