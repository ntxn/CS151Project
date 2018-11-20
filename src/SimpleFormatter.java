import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sarahmai
 */
public class SimpleFormatter extends Formatter
{
    //how many rooms booked total
    private double total;
    /*
    public String formatHeader()
    {
        total = 0;
        return "Simple Receipt";
    }
    */
    
    public String formatReservation(ArrayList<Reservation> reservations)
    {
    	if(reservations !=null){
    		// Search through the reservations arrayList to see which reservation 
    		// has the latest bookingDate
    		Reservation r = reservations.get(reservations.size()-1);
    		total = r.getTotal();
    		return r.toString();
    	}
    	
        return "No Reservation";
    }
    
    public String formatFooter()
    {
        return (String.format("\n\nTOTAL DUE: $%.2f\n", total));
    }
}
