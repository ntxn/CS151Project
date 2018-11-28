import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class Day {
	private ArrayList<Integer> availableRooms;
	private ArrayList<Reservation> reservations;
	private LocalDate date;
	
	public Day(ArrayList<Integer> rooms, LocalDate d){
		date = d;
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
	
	/*
	public void addReservation(Reservation r){
		reservations.add(r);
	}*/
	
	private void removeReservation(Reservation r){
		for(int i = 0; i < reservations.size(); i++){
			if(r.equals(reservations.get(i))){
				reservations.remove(i);
				break;
			}
		}
	}

	private void removeAvailableRoom(int roomNumber){
		for(int i=0; i<availableRooms.size(); i++){ 
			if(availableRooms.get(i) == roomNumber){
				availableRooms.remove(i);
				break;
			}
		}
	}
	
	private void addAvailableRoom(int roomNumber){
		int i = 0;
		while(availableRooms.get(i) < roomNumber)
			i++;
		availableRooms.add(i, roomNumber);
	}
	
	public ArrayList<Integer> getAvailableRooms(){
		return availableRooms;
	}
	
	public ArrayList<Reservation> getReservations(){
		return reservations;
	}
	
	public void updateCancelledRoom(Reservation r){
		removeReservation(r);
		addAvailableRoom(r.getRoom().getRoom_number());
	}
	
	public void addReservation(Reservation r){
		reservations.add(r);
		removeAvailableRoom(r.getRoom().getRoom_number());
	}
}
