import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.event.*;

public class BookedRoomsByDatesModel {
	private ArrayList<ChangeListener> listeners;		// Data structure to hold listeners
	private ArrayList<Reservation> all_reservations;
	private ArrayList<Room> allRoomsByType; 			// Data structure to hold data
	private ArrayList<Integer> availableRoomsByType;
	private DateInterval dateInterval;
	private Hashtable catagorizedRooms;
	private int roomType;
	private Guest currentGuest;
	private Hashtable rooms;
	
	public BookedRoomsByDatesModel(ArrayList<Reservation> all_reservations,
			Hashtable catagorizedRooms, Guest currentGuest, Hashtable rooms){
		listeners = new ArrayList<ChangeListener>();
		this.all_reservations = all_reservations;
		availableRoomsByType = new ArrayList<Integer>();
		this.catagorizedRooms = catagorizedRooms;
		this.currentGuest = currentGuest;
		this.rooms = rooms;
	}

	// Attach listener
	public void addChangeListener(ChangeListener l){
		listeners.add(l);
	}
	
	// Accessor 
	public ArrayList<Integer> getAvailableRoomsByType(){
		return availableRoomsByType;
	}
	
	
	
	// Mutator
	public void getAvailableRooms(DateInterval dateInterval, int roomType){
		this.dateInterval = dateInterval;
		this.roomType = roomType;
		Reservation reservation;
		availableRoomsByType.clear();
		Hashtable bookedRoom = new Hashtable();
		
		for(int i=0; i<all_reservations.size(); i++){
			reservation = all_reservations.get(i);
			if(reservation.getDateInterval().isConflicting(this.dateInterval))
				if(reservation.getRoom().getPrice() == roomType)
					bookedRoom.put(reservation.getRoom().getRoom_number(), roomType); 
		}
		
		System.out.println(bookedRoom.size());
		allRoomsByType = (ArrayList<Room>) catagorizedRooms.get(roomType);
		for(int i=0; i<allRoomsByType.size(); i++)
			if(!bookedRoom.containsKey(allRoomsByType.get(i).getRoom_number()))
				availableRoomsByType.add(allRoomsByType.get(i).getRoom_number());
		
		// Notify observers of the change
		ChangeEvent event = new ChangeEvent(this);
		/*for(ChangeListener listener : listeners){
			listener.stateChanged(event);
		}*/
		listeners.get(0).stateChanged(event);
	}
	
	public void getBookingConfirmation(int room_number){
		// Create a reservation & add it to the all_reservations variable
		Reservation r = new Reservation(currentGuest.username, (Room)rooms.get(room_number), dateInterval, 
				dateInterval.getNumberOfDays(), LocalDate.now());
		all_reservations.add(r);
		ChangeEvent event = new ChangeEvent(this);
		listeners.get(1).stateChanged(event);
	}
	
	public String toString(){
		String s = "";
		for(Integer r : availableRoomsByType){
			s += Integer.toString(r) + "\n";
		}
		return s;
	}
}
