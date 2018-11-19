import java.util.ArrayList;

import javax.swing.event.*;

public class ReservationsByRoomModel {
	private ArrayList<ChangeListener> listeners;		// Data structure to hold listeners
	private ArrayList<Reservation> all_reservations;
	private ArrayList<Reservation> reservationsByRoom; 	// Data structure to hold data
	
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
			if(all_reservations.get(i).getRoom().getRoom_number() == room_number){
				addReservation(all_reservations.get(i));
			}
		}
		
		// Notify observers of the change
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener listener : listeners){
			listener.stateChanged(event);
		}
	}
	
	/**
	 * 
	 * @param r
	 */
	private void addReservation(Reservation r){
		int size = reservationsByRoom.size();
		if(size == 0){
			reservationsByRoom.add(r);
			return;
		}
		
		int i=0;
		while(i<size && r.getDateInterval().isAfter(
				reservationsByRoom.get(i).getDateInterval()))
			i++;
		reservationsByRoom.add(i, r);
	}
}
