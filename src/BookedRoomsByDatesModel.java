import java.util.ArrayList;
import java.util.HashMap;

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
		availableRoomsByType = new ArrayList<Integer>();
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
		ArrayList<Room> bookedRoom = new ArrayList<Room>();
		
		// it doesn't get the right room
		for(int i=0; i<all_reservations.size(); i++){
			reservation = all_reservations.get(i);
			if(!reservation.getDateInterval().isConflicting(this.dateInterval))
				bookedRoom.add(reservation.getRoom());
		}
		
		allRoomsByType = catagorizedRooms.get(roomType);
		for(int i=0; i<allRoomsByType.size(); i++){System.out.println(allRoomsByType.get(i).getPrice());
			for(int j=0; j<bookedRoom.size(); j++){
				if(allRoomsByType.get(i).isEqual(bookedRoom.get(j)))
					availableRoomsByType.add(allRoomsByType.get(i).getRoom_number());
			}
			
		}
		System.out.println(availableRoomsByType.size());
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
