/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import javax.swing.event.*;

/**
 *
 * @author sarahmai
 */
public class Receipt 
{
    private ArrayList<Reservation> reserve;
    private ArrayList<ChangeListener> listeners;

    public Receipt()
    {
        reserve = new ArrayList<>();
        listeners = new ArrayList<>();
    }
    
    public void addItem(Reservation item)
    {
        reserve.add(item);
        /**
         * 
         */
        ChangeEvent event = new ChangeEvent(this);
        for(ChangeListener listener : listeners)
        {
            listener.stateChanged(event);
        }
    }
    
    public void addChangeListener(ChangeListener listener)
    {
        listeners.add(listener);
    }
    
    public Iterator<Reservation> getRooms()
    {
        return new
            Iterator<Reservation>()
            {
                private int current = 0;
                
                public boolean hasNext()
                {
                    return current < reserve.size();
                }
                
                public Reservation next()
                {
                    return reserve.get(current++);
                }
                
                public void remove()
                {
                    throw new UnsupportedOperationException();
                }
            };
    }

    public String format(ReceiptFormatter formatter)
    {
        String r = formatter.formatHeader();
        Iterator<Reservation> rs = getRooms(); // returns an iterator of this invoice. 
        while(rs.hasNext())
        {
            r += formatter.formatReservation(rs.next());
        } 
        return r + formatter.formatFooter();
    }
}
