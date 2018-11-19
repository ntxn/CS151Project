import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Hold information of a reservation including guest info, 
 * room info, booking date and total charge
 * @author Ngan Nguyen
 *
 */
public class Reservation {
	private Guest guest;
	private DateInterval dateInterval;
	private Room room;
	private int totalCharge;
	private LocalDate bookingDate;
	
	/**
	 * Constructor to create a Reservation object with all instance variables
	 * @param guest
	 * @param room
	 * @param dateInterval
	 * @param total
	 * @param bookingDate
	 */
	public Reservation(Guest guest, Room room, DateInterval dateInterval, 
			int total, LocalDate bookingDate) {
		this.guest = guest;
		this.dateInterval = dateInterval;
		this.room = room;
		this.totalCharge = total;
		this.bookingDate = bookingDate;
	}
	
	/**
	 * get Guest info as a Guest Object
	 * @return Guest object
	 */
	public Guest getGuest() {
		return guest;
	}
	
	/**
	 * set Guest info for this reservation
	 * @param guest
	 */
	public void setGuest(Guest guest) {
		this.guest = guest;
	}
	
	/**
	 * get the DateInterval for booking dates
	 * @return DateInterval
	 */
	public DateInterval getDateInterval() {
		return dateInterval;
	}
	
	/**
	 * set DateInterval for booking dates
	 * @param dateInterval
	 */
	public void setDateInterval(DateInterval dateInterval) {
		this.dateInterval = dateInterval;
	}
	
	/**
	 * get Room info as Room object
	 * @return a Room Object for this reservation
	 */
	public Room getRoom() {
		return room;
	}
	
	/**
	 * set Room info
	 * @param room
	 */
	public void setRoom(Room room) {
		this.room = room;
	}
	
	/**
	 * Get total charge for the room * total dates
	 * @return charge
	 */
	public int getTotal() {
		return totalCharge;
	}
	
	/**
	 * set total charge for the booking
	 * @param total
	 */
	public void setTotal(int total) {
		this.totalCharge = total;
	}
	
	/**
	 * get the date the reservation booked
	 * @return LocalDate object of that date
	 */
	public LocalDate getDateBooked() {
		return bookingDate;
	}
	
	/**
	 * set the date the reservation booked
	 * @param bookingDate 
	 */
	public void setDateBooked(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}
	
	/**
	 * Return String for a whole reservation info
	 */
	public String toString(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
		return  "Name:\t" + guest.getName() 
			+ "\nRoom#:\t" + room.getRoom_number() 
			+ "\nDates:\t" + dateInterval.toString() 
			+ "\nTotal:\t$" + totalCharge  
			+ "\nBooked on:\t" + formatter.format(bookingDate) + "\n\n";
	}
}
