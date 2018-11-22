import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.event.*;

/**
 * DATA MODEL for MVC Make A Reservation
 * @author Ngan Nguyen
 *
 */
public class BookedRoomsByDatesModel {
	private ArrayList<Reservation> all_reservations;
	private ArrayList<Integer> availableRoomsByType;
	private ArrayList<ChangeListener> listeners;	// Data structure to hold listeners
	private ArrayList<Room> allRoomsByType; // 1 ArrayList of rooms by 1 chosen price, 
											// main DATA for this MODEL
	private Hashtable categorizedRooms;	// 3 ArrayLists of rooms catagorized by price 
	private DateInterval dateInterval;
	private Guest currentGuest;		// Guest that currently using the software
	private Hashtable rooms;		// All rooms in the hotel
	private int roomType;			// Hold the type of the room that guest chooses
	private int charge;				// Total charge for the booking
	
	
	/**
	 * Constructor to initialize some of the variables
	 * @param reservations
	 * @param catagorizedRooms
	 * @param currentGuest
	 * @param rooms
	 */
	public BookedRoomsByDatesModel(ArrayList<Reservation> reservations,
			Hashtable catagorizedRooms, Hashtable rooms){
		listeners = new ArrayList<ChangeListener>();
		this.all_reservations = reservations;
		allRoomsByType = new ArrayList<Room>();
		availableRoomsByType = new ArrayList<Integer>();
		this.categorizedRooms = catagorizedRooms;
		this.rooms = rooms;
	}

	/**
	 * Attach ChangeListener to this model
	 * @param l
	 */
	public void addChangeListener(ChangeListener l){
		listeners.add(l);
	}
	
	/**
	 * Accessor : get the main DATA for the model
	 * 			  All Available rooms based on 1 price
	 * @return	ArrayList<Integer> that hold the room numbers
	 */
	public ArrayList<Integer> getAvailableRoomsByType(){
		return availableRoomsByType;
	}
	
	
	
	/**
	 * Mutator1:Make changes to the main Data by adding all the available
	 * 			rooms based on a chosen date interval & room type
	 * 			Then notify the VIEW 1 about the changes
	 * @param dateInterval
	 * @param roomType
	 */
	public void getAvailableRooms(DateInterval dateInterval, int roomType){
		this.dateInterval = dateInterval;
		this.roomType = roomType;
		Reservation reservation;
		availableRoomsByType.clear();
		Hashtable bookedRoom = new Hashtable();
		
		// Get all rooms that are booked during that DateInterval
		for(int i=0; i<all_reservations.size(); i++){
			reservation = all_reservations.get(i);
			if(reservation.getDateInterval().isConflicting(this.dateInterval))
				if(reservation.getRoom().getPrice() == roomType)
					bookedRoom.put(reservation.getRoom().getRoom_number(), roomType); 
		}
		
		// Get the available rooms from the same categorized rooms 
		// by subtract bookedRoom out of categorizedRooms.get(roomType)
		allRoomsByType = (ArrayList<Room>) categorizedRooms.get(roomType);
		for(int i=0; i<allRoomsByType.size(); i++)
			if(!bookedRoom.containsKey(allRoomsByType.get(i).getRoom_number()))
				availableRoomsByType.add(allRoomsByType.get(i).getRoom_number());
		
		// Notify VIEW 1 of the change
		ChangeEvent event = new ChangeEvent(this);
		listeners.get(0).stateChanged(event);
	}
	
	/**
	 * Mutator 2 : Take the chosen room#, create a reservation based on 
	 * 			   the collected data, & then add it to the existing 
	 * 			   ArrayList<Reservation> 
	 * 			   Then notify VIEW 2 of the changes
	 * @param room_number
	 */
	public void getBookingConfirmation(int room_number){
		// Create a reservation & add it to the all_reservations variable
		charge = roomType*dateInterval.getNumberOfDays();
		Reservation r = new Reservation(currentGuest, (Room)rooms.get(room_number), dateInterval, 
				charge, LocalDate.now());
		all_reservations.add(r); 
		ChangeEvent event = new ChangeEvent(this);
		listeners.get(1).stateChanged(event);
	}
	
	/**
	 * Get the date interval of the current booking
	 * @return
	 */
	public DateInterval getDateInterval(){
		return dateInterval;
	}
	
	/**
	 * Get total charge for the current booking
	 * @return
	 */
	public int getCharge(){
		return charge;
	}
	
	/**
	 * Get String of room numbers from availableRoomsByType
	 * to display on VIEW 1
	 */
	public String toString(){
		String s = "";
		for(Integer r : availableRoomsByType){
			s += Integer.toString(r) + "\n";
		}
		return s;
	}
	
	/**
	 * Reset fields to make new reservation
	 */
	public void resetFields(){
		allRoomsByType = new ArrayList<Room>(); 
		availableRoomsByType = new ArrayList<Integer>();
		dateInterval = null;
		roomType = 0;
	}
	
	/**
	 * set the currentGuest
	 * @param guest
	 */
	public void setCurrentGuest(Guest guest){
		currentGuest = guest;
	}
}
