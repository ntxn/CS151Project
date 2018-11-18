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
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ReservationsByRoomPanel extends JPanel implements ChangeListener{
	private ReservationsByRoomModel reservationsByRoomModel;
	private ArrayList<Room> rooms;
	private ArrayList<Reservation> res;
	private JTextArea textArea;
	
	public ReservationsByRoomPanel(ReservationsByRoomModel reservationsByRoomModel, ArrayList<Room> rooms){
		this.reservationsByRoomModel = reservationsByRoomModel;
		this.rooms = rooms;
		
		//CONTROLLER
		JPanel roomListPanel = new JPanel(new GridLayout(2,8));
		ArrayList<JButton> roomButtons = new ArrayList<JButton>();
		
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
		
		textArea  = new JTextArea();
		textArea.setBorder(BorderFactory.createCompoundBorder(
				textArea.getBorder(), 
		        BorderFactory.createEmptyBorder(20, 20, 20, 20)));
		
		setSize(700, 500);
		setLayout(new BorderLayout());
		
		add(roomListPanel, BorderLayout.NORTH);
		add(textArea, BorderLayout.CENTER);
		setVisible(true);
	}
	
	
	
	@Override
	public void stateChanged(ChangeEvent e){
		res = reservationsByRoomModel.getReservationsByRoom();
		
		String s = "";
		for(int i=0; i<res.size(); i++)
			s+= res.get(i).toString() + "\n";
		textArea.setText(s);
	}
}
