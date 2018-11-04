import java.time.LocalDate;

public class Reservation {
	private String guestUsername;
	private DateInterval dateInterval;
	private Room room;
	private int totalCharge;
	private LocalDate bookingDate;
	
	
	public Reservation(String guest, Room room, DateInterval dateInterval, 
			int total, LocalDate bookingDate) {
		this.guestUsername = guest;
		this.dateInterval = dateInterval;
		this.room = room;
		this.totalCharge = total;
		this.bookingDate = bookingDate;
	}
	
	public String getGuest() {
		return guestUsername;
	}
	public void setGuest(String guest) {
		this.guestUsername = guest;
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
		return guestUsername + " " + room + " " + dateInterval.toString() 
		 + " " + totalCharge + " " + bookingDate;
	}
}
