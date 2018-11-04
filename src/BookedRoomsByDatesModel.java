import java.util.ArrayList;

import javax.swing.event.*;

public class BookedRoomsByDatesModel {
	ArrayList<ChangeListener> listeners;		// Data structure to hold listeners
	ArrayList<Reservation> all_reservations;
	ArrayList<Integer> room_numbers; 			// Data structure to hold data
	
	public BookedRoomsByDatesModel(ArrayList<Reservation> all_reservations){
		listeners = new ArrayList<ChangeListener>();
		this.all_reservations = all_reservations;
	}

	// Attach listener
	public void addChangeListener(ChangeListener l){
		listeners.add(l);
	}
	
	// Accessor 
	public ArrayList<Integer> getRoom_numbers(){
		return room_numbers;
	}
	
	// Mutator
	public void getBookedRoomNumbers(DateInterval period){
		room_numbers = new ArrayList<Integer>();
		
		for(int i=0; i<all_reservations.size(); i++){
			if(all_reservations.get(i).getDateInterval().isConflicting(period))
				room_numbers.add(all_reservations.get(i).getRoom_number());
		}
		
		// Notify observers of the change
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener listener : listeners){
			listener.stateChanged(event);
		}
		
	}
}
