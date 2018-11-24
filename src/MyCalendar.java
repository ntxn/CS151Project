import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Set;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MyCalendar{

	private int maxDays;
	private int selectedDay;
	private ArrayList<ChangeListener> listeners;
	private GregorianCalendar cal;
	private boolean monthChanged;
	private ArrayList<Day> days;
	private Hashtable existingDates;
	private ArrayList<Integer> allRoomNumbers;
	
	/**
	 * Constructor
	 */
	public MyCalendar(ArrayList<Day> days, Hashtable existingDates, ArrayList<Integer> allRoomNumbers) {
		listeners = new ArrayList<ChangeListener>();
		cal = new GregorianCalendar();
		monthChanged = false;
		maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		selectedDay = cal.get(Calendar.DATE);
		this.days = days;
		this.existingDates = existingDates;
		this.allRoomNumbers = allRoomNumbers;
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
	
	
	public String getRooms(){
		LocalDate date = LocalDate.of(getCurrentYear(), getCurrentMonth() +1, selectedDay);
		System.out.println("\n" + date + "\n");
		if(existingDates.containsKey(date)){
			int index = (int)existingDates.get(date);
			Day d = days.get(index);
			String str = "Available Room: \n";
			int base = 100;
			ArrayList<Integer> availableRooms = d.getAvailableRooms();
			for(int i = 0; i< availableRooms.size(); i++){
				str += availableRooms.get(i) + "\t";
				if((i+1)%4 == 0)
					str += "\n";
			}
			str += "\n\nBooked Rooms: \n";
			
			for(Reservation r : d.getReservations())
				str += r.toString();
			
			return str;
		}
		
		String str = "Available Room:\n";
		
		for(int i : allRoomNumbers){
			str += i + "\t";
			if(i%4 == 0){
				str += "\n";
			}
		}
		return str;
	}
	
	
}
