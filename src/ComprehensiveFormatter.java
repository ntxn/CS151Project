import java.util.ArrayList;

public class ComprehensiveFormatter extends Formatter{
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
