import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Receipt extends JPanel{
	private ArrayList<Reservation> guestReservations;
	private JTextArea textArea;
	
	
	public Receipt(){
		textArea = new JTextArea();
		add(textArea);
	}
	
	public void printReceipt(ReceiptFormatter formatter){
		String s= formatter.formatHeader() + formatter.formatReservation(guestReservations) 
			+ formatter.formatFooter();
		textArea.setText(s);
	}
	
	public void setGuestReservations(ArrayList<Reservation> reservations){
		guestReservations = reservations;
	}
}