import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Panel to hold View & Controller for MVC View Reservations by Room
 * @author Ngan Nguyen
 *
 */
public class ReservationsByRoomPanel extends JPanel implements ChangeListener{
	private ReservationsByRoomModel reservationsByRoomModel;	// Data
	private ArrayList<Room> rooms;	// All the rooms of the hotel
	private JTextArea textArea;		// VIEW GUI - to display bookings
	
	/**
	 * Constructor to make VIEW & CONTROLLER for Reservations By Room MVC
	 * The new Object is Listener ready
	 * @param reservationsByRoomModel
	 * @param rooms
	 */
	public ReservationsByRoomPanel(ReservationsByRoomModel reservationsByRoomModel, ArrayList<Room> rooms){
		this.reservationsByRoomModel = reservationsByRoomModel;
		this.rooms = rooms;
		
	//CONTROLLER
		JPanel roomListPanel = new JPanel(new GridLayout(2,8));
		ArrayList<JButton> roomButtons = new ArrayList<JButton>();
		
		// Add buttons for each room & add an ActionListener on them
		for(int i=0; i<rooms.size(); i++){
			JButton button = new JButton(String.valueOf(rooms.get(i).getRoom_number()));
			button.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JButton b = (JButton) e.getSource();
					reservationsByRoomModel.setReservationsByRoom(Integer.parseInt(b.getText()));
				}
			});
			roomButtons.add(button);
			roomListPanel.add(roomButtons.get(i));
		}
	
	//VIEW
		textArea  = new JTextArea();
		textArea.setBorder(BorderFactory.createCompoundBorder(
				textArea.getBorder(), 
		        BorderFactory.createEmptyBorder(20, 20, 20, 20)));
		
		// Add a scroll for textArea
		JScrollPane scroll = new JScrollPane(textArea, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		setSize(700, 500);
		setLayout(new BorderLayout());
		
		add(roomListPanel, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		setVisible(true);
	}
	
	public void resetFields(){
		textArea.setText("");
	}
	
	
	/**
	 * VIEW - what to do when the data change
	 */
	@Override
	public void stateChanged(ChangeEvent e){
		ArrayList<Reservation> res = reservationsByRoomModel.getReservationsByRoom();
		
		String s = "";
		for(int i=0; i<res.size(); i++)
			s+= res.get(i).toString() + "\n";
		textArea.setText(s);
	}
}
