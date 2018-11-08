import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GetConfirmationPanel extends JPanel implements ChangeListener{
	private JTextField getRoomTextField;
	private JTextField confirmTextField;
	private ArrayList<Reservation> r;
	
	public GetConfirmationPanel(BookedRoomsByDatesModel bookedRoomsByDatesModel, ArrayList<Reservation> r){
		this.r= r;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		getRoomTextField = new JTextField("Type a room");
		confirmTextField = new JTextField();
		JButton confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bookedRoomsByDatesModel.getBookingConfirmation(Integer.parseInt
						(getRoomTextField.getText()));
				
			}
		});
		
		add(getRoomTextField);
		add(confirmButton);
		add(confirmTextField);
		setVisible(true);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		confirmTextField.setText("Booking confirmed. Total Booking: " + r.size());
	}
	
	
}
