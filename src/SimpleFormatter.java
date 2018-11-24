import java.time.LocalDate;
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
    
    public String formatReservation(ArrayList<Reservation> reservations)
    {
    	if(reservations.size() == 0)
    		return "You did not book anything today";
    	
		Reservation r = reservations.get(0);
		total = r.getTotal();
		return r.toString();
    }
    
    public String formatFooter()
    {
        return (String.format("\n\nTOTAL DUE: $%.2f\n", total));
    }
}
