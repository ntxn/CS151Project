import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reservation {
	private Guest guest;
	private DateInterval dateInterval;
	private Room room;
	private int totalCharge;
	private LocalDate bookingDate;
	
	
	public Reservation(Guest guest, Room room, DateInterval dateInterval, 
			int total, LocalDate bookingDate) {
		this.guest = guest;
		this.dateInterval = dateInterval;
		this.room = room;
		this.totalCharge = total;
		this.bookingDate = bookingDate;
	}
	
	public Guest getGuest() {
		return guest;
	}
	public void setGuest(Guest guest) {
		this.guest = guest;
	}
	public DateInterval getDateInterval() {
		return dateInterval;
	}
	public void setDateInterval(DateInterval dateInterval) {
		this.dateInterval = dateInterval;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public int getTotal() {
		return totalCharge;
	}
	public void setTotal(int total) {
		this.totalCharge = total;
	}
	public LocalDate getDateBooked() {
		return bookingDate;
	}
	public void setDateBooked(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}
	
	public String toString(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
		return  "Name:\t" + guest.getName() 
			+ "\nRoom#:\t" + room.getRoom_number() 
			+ "\nDates:\t" + dateInterval.toString() 
			+ "\nTotal:\t$" + totalCharge  
			+ "\nBooked on:\t" + formatter.format(bookingDate) + "\n\n";
	}
}
