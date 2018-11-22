
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Model for Calendar. 
 */
public class CalendarModel{

	private int maxDays;
	private int selectedDay;
	private ArrayList<ChangeListener> listeners = new ArrayList<>();
	private GregorianCalendar cal = new GregorianCalendar();
	private boolean monthChanged = false;
	
	/**
	 * Constructor
	 */
	public CalendarModel() {
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
	 */
	public void update() {
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
	 * Moves the selected day forward by one.
	 */
	public void nextDay() {
		selectedDay++;
		if (selectedDay > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
			nextMonth();
			selectedDay = 1;
		}
		update();
	}
	
	/**
	 * Moves the selected day backward by one.
	 */
	public void prevDay() {
		selectedDay--;
		if (selectedDay < 1) {
			prevMonth();
			selectedDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
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
	
}