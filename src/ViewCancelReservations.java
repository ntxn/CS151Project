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

/** 
 * This class allows User to View and Cancel their reservations
 * @author Ngan Nguyen
 *
 */
public class ViewCancelReservations extends JPanel{
	private ArrayList<Reservation> guestReservations;
	private ArrayList<Reservation> reservations; 
	private ArrayList<Integer> originalIndexes;
	private JPanel cancelConfirmationPanel;
	private ArrayList<JButton> buttons;
	private Guest currentGuest;
	private CalendarModel calendar;
	
	
	
	/**
	 * Constructor to initialize variables and set up JPanel
	 * to display guest's reservations
	 * @param res
	 * @param guest
	 */
	public ViewCancelReservations(ArrayList<Reservation> res, CalendarModel c){
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// INITIALIZE variables
		reservations = res;
		calendar = c;
		
		originalIndexes = new ArrayList<Integer>();
		guestReservations = new ArrayList<Reservation>();
		buttons = new ArrayList<JButton>();
		
		// label to display cancellation confirmation
		JLabel cancelConfirmLabel = new JLabel();
		cancelConfirmLabel.setForeground(Color.RED);
		cancelConfirmLabel.setText("1 Reservation " + " Cancelled. Current Reservations: ");
		
		cancelConfirmationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		cancelConfirmationPanel.add(cancelConfirmLabel);
		cancelConfirmationPanel.setBorder(BorderFactory.createCompoundBorder(
				cancelConfirmationPanel.getBorder(), 
		        BorderFactory.createEmptyBorder(15, 20, 0, 0)));
		
		setVisible(true);
	}
	
	
	/**
	 * 
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	
	
	/**
	 * Generate an ArrayList of Reservations made by currentGuest
	 */
	private void generateGuestReservations(){
		guestReservations.clear();
		for(int i = 0; i<reservations.size(); i++)
			if(reservations.get(i).getGuest().isEqual(currentGuest))
				addReservation(reservations.get(i), i);
	}
	
	
	/**
	 * Add a reservation to ArrayList guestReservations by date order
	 * HELPER of generateGuestReservations
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
	 * Add each reservation to an individual JPanel 
	 * along with a JButton to cancel this reservation
	 * @param r Reservation object
	 * @param i index of this reservation in the array
	 * @return a JPanel with a reservation & a button
	 */
	private JPanel addReservationToAPanel(Reservation r, int i){
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.setBorder(BorderFactory.createCompoundBorder(
				panel.getBorder(), 
		        BorderFactory.createEmptyBorder(15, 20, 0, 20)));
		
		JTextArea textArea = new JTextArea("#" + i +"\n" + r.toString());
		textArea.setOpaque(false);
		textArea.setEditable(false);
		
		panel.add(textArea, BorderLayout.CENTER);
		JButton button = new JButton("Cancel This Reservation");
		buttons.add(button);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.add(button);
		panel.add(buttonPanel, BorderLayout.EAST);
		
		return panel;
	}
	
	
	/**
	 * Add ActionListener to each button to delete the reservation
	 * that it links to
	 * @param i
	 */
	private void addActionListener(int i){
		buttons.get(i).addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				calendar.removeReservation(guestReservations.get(i));
				guestReservations.remove(i);
				int index = originalIndexes.get(i);
				reservations.remove(index);
				originalIndexes.remove(i);
				decrementIndexes(index);
				buttons = new ArrayList<JButton>();
				
				// Clear all existing components on the main Panel
				removeAll();
				revalidate();
				repaint();
				
				// Display confirmation message
				add(cancelConfirmationPanel);
				
				// Display the remaining reservations, if any
				displayReservations();
			}
		});
	}
	
	/**
	 * Decrement only the original indexes after the j position
	 * HELPER for addActionListener
	 */
	private void decrementIndexes(int removedIndex){
		int index;
		for(int i=0; i<originalIndexes.size(); i++){
			index = originalIndexes.get(i);
			if(index > removedIndex)
				originalIndexes.set(i, index-1);
		}
	}
	
	
	
	/**
	 * set the currentGuest
	 * @param guest
	 */
	public void setCurrentGuest(Guest guest){
		currentGuest = guest;
	}
	
	public ArrayList<Reservation> getGuestReservations(){
		generateGuestReservations();
		return guestReservations;
	}
	
	public void setReservations(ArrayList<Reservation> r){
		reservations = r;
	}
	
	/**
	 * Add all the panels that hold each reservation & a cancel button
	 * to the main panel (this class) to display them
	 * @param panels
	 */
	public void displayReservations(){
		// reset guestReservations
		guestReservations.clear();
		
		
		
		// Generate an ArrayList of Reservations made by currentGuest
		generateGuestReservations(); 
		
		JPanel p;
		for(int i=0; i < guestReservations.size(); i++){ 
			p = addReservationToAPanel(guestReservations.get(i), i+1);
			addActionListener(i); // for each button
			add(p);
		}
	}
	
	public void clearPanel(){
		// Clear all existing components on the main Panel
		removeAll();
		revalidate();
		repaint();
		buttons = new ArrayList<JButton>();
	}
	
}
