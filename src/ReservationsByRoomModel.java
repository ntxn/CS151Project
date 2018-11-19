import java.util.ArrayList;

import javax.swing.event.*;

/**
 * DATA MODEL for MVC View Reservations by Room (for a manager)
 * @author Ngan Nguyen
 *
 */
public class ReservationsByRoomModel {
	private ArrayList<ChangeListener> listeners;		// Data structure to hold listeners
	private ArrayList<Reservation> all_reservations;	// ALL reservations
	private ArrayList<Reservation> reservationsByRoom; 	// Data structure to hold data
														// to display reservations by room
	
	/**
	 * Contructor to initialize variables
	 * @param all_reservations
	 */
	public ReservationsByRoomModel(ArrayList<Reservation> all_reservations){
		listeners = new ArrayList<ChangeListener>();
		this.all_reservations = all_reservations;
	}

	/**
	 * Attach ChangeListener to this data model
	 * @param l : ChangeListener
	 */
	public void addChangeListener(ChangeListener l){
		listeners.add(l);
	}

	/**
	 * Accessor: get reservations by room (main data for this MODEL)
	 * @return ArrayList<Reservation>
	 */
	public ArrayList<Reservation> getReservationsByRoom(){
		return reservationsByRoom;
	}
	
	/**
	 * Mutator: get reservationsByRoom based on which room_number is requested
	 * Then will notify the listener
	 * @param room_number
	 */
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
	 * Add a reservation to ArrayList reservationsByRoom by date order
	 * @param a Reservation object
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
