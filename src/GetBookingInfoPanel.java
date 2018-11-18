import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

/**
 * Detect selection of  check in, check out.
 * 		 				room type
 * 						roomSelected
 * @author Kiera
 *
 */
public class GetBookingInfoPanel extends JPanel implements ChangeListener{
	private LocalDate start_date;
	private LocalDate end_date;
	private BookedRoomsByDatesModel dataModel;
	private int roomType;
	private JTextArea availableRoomTextArea;
	private static final int NUMBER_OF_ROOM_TYPE = 3;
	private JTextField start_date_txtField;
	private JTextField end_date_txtField;
	
	public GetBookingInfoPanel(BookedRoomsByDatesModel dataModel){
		this.dataModel = dataModel;
		roomType = 0;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));		
		
		// CHECK-IN - CHECK-OUT JPANEL
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
		
		
		// CONTROLER ADD changes to data model
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
				
				//System.out.println(start_date + "\t" + LocalDate.now() + "\t" + end_date +"\t" + roomType);
				
				if(roomType == 0)
					button_error.setText("\t\tSelect a price");
				else
					button_error.setText("");
				/*
				System.out.println("1\t" + (start_date!= null && end_date != null));
				if(start_date != null)
					System.out.println("2\t" + start_date.isAfter(LocalDate.now()));
				if(start_date!= null && end_date != null)
					System.out.println("3\t" + end_date.isAfter(start_date));
				System.out.println("4\t" + (roomType != 0));
				*/
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
	 * VIEW Respond to data model changes
	 */
	public void stateChanged(ChangeEvent e){
		dataModel = (BookedRoomsByDatesModel) e.getSource();
		ArrayList<Integer> availableRooms = dataModel.getAvailableRoomsByType();
		
		String s = "";
		for(Integer r : availableRooms){
			s += Integer.toString(r) + "\n";
		}
		availableRoomTextArea.setText(s);
	}
	
	/**
	 * HELPER
	 * @param input
	 * @return
	 */
	private LocalDate convertToDate(String input){
		String[] texts = input.split("/");
		return LocalDate.of(Integer.parseInt(texts[2]), 
				Integer.parseInt(texts[0]), Integer.parseInt(texts[1]));
		
	}
	
	/**
	 * HELPER
	 * @param input
	 * @return
	 */

	private boolean validate_date_format(String input){
		if(input.matches("\\d{1,2}\\/\\d{1,2}\\/\\d{4}"))
			return true;
		return false;
	}
	
	/**
	 * 
	 * @param txtField
	 * @param error_label
	 * @param message
	 * @param date
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
