import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

/**
 * Day class holds available rooms & reservations of a date
 * @author Ngan Nguyen
 *
 */
public class Day {
	private ArrayList<Integer> availableRooms;
	private ArrayList<Reservation> reservations;
	private LocalDate date;
	
	/**
	 * Constructor that will make a copy of all room numbers 
	 * to another ArrayList<Integer> for each Date
	 * and initialize reservations ArrayList
	 * @param rooms
	 * @param d
	 */
	public Day(ArrayList<Integer> rooms, LocalDate d){
		date = d;
		availableRooms = new ArrayList<Integer>();
		for(int i=0; i<rooms.size(); i++)
			availableRooms.add(rooms.get(i));
		
		reservations = new ArrayList<Reservation>();
	}
	
	/**
	 * get the Date of this Day Object
	 * @return
	 */
	public LocalDate getDate(){
		return date;
	}
	
	/**
	 * remove reservation from the ArrayList<Reservation>
	 * @param r
	 */
	private void removeReservation(Reservation r){
		for(int i = 0; i < reservations.size(); i++){
			if(r.equals(reservations.get(i))){
				reservations.remove(i);
				break;
			}
		}
	}

	/**
	 * remove available room from the ArrayList<Integer>
	 * @param roomNumber
	 */
	private void removeAvailableRoom(int roomNumber){
		for(int i=0; i<availableRooms.size(); i++){ 
			if(availableRooms.get(i) == roomNumber){
				availableRooms.remove(i);
				break;
			}
		}
	}
	
	/**
	 * add room number to the available room arraylist
	 * @param roomNumber
	 */
	private void addAvailableRoom(int roomNumber){
		int i = 0;
		while(availableRooms.get(i) < roomNumber)
			i++;
		availableRooms.add(i, roomNumber);
	}
	
	/**
	 * get the arraylist of available room numbers for this.date
	 * @return
	 */
	public ArrayList<Integer> getAvailableRooms(){
		return availableRooms;
	}
	
	/**
	 * get the arraylist of reservation for this.date
	 * @return
	 */
	public ArrayList<Reservation> getReservations(){
		return reservations;
	}
	
	/**
	 * remove reservation r from reservations 
	 * and add available room to availableRooms
	 * 
	 * @param r
	 */
	public void updateCancelledRoom(Reservation r){
		removeReservation(r);
		addAvailableRoom(r.getRoom().getRoom_number());
	}
	
	/**
	 * add reservation to the arraylist of reservations
	 * and remove the room number from the available room list
	 * @param r
	 */
	public void addReservation(Reservation r){
		reservations.add(r);
		removeAvailableRoom(r.getRoom().getRoom_number());
	}
}
