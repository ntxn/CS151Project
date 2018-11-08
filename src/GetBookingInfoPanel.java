import java.awt.BorderLayout;
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
	private ArrayList<Integer> availableRooms;
	private static final int NUMBER_OF_ROOM_TYPE = 3;
	
	public GetBookingInfoPanel(BookedRoomsByDatesModel dataModel){
		this.dataModel = dataModel;
		availableRooms = new ArrayList<Integer>();
		availableRoomTextArea = new JTextArea(5, 5);
		
		//setSize(700, 500);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JTextField start_date_txtField = new JTextField("Check in");
		JTextField end_date_txtField = new JTextField("Check out");
		
		JPanel roomTypeButtonsPanel = new JPanel();
		roomTypeButtonsPanel.setLayout(new FlowLayout());
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
		
		JButton showRooms = new JButton("Show Available Rooms");
		showRooms.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				start_date = convertToDate(start_date_txtField);
				end_date = convertToDate(end_date_txtField);
				DateInterval dateInterval = new DateInterval(start_date, end_date);
				dataModel.getAvailableRooms(dateInterval, roomType);
				
			}
		});
			
		add(start_date_txtField);
		add(end_date_txtField);
		add(roomTypeButtonsPanel);
		add(showRooms);
		add(availableRoomTextArea);
		
		setVisible(true);
	}
	
	
	
	/**
	 * 
	 */
	public void stateChanged(ChangeEvent e){
		dataModel = (BookedRoomsByDatesModel) e.getSource();
		availableRooms = dataModel.getAvailableRoomsByType();
		System.out.println(availableRooms);
		String s = "";
		for(Integer r : availableRooms){
			s += Integer.toString(r) + "\n";
		}
		availableRoomTextArea.setText(s);
	}
	
	/**
	 * 
	 * @param textField
	 * @return
	 */
	private LocalDate convertToDate(JTextField textField){
		String[] texts = textField.getText().trim().split("/");
		return LocalDate.of(Integer.parseInt(texts[2]), 
				Integer.parseInt(texts[0]), Integer.parseInt(texts[1]));
		
	}
}