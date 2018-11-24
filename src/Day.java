import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class Day {
	private ArrayList<Integer> availableRooms;
	private ArrayList<Reservation> reservations;
	private LocalDate date;
	
	public Day(ArrayList<Integer> rooms){
		availableRooms = new ArrayList<Integer>();
		for(int i=0; i<rooms.size(); i++)
			availableRooms.add(rooms.get(i));
		
		reservations = new ArrayList<Reservation>();
	}
	
	public void setDate(LocalDate d){
		date = d;
	}
	
	public LocalDate getDate(){
		return date;
	}
	
	public void addReservation(Reservation r){
		reservations.add(r);
	}
	
	

	public void updateAvailableRooms(int roomNumber){
		for(int i=0; i<availableRooms.size(); i++){ 
			if(availableRooms.get(i) == roomNumber){
				availableRooms.remove(i);
				break;
			}
		}
	}
	
	public void updateCancelledRoom(Room room){
		int roomNumber = room.getRoom_number();
		//availableRooms.put(roomNumber, room);
		//reservations.remove(roomNumber);
	}
	
	public ArrayList<Integer> getAvailableRooms(){
		return availableRooms;
	}
	
	public ArrayList<Reservation> getReservations(){
		return reservations;
	}
	
}
