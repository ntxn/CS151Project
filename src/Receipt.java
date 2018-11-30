import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * STRATEGY PATTERN
 * JPanel display content of the receipt
 * @author Ngan Nguyen
 *
 */
public class Receipt extends JPanel{
	private ArrayList<Reservation> guestReservations;	// 1 guest's reservations
	private JTextArea textArea;	// to display receipt
	
	/**
	 * constructor to initialize textArea
	 */
	public Receipt(){
		textArea = new JTextArea();
		textArea.setOpaque(false);
		textArea.setEditable(false);
		add(textArea);
	}
	
	/**
	 * print receipt content
	 * @param formatter an instance of concrete ReceiptFormatter
	 */
	public void printReceipt(ReceiptFormatter formatter){
		String s= formatter.formatHeader() + formatter.formatReservation(guestReservations) 
			+ formatter.formatFooter();
		textArea.setText(s);
	}
	
	/**
	 * Update guestReservations with the most receipt one
	 * @param reservations
	 */
	public void setGuestReservations(ArrayList<Reservation> reservations){
		guestReservations = reservations;
	}
}