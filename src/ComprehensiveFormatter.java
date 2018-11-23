import java.util.ArrayList;

public class ComprehensiveFormatter extends Formatter{

	//how many rooms booked total
    private double total;
    
    public String formatReservation(ArrayList<Reservation> reservations)
    {
    	if(reservations !=null){ System.out.println(reservations.size());
    	String s = "";
    		for(Reservation r : reservations){
    			total += r.getTotal();
    			s+= r.toString() + "\n\n";
    		}
    		
    		return s;
    	}
    	
        return "No Reservation";
    }
    
    public String formatFooter()
    {
        return (String.format("\n\nTOTAL DUE: $%.2f\n", total));
    }

}
