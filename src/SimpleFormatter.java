/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sarahmai
 */
public class SimpleFormatter implements ReceiptFormatter
{
    //how many rooms booked total
    private double total;
    
    public String formatHeader()
    {
        total = 0;
        return "Simple Receipt";
    }
    
    public String formatReservation(Reservation r)
    {
        total += r.getTotal();
        return (String.format( "Room Number: %s\n Durration: %s\nTotal: $%.2f\n"
                ,r.getRoom(),r.getDateInterval(),r.getTotal()));
    }
    
    public String formatFooter()
    {
        return (String.format("\n\nTOTAL DUE: $%.2f\n", total));
    }
    
    /**public String printSimpleReceipt(ReceiptFormatter rf)
    {
        rf.formatHeader();
        rf.formatReservation();
        rf.formatFooter();
        
        //
        System.out.print("Room Number: ",);
        System.out.print("%4s%.2f", "\nTotal: $",);
    }*/
}
