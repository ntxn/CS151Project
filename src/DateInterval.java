import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * hold the start date and end date of a reservation
 * @author Ngan Nguyen
 *
 */
public class DateInterval {
	private LocalDate start_date;	// Check-in date
	private LocalDate end_date;		// Check-out date
	
	/**
	 * Constructor to create a DateInterval
	 * @param start_date
	 * @param end_date
	 */
	public DateInterval(LocalDate start_date, LocalDate end_date) {
		super();
		this.start_date = start_date;
		this.end_date = end_date;
	}
	
	/**
	 * get check-in date
	 * @return start_date
	 */
	public LocalDate getStart_date() {
		return start_date;
	}
	
	/**
	 * set check-in date
	 * @param start_date
	 */
	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}
	
	/**
	 * get check-out date
	 * @return end_date	check-out date
	 */
	public LocalDate getEnd_date() {
		return end_date;
	}
	
	/**
	 * set check-out date
	 * @param end_date check-out date
	 */
	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}
	
	/**
	 * Check if two interval is conflicting
	 * @param otherInterval
	 * @return true/false
	 */
	public boolean isConflicting(DateInterval otherInterval){
		if(otherInterval.end_date.isBefore(this.start_date) || 
		   otherInterval.end_date.equals(this.start_date) ||
		   otherInterval.start_date.isAfter(this.end_date) ||
		   otherInterval.start_date.equals(this.end_date))
				return false;
		 
		return true;		
	}
	
	/**
	 * Get the number of days from check-in to check-out dates
	 * @return number of days (int)
	 */
	public int getNumberOfDays(){
		return (int)Duration.between(start_date.atStartOfDay(), 
				end_date.atStartOfDay()).toDays();
	}
	
	/**
	 * Check if this DateInterval is after another DateInterval
	 * @param otherInterval
	 * @return true/false
	 */
	public boolean isAfter(DateInterval otherInterval){
		if(this.start_date.isAfter(otherInterval.getStart_date()))
			return true;
		return false;
	}
	
	/**
	 * Return String of check-in and check-out dates
	 */
	public String toString(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
		return formatter.format(start_date) + " - " 
				+ formatter.format(end_date);
	}
	
	
}
