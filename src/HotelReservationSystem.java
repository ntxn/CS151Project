import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
/**
 * NEED TO ADD FUNCTION TO BACK BUTTON OF view resersation by room
 * @author Kiera
 *
 */
public class HotelReservationSystem {
	private static Hashtable guests = new Hashtable(); // hold existing guest info from guests.txt
	private static ArrayList<Reservation> all_reservations = new ArrayList<Reservation>(); // hold all reservations from reservations.txt
    private static ArrayList<Room> rooms = new ArrayList<Room>(); // hold all general rooms info from rooms.txt
    private static Hashtable roomsByHashtable = new Hashtable();
    private static Hashtable catagorizedRooms = new Hashtable();
    private static Guest currentUser; // keep track of which user is using the program
    
    
    
	public static void main(String[] args) throws FileNotFoundException{
		loadData("guests.txt", 1);
		loadData("rooms.txt", 2);
		loadData("reservations.txt", 3);
		currentUser = (Guest) guests.get("Luweiwei");
		
		JFrame frame = new JFrame("Hotel SEN");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 500);
		
		/* TEST CODE FOR LOADING DATA 
        System.out.println(guests.get("ngannguyen2").toString());
		
        for(Room room : rooms)
        	System.out.println(room.toString());
        
        for(Reservation r: all_reservations)
        	System.out.println(r.toString());
        */
		
	//MVC - VIEW RESERVATION BY ROOM	
		ReservationsByRoomModel reservationsByRoomModel = new ReservationsByRoomModel(all_reservations);
		ReservationsByRoomPanel reservationsByRoomPanel = new ReservationsByRoomPanel(reservationsByRoomModel, rooms);
		reservationsByRoomModel.addChangeListener(reservationsByRoomPanel);
		JPanel viewReservationByRoomPANEL = new JPanel(new BorderLayout());
		viewReservationByRoomPANEL.add(reservationsByRoomPanel, BorderLayout.CENTER);
		
		JPanel viewReservationByRoomButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton reservationsByRoomBACKbutton = new JButton("Back");
		viewReservationByRoomButtonPanel.add(reservationsByRoomBACKbutton);
		viewReservationByRoomPANEL.add(viewReservationByRoomButtonPanel, BorderLayout.SOUTH);
		
	//MVC - MAKING A RESERVATION	
		BookedRoomsByDatesModel bookedRoomsByDatesModel = new BookedRoomsByDatesModel(all_reservations, catagorizedRooms, currentUser, roomsByHashtable);
		
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
		ViewCancelReservations viewCancelReservations = new ViewCancelReservations(all_reservations, currentUser);
		// Add a scroll
		JScrollPane scroll = new JScrollPane(viewCancelReservations, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JPanel cancelReservationsPanel = new JPanel(new BorderLayout());
		cancelReservationsPanel.add(scroll, BorderLayout.CENTER);
		
		JButton cancelReservation_QUIT_Button = new JButton("Quit");
		
		ReceiptFormatter formatter = new SimpleFormatter(); // TESTER FOR RECEIPT
		
		
		cancelReservation_QUIT_Button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){ // FOR TEMPORARY TESTING ONLY
				/*System.out.println("\n" + all_reservations.size());
				for(int i=0; i < all_reservations.size(); i++)
					System.out.println("\n#" + i+ "\n" + all_reservations.get(i).toString());
					*/
				//System.out.println(viewCancelReservations.printReceipt(formatter));
				
				frame.add(viewReservationByRoomPANEL);
				//frame.dispose();
				
			}
		});
		
		JPanel cancelReservationButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cancelReservationButtonPanel.add(cancelReservation_QUIT_Button);
		cancelReservationsPanel.add(cancelReservationButtonPanel, BorderLayout.SOUTH);
				
		
	
		
		
		
		
		
		//frame.add(viewReservationByRoomPANEL);
		//frame.add(bookingPanel);
		frame.add(cancelReservationsPanel);
		
		frame.setVisible(true);
    }
	
	/**
	 * Load data from a text file to data structure in Java
	 * @param fileName
	 * @param option 1: guests.txt, 2: rooms.txt, 3: reservations.txt
	 * @throws FileNotFoundException
	 */
	public static void loadData(String fileName, int option) throws FileNotFoundException{
		File file = new File(fileName);
		Scanner fin = new Scanner(file);
		
		switch(option){
		case 1:
			/* LOAD GUESTS INFO */
			while(fin.hasNextLine()){
				String name = fin.nextLine(); 
				String[] logIn = fin.nextLine().split(" ");
				guests.put(logIn[0], new Guest(name, logIn[0], logIn[1]));
			}
			break;
		case 2:
			/* LOAD ROOMS INFO */
			ArrayList<Room> type1Rooms = new ArrayList<Room>();
		    ArrayList<Room> type2Rooms = new ArrayList<Room>();
		    ArrayList<Room> type3Rooms = new ArrayList<Room>();
		    
			while(fin.hasNextLine()){
				String[] roomInfo = fin.nextLine().split(" ");
				int room_number = Integer.parseInt(roomInfo[0]);
				int price = Integer.parseInt(roomInfo[1]);
				Room room = new Room(room_number, price);
				rooms.add(room);
				
				roomsByHashtable.put(room_number, room);
				
				if(price == 100)
					type1Rooms.add(room);
				else if(price == 200)
					type2Rooms.add(room);
				else
					type3Rooms.add(room);
			}
			
			catagorizedRooms.put(100, type1Rooms);
			catagorizedRooms.put(200, type2Rooms);
			catagorizedRooms.put(300, type3Rooms);
			break;
		case 3:
			/* LOAD RESERVATIONS INFO */
			while(fin.hasNextLine()){
				String[] str = fin.nextLine().split(" ");
				
				String[] date = str[2].split("/");
				LocalDate start_date = LocalDate.of(Integer.parseInt(date[2]), 
						Integer.parseInt(date[0]), Integer.parseInt(date[1]));
				
				date = str[3].split("/");
				LocalDate end_date = LocalDate.of(Integer.parseInt(date[2]), 
						Integer.parseInt(date[0]), Integer.parseInt(date[1]));
				
				date = str[5].split("/");
				LocalDate bookingDate = LocalDate.of(Integer.parseInt(date[2]), 
						Integer.parseInt(date[0]), Integer.parseInt(date[1]));
				DateInterval dateInterval = new DateInterval(start_date, end_date);
				
				int room_number = Integer.parseInt(str[1]);
				
				all_reservations.add(new Reservation((Guest)guests.get(str[0]), (Room)roomsByHashtable.get(room_number), 
						dateInterval, Integer.parseInt(str[4]), bookingDate));
			} 
			break;
		}
		
		fin.close();
	}
}
