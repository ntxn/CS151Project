import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

/**
 * VIEW & CONTROLLER 2 for MVC Make A Reservation
 * Get the booking info (start date, end date, room price)
 * to display available rooms
 * @author Ngan Nguyen
 *
 */
public class BookingInfoPanel extends JPanel implements ChangeListener{
	private static final int NUMBER_OF_ROOM_TYPE = 3;
	private BookingModel dataModel;
	private JTextArea availableRoomTextArea;
	private JTextField start_date_txtField;
	private JTextField end_date_txtField;
	private LocalDate start_date;
	private LocalDate end_date;
	private int roomType;
	
	/**
	 * Constructor to initialize dataModel & 
	 * set up VIEW & CONTROLLER
	 * @param dataModel
	 */
	public BookingInfoPanel(BookingModel dataModel){
		this.dataModel = dataModel;
		roomType = 0;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));		
		
		// CONTROLLER - GUI
		// CHECK-IN - CHECK-OUT Dates JPANEL
		JLabel start_date_label = new JLabel("Check In (MM/DD/YYYY)   ");
		JLabel end_date_label = new JLabel("Check Out (MM/DD/YYYY)");
		
		JLabel start_date_error_label = new JLabel("");
		start_date_error_label.setForeground(Color.RED);
		JLabel end_date_error_label = new JLabel("");
		end_date_error_label.setForeground(Color.RED);
		
		start_date_txtField = new JTextField("MM/DD/YYYY");
		start_date_txtField.setPreferredSize(new Dimension(100, 30));
		end_date_txtField = new JTextField("MM/DD/YYYY");
		end_date_txtField.setPreferredSize(new Dimension(100, 30));
		
		JPanel start_date_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel end_date_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		start_date_panel.add(start_date_label);
		start_date_panel.add(start_date_txtField);
		start_date_panel.add(start_date_error_label);
		end_date_panel.add(end_date_label);
		end_date_panel.add(end_date_txtField);
		end_date_panel.add(end_date_error_label);
		
		
		// CONTROLLER - GUI
		// JPanel with room type BUTTONS
		JPanel roomTypeButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		int base = 100;
		for(int i=1; i<NUMBER_OF_ROOM_TYPE+1; i++){
			JButton button = new JButton(String.valueOf(base*i));
			button.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JButton b = (JButton) e.getSource();
					roomType = Integer.parseInt(b.getText());
				}
			});
			roomTypeButtonsPanel.add(button);
		}
		JLabel button_error = new JLabel("");
		button_error.setForeground(Color.RED);
		roomTypeButtonsPanel.add(button_error);
		
		
		JPanel availableRoomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		
		// CONTROLER - listener Part - ADD changes to data model
		JButton showRooms = new JButton("Show Available Rooms");
		showRooms.setAlignmentX(LEFT_ALIGNMENT);
		showRooms.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				start_date = validate_date_eligibility(
						start_date_txtField, 
						start_date_error_label, 
						"Check-in date has to be at least one day in advance", 
						start_date, LocalDate.now());
				
				end_date = validate_date_eligibility(
						end_date_txtField, 
						end_date_error_label, 
						"Checkout date is at least one day after check-in date", 
						end_date, start_date);
				
				if(roomType == 0)
					button_error.setText("\t\tSelect a price");
				else
					button_error.setText("");
				
				if(start_date!= null && end_date != null && start_date.isAfter(
						LocalDate.now()) && end_date.isAfter(start_date) && roomType != 0){
					DateInterval dateInterval = new DateInterval(start_date, end_date);
					dataModel.getAvailableRooms(dateInterval, roomType);
				}
			}
		});
		
		// View GUI
		availableRoomTextArea = new JTextArea();
		availableRoomTextArea.setPreferredSize(new Dimension(78, 200));
		availableRoomTextArea.setBorder(BorderFactory.createCompoundBorder(
				availableRoomTextArea.getBorder(), 
		        BorderFactory.createEmptyBorder(15, 15, 15, 15)));
		availableRoomPanel.add(showRooms);
		availableRoomPanel.add(availableRoomTextArea);
		
		add(start_date_panel);
		add(end_date_panel);
		add(roomTypeButtonsPanel);
		add(availableRoomPanel);
		
		setVisible(true);
	}
	
	
	
	/**
	 * VIEW - LISTENER Part - Respond to data model changes
	 */
	public void stateChanged(ChangeEvent e){
		dataModel = (BookingModel) e.getSource();
		ArrayList<Integer> availableRooms = dataModel.getAvailableRoomsByType();
		
		String s = "";
		for(Integer r : availableRooms){
			s += Integer.toString(r) + "\n";
		}
		availableRoomTextArea.setText(s);
	}
	
	/**
	 * HELPER METHOD
	 * convert a string of the correct format into a LocalDate Object
	 * @param input
	 * @return
	 */
	private LocalDate convertToDate(String input){
		String[] texts = input.split("/");
		return LocalDate.of(Integer.parseInt(texts[2]), 
				Integer.parseInt(texts[0]), Integer.parseInt(texts[1]));
		
	}
	
	/**
	 * HELPER METHOD
	 * validate if the date enter is in the right format
	 * @param input
	 * @return true/false
	 */

	private boolean validate_date_format(String input){
		if(input.matches("\\d{1,2}\\/\\d{1,2}\\/\\d{4}"))
			return true;
		return false;
	}
	
	/**
	 * HELPER METHOD
	 * validate if the date entered is valid 
	 * (ex: checkout date has to be after checking date,
	 * 		Check-in date cannot be before the current date)
	 * @param txtField
	 * @param error_label
	 * @param message
	 * @param date
	 * @param date_to_compare
	 * @return LocalDate object of the date entered in the JTextfield
	 */
	private LocalDate validate_date_eligibility(JTextField txtField, JLabel error_label, 
			String message, LocalDate date, LocalDate date_to_compare){
		String input = txtField.getText().trim();
		
		if(validate_date_format(input)){
			error_label.setText("");
			date = convertToDate(input);
			
			if(date_to_compare == null){
				error_label.setText("Please start with Check-in date");
				return date;
			}
			
			if(date.equals(date_to_compare) || date.isBefore(date_to_compare)){
				error_label.setText(message);
				return date;
			}
			
			return date;
		}
		
		error_label.setText("Invalid Date Format");
		return date;
	}
	
	
	/**
	 * Reset fields to make new reservation
	 */
	public void resetFields(){
		start_date_txtField.setText("");
		end_date_txtField.setText("");
		roomType = 0;
		availableRoomTextArea.setText("");
	}
}
