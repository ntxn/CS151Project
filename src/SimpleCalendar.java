

public class SimpleCalendar {

	public static void main(String[] args) {
		CalendarModel model = new CalendarModel();
		CalendarView view = new CalendarView(model);
		model.attach(view);
	}
	
}
