
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;



public class CalendarView implements ChangeListener {

	public enum DAYS {
		Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
	}
	
	public enum MONTHS {
		January, February, March, April, May, June, July, August, September, October, November, December;
	}
	private CalendarModel model;
	private DAYS[] arrayOfDays = DAYS.values();
	private MONTHS[] arrayOfMonths = MONTHS.values();
	private int prevHighlight = -1;
	private int maxDays;

	private JFrame frame = new JFrame("Calendar");
	private JPanel monthViewPanel = new JPanel();
	private JLabel monthLabel = new JLabel();
	private JButton nextDay = new JButton("Next Date");
	private JButton prevDay = new JButton("Prev Date");
	private JTextPane dayTextPane = new JTextPane();
	private ArrayList<JButton> dayButtons = new ArrayList<JButton>();

	/**
	 * Constructs the calendar.
	 * @param model the  model that stores and manipulates calendar data
	 */
	public CalendarView(CalendarModel model) {
		this.model = model;
		maxDays = model.getMaxDays();
		monthViewPanel.setLayout(new GridLayout(0, 7));
		dayTextPane.setPreferredSize(new Dimension(700, 200));
		dayTextPane.setEditable(false);

		createDayButtons();
		addBlankButtons();
		addDayButtons();
		showDate(model.getSelectedDay());
		highlightSelectedDate(model.getSelectedDay() - 1);

		
		JButton prevMonth = new JButton("<");
		prevMonth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.prevMonth();
				nextDay.setEnabled(false);
				prevDay.setEnabled(false);
				dayTextPane.setText("");
				frame.setSize(700,500);
			}
		});
		JButton nextMonth = new JButton(">");
		nextMonth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.nextMonth();
				nextDay.setEnabled(false);
				prevDay.setEnabled(false);
				dayTextPane.setText("");
				frame.setSize(700,500);
			}
		});
		
		JPanel monthContainer = new JPanel();
		monthContainer.setLayout(new BorderLayout());
		monthLabel.setText(arrayOfMonths[model.getCurrentMonth()] + " " + model.getCurrentYear());
		monthContainer.add(monthLabel, BorderLayout.NORTH);
		monthContainer.add(new JLabel("  Sunday        Monday      Tuesday    Wednesday   Thursday      Friday       Saturday"), BorderLayout.CENTER);
		monthContainer.add(monthViewPanel, BorderLayout.SOUTH);
		
		JPanel dayViewPanel = new JPanel();
		dayViewPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		JScrollPane dayScrollPane = new JScrollPane(dayTextPane);
		dayScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		dayViewPanel.add(dayScrollPane, c);
		JPanel buttonsPanel = new JPanel();
		nextDay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.nextDay();
			}
		});
		prevDay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.prevDay();
			}
		});
		buttonsPanel.add(prevDay);
		buttonsPanel.add(nextDay);
		c.gridx = 0;
		c.gridy = 1;
		dayViewPanel.add(buttonsPanel, c);

		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});

		frame.add(prevMonth);
		frame.add(monthContainer);
		frame.add(nextMonth);
		frame.add(dayViewPanel);
		frame.add(quit);
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(700, 500);
		frame.setVisible(true);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (model.hasMonthChanged()) {
			maxDays = model.getMaxDays();
			dayButtons.clear();
			monthViewPanel.removeAll();
			monthLabel.setText(arrayOfMonths[model.getCurrentMonth()] + " " + model.getCurrentYear());
			createDayButtons();
			addBlankButtons();
			addDayButtons();
			
			prevHighlight = -1;
			model.resetHasMonthChanged();
			frame.pack();
			frame.repaint();
		} else {
			showDate(model.getSelectedDay());
			highlightSelectedDate(model.getSelectedDay() - 1);
		}
	}

	

	/**
	 * Shows the selected date and events on that date.
	 * @param d the selected date
	 */
	private void showDate(final int d) {
		model.setSelectedDate(d);
		String dayOfWeek = arrayOfDays[model.getDayOfWeek(d) - 1] + "";
		String date = (model.getCurrentMonth() + 1) + "/" + d + "/" + model.getCurrentYear();
		String events = "";
		
		dayTextPane.setText(dayOfWeek + " " + date + "\n" + events);
		dayTextPane.setCaretPosition(0);
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
		for (int i = 1; i <= maxDays; i++) {
			final int d = i;
			JButton day = new JButton(Integer.toString(d));
			day.setBackground(Color.WHITE);
	
			day.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent arg0) {
					showDate(d);
					highlightSelectedDate(d - 1);
					//create.setEnabled(true);
					nextDay.setEnabled(true);
					prevDay.setEnabled(true);
				}
			});
			dayButtons.add(day);
		}
	}

	/**
	 * Adds the buttons representing the days of the month to the panel.
	 */
	private void addDayButtons() {
		for (JButton d : dayButtons) {
			monthViewPanel.add(d);
		}
	}

	/**
	 * Adds filler buttons before the start of the month to align calendar.
	 */
	private void addBlankButtons() {
		for (int j = 1; j < model.getDayOfWeek(1); j++) {
			JButton blank = new JButton();
			blank.setEnabled(false);
			monthViewPanel.add(blank);
		}
	}
}
