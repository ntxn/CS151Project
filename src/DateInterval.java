import java.time.LocalDate;

public class DateInterval {
	private LocalDate start_date;
	private LocalDate end_date;
	
	public DateInterval(LocalDate start_date, LocalDate end_date) {
		super();
		this.start_date = start_date;
		this.end_date = end_date;
	}
	
	public LocalDate getStart_date() {
		return start_date;
	}
	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}
	public LocalDate getEnd_date() {
		return end_date;
	}
	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}
	
	public boolean isConflicting(DateInterval duration){
		if(duration.end_date.isBefore(this.start_date) || 
		   duration.end_date.equals(this.start_date) ||
		   duration.start_date.isAfter(this.end_date) ||
		   duration.start_date.equals(this.end_date))
				return false;
		 
		return true;		
	}
	
	public String toString(){
		return start_date + " - " + end_date;
	}
}
