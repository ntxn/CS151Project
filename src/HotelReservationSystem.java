import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
/**
 * NEED TO ADD FUNCTION TO BACK BUTTON OF view resersation by room
 * @author Ngan Nguyen
 *
 */
public class HotelReservationSystem extends JFrame{
	private static Hashtable guests = new Hashtable(); 
	// hold existing guests info from guests.txt
	private static ArrayList<Reservation> reservations = new ArrayList<Reservation>(); 
		// hold all reservations from reservations.txt
	private static ArrayList<Room> rooms = new ArrayList<Room>(); 
		// hold all general rooms info from rooms.txt
	private static Hashtable categorizedRooms = new Hashtable();
	private static Hashtable roomsByHashtable = new Hashtable();
    private static Guest currentUser; // keep track of which user is using the program
    
    public HotelReservationSystem(Hashtable guests, ArrayList<Reservation> reservations,
    		ArrayList<Room> rooms, Hashtable catagorizedRooms, Hashtable roomsByHashtable){
    
    	// INITIALIZE variables	
    	this.guests = guests;
    	this.reservations = reservations;
    	this.rooms = rooms;
    	this.categorizedRooms = catagorizedRooms;
    	this.roomsByHashtable = roomsByHashtable;
    	currentUser = (Guest)guests.get("ngannguyen");
    	
    	
    //MVC - VIEW RESERVATION BY ROOM	
		ReservationsByRoomModel reservationsByRoomModel = new ReservationsByRoomModel(reservations);
		ReservationsByRoomPanel reservationsByRoomPanel = new ReservationsByRoomPanel(reservationsByRoomModel, rooms);
		reservationsByRoomModel.addChangeListener(reservationsByRoomPanel);
		JPanel viewReservationByRoomPANEL = new JPanel(new BorderLayout());
		viewReservationByRoomPANEL.add(reservationsByRoomPanel, BorderLayout.CENTER);
		
		JPanel viewReservationByRoomButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton reservationsByRoomBACKbutton = new JButton("Back");
		viewReservationByRoomButtonPanel.add(reservationsByRoomBACKbutton);
		viewReservationByRoomPANEL.add(viewReservationByRoomButtonPanel, BorderLayout.SOUTH);
		
	//MVC - MAKING A RESERVATION	
		BookedRoomsByDatesModel bookedRoomsByDatesModel = new BookedRoomsByDatesModel(reservations, categorizedRooms, currentUser, roomsByHashtable);
		
		// VIEW & CONTROLLER 1
		GetBookingInfoPanel getBookingInfoPanel = new GetBookingInfoPanel(bookedRoomsByDatesModel);
		bookedRoomsByDatesModel.addChangeListener(getBookingInfoPanel);
		
		// VIEW & CONTROLLER 2
		GetConfirmationPanel getConfirmationPanel = new GetConfirmationPanel(bookedRoomsByDatesModel);
		bookedRoomsByDatesModel.addChangeListener(getConfirmationPanel);
		
		// Button to add more reservations from the same guest
		JButton moreReservation = new JButton("More Reservation");
		moreReservation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bookedRoomsByDatesModel.resetFields();
				getBookingInfoPanel.resetFields();
				getConfirmationPanel.resetFields();
			}
		});
		
		// DONE button to go to view receipt page
		JButton doneButton = new JButton("Done");
		
		
		JPanel endReservationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		endReservationPanel.add(moreReservation);
		endReservationPanel.add(doneButton);
		
		JPanel bookingPanel = new JPanel();
		bookingPanel.setLayout(new BorderLayout());
		bookingPanel.add(getBookingInfoPanel, BorderLayout.NORTH);
		bookingPanel.add(getConfirmationPanel, BorderLayout.CENTER);
		bookingPanel.add(endReservationPanel, BorderLayout.SOUTH);
		
	//VIEW/CANCEL RESERVATIONS BY GUEST
		ViewCancelReservations viewCancelReservations = new ViewCancelReservations(reservations, currentUser);
		// Add a scroll
		JScrollPane scroll = new JScrollPane(viewCancelReservations, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JPanel cancelReservationsPanel = new JPanel(new BorderLayout());
		cancelReservationsPanel.add(scroll, BorderLayout.CENTER);
		
		JButton cancelReservation_QUIT_Button = new JButton("Quit");
		
		ReceiptFormatter formatter = new SimpleFormatter(); // TESTER FOR RECEIPT
		
		
		
		
		JPanel cancelReservationButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cancelReservationButtonPanel.add(cancelReservation_QUIT_Button);
		cancelReservationsPanel.add(cancelReservationButtonPanel, BorderLayout.SOUTH);
				
		
	// PANEL to hold all PAGES
		// Layout allows us to flip through different JPANEL
		JPanel pages = new JPanel(new CardLayout());
		pages.add(cancelReservationsPanel, "guest");
		pages.add(bookingPanel, "booking");
		pages.add(viewReservationByRoomPANEL, "manager");
		
		CardLayout cardLayout = (CardLayout) pages.getLayout();
		
	// ADD ActionListener to each button to flip through different pages
		cancelReservation_QUIT_Button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				//System.out.println(viewCancelReservations.printReceipt(formatter));
				
				cardLayout.show(pages, "booking");
				
			}
		});
		
		
		
		
		add(pages);
    }
	
}
