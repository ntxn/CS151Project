import java.time.LocalDate;

public class Reservation {
	private String guestUsername;
	private DateInterval dateInterval;
	private int room_number;
	private int totalCharge;
	private LocalDate bookingDate;
	
	
	public Reservation(String guest, int room_number, DateInterval dateInterval, 
			int total, LocalDate bookingDate) {
		this.guestUsername = guest;
		this.dateInterval = dateInterval;
		this.room_number = room_number;
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
	public int getRoom_number() {
		return room_number;
	}
	public void setRoom_number(int room_number) {
		this.room_number = room_number;
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
		return guestUsername + " " + room_number + " " + dateInterval.toString() 
		 + " " + totalCharge + " " + bookingDate;
	}
}
