import java.util.ArrayList;

/**
 * Strategy pattern
 * Concrete strategy class
 * A receipt formatter that shows all reservations made by that customer
 * @author Sarah Mai, Ngan Nguyen
 *
 */
public class ComprehensiveFormatter extends Formatter{
	/**
	 * format the body(content) of the receipt
	 * concatenate all guestReservations to display it 
	 */
	public String formatReservation(ArrayList<Reservation> reservations)
    {
    	if(reservations !=null){
    	String s = "";
    		for(Reservation r : reservations){
    			total += r.getTotal();
    			s+= r.toString() + "\n\n";
    		}
    		
    		return s;
    	}
    	
        return "\nNo Reservation\n";
    }
}
