import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * MODEL for MVC VIEW reservations BY DATE
 * @author En-Ping Shih, Ngan Nguyen
 *
 */
public class CalendarModel{
	private int maxDays;	// maximum number of days in the current month
	private int selectedDay;	// the day clicked on the calendar UI
	private ArrayList<ChangeListener> listeners;	//Data structure for listeners
	private GregorianCalendar cal;
	private boolean monthChanged;	
	private ArrayList<Day> days;	// Data structure for data of the model
									// hold reservations for each day 
									// if it has a reservation
	private ArrayList<Integer> allRoomNumbers;	// integer version of room inventory
	private String allRooms;
	
	/**
	 * Constructor
	 * It takes ArrayList<Day> & ArrayList<Integer> loaded from text file
	 */
	public CalendarModel(ArrayList<Day> days, ArrayList<Integer> allRoomNumbers) {
		listeners = new ArrayList<ChangeListener>();
		cal = new GregorianCalendar();
		monthChanged = false;
		maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		selectedDay = cal.get(Calendar.DATE);
		this.days = days;
		this.allRoomNumbers = allRoomNumbers;
		allRooms = getAllRooms();
	}
	
	/**
	 * reset info of the Calendar to the current date
	 */
	public void reset(){
		cal = new GregorianCalendar();
		monthChanged = false;
		maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		selectedDay = cal.get(Calendar.DATE);
	}
	
	/**
	 * Adds ChangeListeners to array.
	 * @param l the ChangeListener
	 */
	public void attach(ChangeListener l) {
		listeners.add(l);
	}
	
	/**
	 * Updates all ChangeListeners in array. 
	 * Notify view of the changes
	 */
	private void update() {
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 * Sets the user selected day.
	 * @param day the day of the month
	 */
	public void setSelectedDate(int day) {
		selectedDay = day;
		update();
	}
	
	/**
	 * Gets the user selected day.
	 * @return the day of the month
	 */
	public int getSelectedDay() {
		return selectedDay;
	}

	/**
	 * Gets the current year the calendar is at.
	 * @return the current year
	 */
	public int getCurrentYear() {
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * Gets the current month the calendar is at.
	 * @return the current month
	 */
	public int getCurrentMonth() {
		return cal.get(Calendar.MONTH);
	}
	
	/**
	 * Gets the value representing the day of the week
	 * @param i the day of the month
	 * @return the day of the week (1-7)
	 */
	public int getDayOfWeek(int i) {
		cal.set(Calendar.DAY_OF_MONTH, i);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * Gets the max number of days in a month.
	 * @return the max number of days in a month
	 */
	public int getMaxDays() {
		return maxDays;
	}

	/**
	 * Moves the calendar forward by one month.
	 */
	public void nextMonth() {
		cal.add(Calendar.MONTH, 1);
		maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		monthChanged = true;
		update();
	}
	
	/**
	 * Moves the calendar backward by one month.
	 */
	public void prevMonth() {
		cal.add(Calendar.MONTH, -1);
		maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		monthChanged = true;
		update();
	}
	
	
	/**
	 * Checks if the month has changed as a result of user interaction.
	 * @return
	 */
	public boolean hasMonthChanged() {
		return monthChanged;
	}
	
	/**
	 * Resets monthChanged to false.
	 */
	public void resetHasMonthChanged() {
		monthChanged = false;
	}
	
	
	/**
	 * get index of d in the array days, if exists
	 * @param d
	 * @return return index or -1
	 */
	private int getDateIndex(LocalDate d){
		for(int i = 0; i<days.size(); i++)
			if(d.equals(days.get(i).getDate()))
				return i;
		
		return -1;
	}
	
	/**
	 * gather all available rooms & booked rooms on a day
	 * @return concatenated string of those info
	 */
	public String printRooms(){
		// get LocalDate of the clicked date on the calendar
		LocalDate date = LocalDate.of(getCurrentYear(), getCurrentMonth() +1, selectedDay);
		int index = getDateIndex(date);	// get index of this date in days
		
		// if this date exists in the array, print info from the Day
		if(index != -1){	
			Day d = days.get(index);
			String str = "Available Room: \n";
			int base = 100;
			ArrayList<Integer> availableRooms = d.getAvailableRooms();
			for(int i = 0; i< availableRooms.size(); i++){
				str += availableRooms.get(i) + "\t";
				if((i+1)%4 == 0)
					str += "\n";
			}
			str += "\n\nBooked Rooms\n";
			
			for(Reservation r : d.getReservations())
				str += r.toString();
			
			return str;
		}
		
		//else return all room numbers
		return allRooms;
	}
	
	/**
	 * concatenate all the room number into a string
	 * @return
	 */
	private String getAllRooms(){
		String str = "Available Room:\n";
				
		for(int i : allRoomNumbers){
			str += i + "\t";
			if(i%4 == 0){
				str += "\n";
			}
		}
		return str;
	}
	
	/**
	 * remove reservation r from all the Days that contains it
	 * and add the room to the available room list
	 * @param r
	 */
	public void removeReservation(Reservation r){
		DateInterval dateInterval = r.getDateInterval();
		LocalDate date = dateInterval.getStart_date();
		LocalDate endDate = dateInterval.getEnd_date();
		int index = getDateIndex(date);
		
		while(date.isBefore(endDate)){
			days.get(index).updateCancelledRoom(r);
			if(days.get(index).getReservations().size() == 0)
				days.remove(index);
			else
				index++;
			
			date = date.plusDays(1);
		}
	}
	
	/**
	 * Add reservation to each day in the calendar
	 * and remove the room number from the available room list
	 * @param r
	 */
	public void addReservation(Reservation r){
		DateInterval dateInterval = r.getDateInterval();
		LocalDate date = dateInterval.getStart_date();
		LocalDate endDate = dateInterval.getEnd_date();
		
		
		//Getting index of the date in days & 
		//whether the date is existing already
		int index =0 ;
		for(int i = 0; i<days.size(); i++){
			if(date.equals(days.get(i).getDate()) 
					|| date.isBefore(days.get(i).getDate()))
				break;
			index++;
		}
		
		// Add the reservation to each day in the date interval
		while(date.isBefore(endDate)){
			if(!date.equals(days.get(index).getDate()))
				days.add(index, new Day(allRoomNumbers, date));
			
			days.get(index).addReservation(r);
			date = date.plusDays(1);
			index++;
		}
	}
	
}
