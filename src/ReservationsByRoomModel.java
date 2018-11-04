import java.util.ArrayList;

import javax.swing.event.*;

public class ReservationsByRoomModel {
	ArrayList<ChangeListener> listeners;		// Data structure to hold listeners
	ArrayList<Reservation> all_reservations;
	ArrayList<Reservation> reservationsByRoom; 	// Data structure to hold data
	
	public ReservationsByRoomModel(ArrayList<Reservation> all_reservations){
		listeners = new ArrayList<ChangeListener>();
		this.all_reservations = all_reservations;
	}

	// Attach listener
	public void addChangeListener(ChangeListener l){
		listeners.add(l);
	}
	
	// Accessor 
	public ArrayList<Reservation> getReservationsByRoom(){
		return reservationsByRoom;
	}
	
	// Mutator
	public void setReservationsByRoom(int room_number){
		reservationsByRoom = new ArrayList<Reservation>();
		for(int i=0; i<all_reservations.size(); i++){
			if(all_reservations.get(i).getRoom_number() == room_number){
				reservationsByRoom.add(all_reservations.get(i));
			}
		}
		
		// Notify observers of the change
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener listener : listeners){
			listener.stateChanged(event);
		}
	}
	/*
	public ArrayList<Integer> getBookedRoomNumbers(DateInterval period){
		ArrayList<Integer> bookedRoomNumbers = new ArrayList<Integer>();
		
		for(int i=0; i<all_reservations.size(); i++){
			if(all_reservations.get(i).getDateInterval().isConflicting(period))
				bookedRoomNumbers.add(all_reservations.get(i).getRoom_number());
		}
		
		return bookedRoomNumbers;
	}
	*/
	
}
