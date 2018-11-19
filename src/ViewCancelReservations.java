import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/** CAN'T DISPLAY CANCEL CONFIRMATION MESSAGE
 * This class allows User to View and Cancel their reservations
 * @author Ngan Nguyen
 *
 */
public class ViewCancelReservations extends JPanel{
	ArrayList<Reservation> guestReservations;
	ArrayList<Reservation> reservations; 
	ArrayList<Integer> originalIndexes;
	Guest currentGuest;
	JLabel cancelConfirmLabel;
	ArrayList<JButton> buttons;
	ArrayList<JPanel> panels;
	
	/**
	 * Constructor to initialize variables and set up JPanel
	 * to display guest's reservations
	 * @param res
	 * @param guest
	 */
	public ViewCancelReservations(ArrayList<Reservation> res, Guest guest){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		reservations = res;
		originalIndexes = new ArrayList<Integer>();
		currentGuest = guest;
		guestReservations = new ArrayList<Reservation>();
		buttons = new ArrayList<JButton>();
		cancelConfirmLabel = new JLabel();
		buttons = new ArrayList<JButton>();
		panels = new ArrayList<JPanel>();
		
		cancelConfirmLabel.setForeground(Color.RED);
		add(cancelConfirmLabel);
		
		generateGuestReservations(); 
		
		addReservationstoPanel(panels);
		
		
		setVisible(true);
	}
	
	/**
	 * Adding all reservations of the same guest into an ArrayList
	 */
	private void generateGuestReservations(){
		for(int i = 0; i<reservations.size(); i++){
			if(reservations.get(i).getGuest().isEqual(currentGuest)){
				addReservation(reservations.get(i), i);
			}
		}
	}
	
	/**
	 * Add a reservation to ArrayList guestReservations by date order
	 * @param a Reservation object
	 */
	private void addReservation(Reservation r, int index){
		int size = guestReservations.size();
		if(size == 0){
			guestReservations.add(r);
			originalIndexes.add(index);
			return;
		}
		
		int i=0;
		while(i<size && r.getDateInterval().isAfter(
				guestReservations.get(i).getDateInterval()))
			i++;
		guestReservations.add(i, r);
		originalIndexes.add(i, index);
	}
	
	/**
	 * 
	 * @param r
	 * @param index
	 * @return
	 */
	private JPanel displayAReservation(Reservation r){
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.setBorder(BorderFactory.createCompoundBorder(
				panel.getBorder(), 
		        BorderFactory.createEmptyBorder(15, 15, 15, 15)));
		
		JTextArea textArea = new JTextArea(r.toString());
		textArea.setOpaque(false);
		textArea.setBorder(BorderFactory.createCompoundBorder(
				panel.getBorder(), 
		        BorderFactory.createEmptyBorder(0, 0, 0, 0)));
		
		panel.add(textArea, BorderLayout.CENTER);
		JButton button = new JButton("Cancel This Reservation");
		buttons.add(button);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.add(button);
		panel.add(buttonPanel, BorderLayout.EAST);
		
		return panel;
	}
	
	private void addReservationstoPanel(ArrayList<JPanel> panels){
		removeAll();
		revalidate();
		repaint();
		for(int i=0; i < guestReservations.size(); i++){
			JPanel p = displayAReservation(guestReservations.get(i));
			addActionListeners(i);
			panels.add(p);
			add(p);
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	
	private void addActionListeners(int i){
		buttons.get(i).addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				guestReservations.remove(i);
				int index = originalIndexes.get(i);
				reservations.remove(index);
				originalIndexes.remove(i);
				decrementIndexes();
				cancelConfirmLabel.setText("Reservation Cancelled. Current Reservations: ");
				buttons = new ArrayList<JButton>();
				panels = new ArrayList<JPanel>();
				
				addReservationstoPanel(panels);
			}
		});
	}
	
	private void decrementIndexes(){
		int index;
		for(int i=0; i<originalIndexes.size(); i++){
			index = originalIndexes.get(i);
			originalIndexes.set(i, index-1);
		}
			
	}
}
