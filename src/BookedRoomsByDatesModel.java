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
	private static HashMap<Integer, ArrayList<Room>> catagorizedRooms;
	private int roomType;
	
	public BookedRoomsByDatesModel(ArrayList<Reservation> all_reservations,
			HashMap<Integer, ArrayList<Room>> catagorizedRooms){
		listeners = new ArrayList<ChangeListener>();
		this.all_reservations = all_reservations;
		
		allRoomsByType = new ArrayList<Room>();
		this.catagorizedRooms = catagorizedRooms;
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
	public void checkAvailableRooms(DateInterval dateInterval, int roomType){
		this.dateInterval = dateInterval;
		this.roomType = roomType;
		Reservation reservation;
		availableRoomsByType = new ArrayList<Integer>();
		Hashtable bookedRoom = new Hashtable();
		
		for(int i=0; i<all_reservations.size(); i++){
			reservation = all_reservations.get(i);
			if(reservation.getDateInterval().isConflicting(this.dateInterval))
				if(reservation.getRoom().getPrice() == roomType)
					bookedRoom.put(reservation.getRoom(), roomType); 
		}
		
		allRoomsByType = catagorizedRooms.get(roomType);
		for(int i=0; i<allRoomsByType.size(); i++){
			if(!bookedRoom.contains(allRoomsByType.get(i)))
				availableRoomsByType.add(allRoomsByType.get(i).getRoom_number());
		}
		
		// Notify observers of the change
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener listener : listeners){
			listener.stateChanged(event);
		}
	}
	
	public String toString(){
		String s = "";
		for(Integer r : availableRoomsByType){
			s += Integer.toString(r) + "\n";
		}
		return s;
	}
}
