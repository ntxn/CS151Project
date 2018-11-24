
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;



public class CalendarPanel extends JPanel implements ChangeListener{

	public enum DAYS {
		Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
	}
	
	public enum MONTHS {
		January, February, March, April, May, June, July, August, September, October, November, December;
	}
	
	
	private MyCalendar calendar;
	private DAYS[] arrayOfDays;
	private MONTHS[] arrayOfMonths;
	private int prevHighlight;
	private int maxDays;
	private JPanel buttonsPanel;
	private JLabel monthLabel;
	private JLabel daysOfWeek;
	private JTextArea roomsTextArea;
	private ArrayList<JButton> dayButtons;
	private JPanel monthContainer;
	private JPanel dayViewPanel;
	private JButton nextMonth;
	private JButton prevMonth;
	private JScrollPane dayScrollPane;
	private JPanel westPanel;
	private JPanel eastPanel;
	private JPanel daysOfWeekPanel;
	private JPanel northPanel;
	private JPanel monthLabelPanel;

	/**
	 * Constructs the calendar.
	 * @param model the  model that stores and manipulates calendar data
	 */
	public CalendarPanel(MyCalendar model) {
		setLayout(new BorderLayout());
		calendar = model;
		maxDays = model.getMaxDays();
		
		arrayOfDays = DAYS.values();
		arrayOfMonths = MONTHS.values();
		buttonsPanel = new JPanel(new GridLayout(0, 7));
		monthLabel = new JLabel();
		dayButtons = new ArrayList<JButton>();
		prevHighlight = -1;
		
		roomsTextArea = new JTextArea();
		roomsTextArea.setPreferredSize(new Dimension(500, 200));
		roomsTextArea.setEditable(false);

		dayScrollPane = new JScrollPane(roomsTextArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		monthContainer = new JPanel(new BorderLayout());
		dayViewPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		northPanel = new JPanel(new BorderLayout());
		monthLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		daysOfWeekPanel  = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		
		
		prevMonth = new JButton("<");
		prevMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendar.prevMonth();
				roomsTextArea.setText("");
			}
		});
		
		nextMonth = new JButton(">");
		nextMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendar.nextMonth();
				roomsTextArea.setText("");
			}
		});
		
		westPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		westPanel.add(prevMonth);
		
		eastPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		eastPanel.add(nextMonth);

		displayCalendar();
		
		setVisible(true);
	}

	private void displayCalendar(){
		addBlankButtons();
		createDayButtons();
		
		
		monthLabel.setText(arrayOfMonths[calendar.getCurrentMonth()] 
				+ " " + calendar.getCurrentYear());
		daysOfWeek = new JLabel("  Sunday      Monday      Tuesday    Wednesday   Thursday      Friday       Saturday");
		monthContainer.add(buttonsPanel, BorderLayout.CENTER);
		
		dayViewPanel.add(dayScrollPane);
		
		
		
		monthLabelPanel.add(monthLabel);
		
		daysOfWeekPanel.add(daysOfWeek);
		northPanel.add(monthLabelPanel, BorderLayout.NORTH);
		northPanel.add(daysOfWeekPanel, BorderLayout.SOUTH);
		
		
		add(northPanel, BorderLayout.NORTH);
		add(westPanel, BorderLayout.WEST);
		add(monthContainer, BorderLayout.CENTER);
		add(eastPanel, BorderLayout.EAST);
		add(dayViewPanel, BorderLayout.SOUTH);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if (calendar.hasMonthChanged()) {
			maxDays = calendar.getMaxDays();
			buttonsPanel.removeAll();

			prevHighlight = -1;
			calendar.resetHasMonthChanged();
			displayCalendar();
			repaint();
		} else {
			highlightSelectedDate(calendar.getSelectedDay() - 1);
		}
	}


	/**
	 * Highlights the currently selected date.
	 * @param d the currently selected date
	 */
	private void highlightSelectedDate(int d) {
		Border border = new LineBorder(Color.ORANGE, 2);
		dayButtons.get(d).setBorder(border);
		if (prevHighlight != -1) {
			dayButtons.get(prevHighlight).setBorder(new JButton().getBorder());
		}
		prevHighlight = d;
	}

	

	/**
	 * Creates buttons representing days of the current month and adds them to an array list.
	 */
	private void createDayButtons() {
		dayButtons.clear();
		for (int i = 1; i <= maxDays; i++) {
			final int d = i;
			JButton day = new JButton(Integer.toString(d));
			day.setBackground(Color.WHITE);
	
			day.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					calendar.setSelectedDate(d);
					highlightSelectedDate(d - 1);
					roomsTextArea.setText(calendar.getRooms());
					
				}
			});
			dayButtons.add(day);
			buttonsPanel.add(day);
		}
	}


	/**
	 * Adds filler buttons before the start of the month to align calendar.
	 */
	private void addBlankButtons() {
		for (int j = 1; j < calendar.getDayOfWeek(1); j++) {
			JButton blank = new JButton();
			blank.setEnabled(false);
			buttonsPanel.add(blank);
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
}