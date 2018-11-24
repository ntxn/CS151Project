import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * VIEW & CONTROLLER 1 for MVC Make A Reservation
 * @author Ngan Nguyen
 *
 */
public class GetConfirmationPanel extends JPanel implements ChangeListener{
	private BookedRoomsByDatesModel dataModel;
	private JTextField getRoomTextField;
	private JLabel confirmationLabel;
	private int roomNumber;
	private Reservation newReservation;
	
	
	/**
	 * Constructor to initialize dataModel & 
	 * set up VIEW & CONTROLLER
	 * @param data
	 */
	public GetConfirmationPanel(BookedRoomsByDatesModel data){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		dataModel = data;
		newReservation = null;
	
	//CONTROLER
		JLabel chooseRoomLABEL = new JLabel("Choose a Room");
		getRoomTextField = new JTextField("");
		getRoomTextField.setPreferredSize(new Dimension(75, 30));

		JLabel roomInputError = new JLabel("");
		roomInputError.setForeground(Color.RED);
		
		// GUI - Event source which passes new info for dataModel to update
		JButton confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String input = getRoomTextField.getText().trim();
				if(input.matches("\\d{3}")){
					roomInputError.setText("");
					roomNumber = Integer.parseInt(input);
					if(verifyRoomNumber(roomNumber))
						newReservation = dataModel.getBookingConfirmation(roomNumber);
					else
						roomInputError.setText("Invalid Room Number");
				}else{
					roomInputError.setText("Invalid Room Number");
				}
				
			}
		});
		
		// Add components to sub panel
		JPanel chooseRoomPANEL = new JPanel(new FlowLayout(FlowLayout.LEFT));
		chooseRoomPANEL.add(chooseRoomLABEL);
		chooseRoomPANEL.add(getRoomTextField);
		chooseRoomPANEL.add(confirmButton);
		chooseRoomPANEL.add(roomInputError);
	
	//VIEW GUI
		confirmationLabel = new JLabel();
		confirmationLabel.setForeground(Color.RED);
		
		JPanel confirmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		confirmPanel.add(confirmationLabel);
	
	//ADD all component to this JPanel
		add(chooseRoomPANEL);
		add(confirmPanel);
		setVisible(true);
	}

	/**
	 * VIEW SET WHAT TO DO WITH THE NEW INFO
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		String s = "CONFIRMATION: Room # " + roomNumber +
				"   From " + dataModel.getDateInterval().toString() 
				+ "   TOTAL: $" + dataModel.getCharge();
		confirmationLabel.setText(s);
	}
	
	/**
	 * HELPER METHOD
	 * Verify if the room# that the guest enter is 
	 * in the available room listed
	 * @param number
	 * @return
	 */
	private boolean verifyRoomNumber(int number){
		ArrayList<Integer> availableRooms = dataModel.getAvailableRoomsByType();
		int size = availableRooms.size();
		int i = 0;
		while(i < size && number != availableRooms.get(i))
			i++;
		return i < size ? true : false;
	}
	
	/**
	 * Reset fields to make new reservation
	 */
	public void resetFields(){
		roomNumber = 0;
		confirmationLabel.setText("");
		getRoomTextField.setText("");
	}
	
	public Reservation getNewReservation(){
		return newReservation;
	}
}
