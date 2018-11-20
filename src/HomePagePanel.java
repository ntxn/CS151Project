import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;


public class HomePagePanel extends JFrame {
	public static void main(String args[]) {
		  
		  HomePage home = new HomePage();
		 }
}

class HomePage extends JFrame
{
	public HomePage()
	{
		final int FRAME_WIDTH=700;
		final int FRAME_HEIGHT=500;
  
		setTitle("Hotal Reservation System");
		JButton guestButton= new JButton("Guest");
		JButton managerButton = new JButton ("Manager");
  
		guestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				guestSignIn nextPage=new guestSignIn(); 
				nextPage.setVisible(true);
				dispose();
			}
		});
  
		managerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				guestSignIn nextPage=new guestSignIn(); 
				nextPage.setVisible(true);
				dispose();
			}
		});
		setLayout( new GridBagLayout() );
		add(guestButton, new GridBagConstraints());
		add(managerButton, new GridBagConstraints());
 
		setVisible(true);
		setSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}//home page class
class guestSignIn extends JFrame
{
	public guestSignIn()
	{
		final int FRAME_WIDTH=700;
		final int FRAME_HEIGHT=500;
  
		setTitle("Guest Sign In");
		JButton signInButton= new JButton("Sign In");
		JButton backButton = new JButton ("<< Previous Page");
  
		JTextField userID = new JTextField("Enter User ID");
		JTextField pw = new JTextField("Enter Password");
		
		
		signInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				guestView nextPage=new guestView(); 
				nextPage.setVisible(true);
				dispose();
			}
		});
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				HomePage previousPage=new HomePage(); 
				previousPage.setVisible(true);
				dispose();
			}
		});
  
		
		setLayout( new GridBagLayout() );
		add(userID);
		add(pw);
		
		add(backButton, new GridBagConstraints());
		add(signInButton, new GridBagConstraints());
		
 
		setVisible(true);
		setSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
}//guest sign in class

class guestView extends JFrame
{
	public guestView()
	{
		final int FRAME_WIDTH=700;
		final int FRAME_HEIGHT=500;
  
		setTitle("Hotel Reservation System (Guest)");
		JButton reservationButton= new JButton("Make a Reservation");
		JButton viewAndCancelButton = new JButton ("View/Cancel a Reservation");
		//JButton backButton = new JButton ("<< Previous Page");
  
		reservationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				makeReservation nextPage=new makeReservation(); 
				nextPage.setVisible(true);
				dispose();
			}
		});
		
		viewAndCancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				
				HotelReservationSystem next = new HotelReservationSystem();
				next.setVisible(true);
				dispose();
				
			}
		});
  
		setLayout( new GridBagLayout() );
		//add(backButton, new GridBagConstraints());
		add(reservationButton, new GridBagConstraints());
		add(viewAndCancelButton, new GridBagConstraints());
 
		setVisible(true);
		setSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
}//guest view class


class makeReservation extends JFrame 
{
	private static HashMap<String, Guest> guests = new HashMap<String, Guest>(); // hold existing guest info from guests.txt
	private static ArrayList<Reservation> all_reservations = new ArrayList<Reservation>(); // hold all reservations from reservations.txt
    private static ArrayList<Room> rooms = new ArrayList<Room>(); // hold all general rooms info from rooms.txt
    private static HashMap<Integer, ArrayList<Room>> catagorizedRooms =
    		new HashMap<Integer, ArrayList<Room>>();
    private static String currentUser; // keep track of which user is using the program
    
	public static final int DEFAULT_WIDTH = 700;
	public static final int DEFAULT_HEIGHT = 500;
 
	public makeReservation()
 	{	
		ReservationsByRoomModel reservationsByRoomModel = new ReservationsByRoomModel(all_reservations);
		ReservationsByRoomPanel reservationsByRoomPanel = new ReservationsByRoomPanel(reservationsByRoomModel, rooms);
		reservationsByRoomModel.addChangeListener(reservationsByRoomPanel);
		
		BookedRoomsByDatesModel bookedRoomsByDatesModel = new BookedRoomsByDatesModel(all_reservations, catagorizedRooms);
		GetBookingInfoPanel getBookingInfoPanel = new GetBookingInfoPanel(bookedRoomsByDatesModel);
		bookedRoomsByDatesModel.addChangeListener(getBookingInfoPanel);
		
		setTitle("Make Reservation");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		//add(reservationsByRoomPanel);
		add(getBookingInfoPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 	}
  
}//make reservation


