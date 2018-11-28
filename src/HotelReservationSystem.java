import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
/**
 * 
 * @author Ngan Nguyen
 *
 */
public class HotelReservationSystem extends JFrame{
	private Hashtable guests;
	// hold existing guests info from guests.txt
	private ArrayList<Reservation> reservations;
		// hold all reservations from reservations.txt
	private ArrayList<Room> rooms;
		// hold all general rooms info from rooms.txt
	private Hashtable categorizedRooms;
	private Hashtable roomsByHashtable;
    private CardLayout cardLayout;
    private JPanel pages;
    private ArrayList<Day> days;
    private ArrayList<Integer> allRoomNumbers;
    
    public HotelReservationSystem(Hashtable guests, ArrayList<Reservation> reservations,
    		ArrayList<Room> rooms, Hashtable catagorizedRooms, Hashtable roomsByHashtable,
    		ArrayList<Day> days,  ArrayList<Integer> allRoomNumbers){
    
    	// INITIALIZE variables	
    	this.guests = guests;
    	this.reservations = reservations;
    	this.rooms = rooms;
    	this.categorizedRooms = catagorizedRooms;
    	this.roomsByHashtable = roomsByHashtable;
    	this.days = days;
    	this.allRoomNumbers = allRoomNumbers;
    	
    // MAIN MENU - choose to use the program as a manager or guest
    	JPanel mainMenu = new JPanel(new GridBagLayout());
    	JButton guestButton= new JButton("Guest");
		JButton managerButton = new JButton ("Manager");
		mainMenu.add(guestButton, new GridBagConstraints());
		mainMenu.add(managerButton, new GridBagConstraints());
		
	// MANAGER MENU
		JPanel managerMenu = new JPanel(new GridBagLayout());
		JLabel viewReservationLabel = new JLabel("View Reservation");
		JButton viewReserveByRoomButton = new JButton("View By Room");
		JButton viewReserveByDateButton = new JButton("View By Date");
		JButton managerBackToMainMenuButton = new JButton("Back");
		JButton loadReservationsButton = new JButton("Load Reservation");
		JButton quitProgramButton = new JButton("Quit");
		managerMenu.add(viewReservationLabel, new GridBagConstraints());
		managerMenu.add(viewReserveByRoomButton, new GridBagConstraints());
		managerMenu.add(viewReserveByDateButton, new GridBagConstraints());
		managerMenu.add(managerBackToMainMenuButton, new GridBagConstraints());
		managerMenu.add(loadReservationsButton, new GridBagConstraints());
		managerMenu.add(quitProgramButton, new GridBagConstraints());
		
	// GUEST MENU
		JPanel guestMenu = new JPanel(new GridBagLayout());
		JButton existingGuestButton= new JButton("Log In");
		JButton newGuestButton = new JButton ("Sign Up");
		JButton guestBackToMainMenuButton = new JButton("Back to Main Menu");
		guestMenu.add(existingGuestButton, new GridBagConstraints());
		guestMenu.add(newGuestButton, new GridBagConstraints());
		guestMenu.add(guestBackToMainMenuButton, new GridBagConstraints());
    	
    //MVC - VIEW RESERVATION BY ROOM	
		ReservationsByRoomModel reservationsByRoomModel = new ReservationsByRoomModel(reservations);
		ReservationsByRoomPanel reservationsByRoomPanel = new ReservationsByRoomPanel(reservationsByRoomModel, rooms);
		reservationsByRoomModel.addChangeListener(reservationsByRoomPanel);
		JPanel viewReservationByRoomPANEL = new JPanel(new BorderLayout());
		viewReservationByRoomPANEL.add(reservationsByRoomPanel, BorderLayout.CENTER);
		
		JPanel viewReservationByRoomButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton viewByRoomBackButton = new JButton("Back");
		viewReservationByRoomButtonPanel.add(viewByRoomBackButton);
		viewReservationByRoomPANEL.add(viewReservationByRoomButtonPanel, BorderLayout.SOUTH);
	
	// MVC - VIEW RESERVATION BY DATE
		//ReservationsByDatePanel reservationsByDatePanel = new ReservationsByDatePanel();
		
		
		
		CalendarModel calendar = new CalendarModel(days, allRoomNumbers);
		CalendarPanel calendarMainPanel = new CalendarPanel(calendar);
		calendar.attach(calendarMainPanel);
		
		JPanel calendarBottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton calendarBackButton = new JButton ("Back");
		calendarBottomPanel.add(calendarBackButton);
		
		JPanel calendarPanel = new JPanel(new BorderLayout());
		calendarPanel.add(calendarMainPanel, BorderLayout.NORTH);
		calendarPanel.add(calendarBackButton, BorderLayout.SOUTH);
		
	// GUEST - SIGN IN - 
		SignIn signInPanel = new SignIn(guests);
		JButton signInButton = new JButton("Sign In");
		signInPanel.add(signInButton);
		
	// GUEST - SIGN UP
		SignUp signUpPanel = new SignUp(guests);
		JButton signUpButton = new JButton("Sign Up");
		signUpPanel.add(signUpButton);
		
	// EXISTING GUEST MENU
		JPanel existingGuestMenu = new JPanel(new GridBagLayout());
    	JButton makeAReservationButton= new JButton("Make A Reservation");
		JButton viewCancelReservationButton = new JButton ("View/Cancel Reservations");
		existingGuestMenu.add(makeAReservationButton, new GridBagConstraints());
		existingGuestMenu.add(viewCancelReservationButton, new GridBagConstraints());
		
	//MVC - MAKING A RESERVATION	
		BookedRoomsByDatesModel bookedRoomsByDatesModel = new BookedRoomsByDatesModel(
				reservations, categorizedRooms, roomsByHashtable, calendar);
		
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
		ViewCancelReservations viewCancelReservations = new ViewCancelReservations(reservations, calendar);
		// Add a scroll
		JScrollPane scroll = new JScrollPane(viewCancelReservations, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		
		
		JButton viewCancelReservation_QUIT_Button = new JButton("Quit");
		JPanel cancelReservationButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cancelReservationButtonPanel.add(viewCancelReservation_QUIT_Button);
		
		JPanel viewCancelReservationsPanel = new JPanel(new BorderLayout());
		viewCancelReservationsPanel.add(scroll, BorderLayout.CENTER);
		viewCancelReservationsPanel.add(cancelReservationButtonPanel, BorderLayout.SOUTH);

	// RECEIPT FORMAT OPTIONS
		JPanel receiptFormatOptionsPanel  = new JPanel(new GridBagLayout());
    	JButton simpleFormatButton= new JButton("Simple Receipt");
		JButton comprehensiveFormatButton = new JButton ("Comprehensive Receipt");
		receiptFormatOptionsPanel.add(simpleFormatButton, new GridBagConstraints());
		receiptFormatOptionsPanel.add(comprehensiveFormatButton, new GridBagConstraints());
		
		
	// JPANEL DISPLAY RECEIPT - general for both simple & comprehensive
		Receipt receiptPanel = new Receipt();
		JButton receiptDoneButton = new JButton("Exit");
		JButton receiptBackButton = new JButton("Back");
		receiptPanel.add(receiptDoneButton);
		receiptPanel.add(receiptBackButton);
		
		
	// PANEL to hold all PAGES
		// Layout allows us to flip through different JPANEL
		pages = new JPanel(new CardLayout());
		pages.add(mainMenu, "mainMenu");
		pages.add(managerMenu, "managerMenu");
		pages.add(guestMenu, "guestMenu");
		pages.add(viewCancelReservationsPanel, "viewCancelReservationPanel");
		pages.add(bookingPanel, "guestBooking");
		pages.add(viewReservationByRoomPANEL, "managerReservationByROOM");
		
		pages.add(calendarPanel, "managerReservationByDATE");
		
		pages.add(signInPanel, "signIn");
		pages.add(signUpPanel, "signUp");
		pages.add(existingGuestMenu, "existingGuestMenu");
		pages.add(receiptFormatOptionsPanel, "receiptFormatOptions");
		pages.add(receiptPanel, "receiptPanel");
		
		cardLayout = (CardLayout) pages.getLayout();
		
		
	// ADD ACTIONLISTENER to button with different responsibility
		quitProgramButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// Export data & quit
				exportData(1,"./reservationsOUTPUT.txt");
			    exportData(2,"./guestsOUTPUT.txt");
				closeFrame();
			}
		});
		
		
		loadReservationsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("TO DO: link to load reservation");
			}
		});
		
		
		signInButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Guest guest = signInPanel.verifyAccount();
				if(guest != null){
					viewCancelReservations.setCurrentGuest(guest);
					bookedRoomsByDatesModel.setCurrentGuest(guest);
					cardLayout.show(pages, "existingGuestMenu");
				}
			}
		});
		
		
		signUpButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Guest guest = signUpPanel.createAccount();
				if(guest != null){
					bookedRoomsByDatesModel.setCurrentGuest(guest);
					viewCancelReservations.setCurrentGuest(guest);
					cardLayout.show(pages, "existingGuestMenu");
				}
			}
		});
		
		
		viewCancelReservationButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				viewCancelReservations.setReservations(reservations);
				viewCancelReservations.displayReservations();
				cardLayout.show(pages, "viewCancelReservationPanel");
			}
		});
		
		viewCancelReservation_QUIT_Button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bookedRoomsByDatesModel.setCurrentGuest(null);
				viewCancelReservations.clearPanel();
				cardLayout.show(pages, "mainMenu");
			}
		});
		
		receiptDoneButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bookedRoomsByDatesModel.setCurrentGuest(null);
				cardLayout.show(pages, "mainMenu");
			}
		});
		
		
		doneButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				getConfirmationPanel.resetFields();
				getBookingInfoPanel.resetFields();
				cardLayout.show(pages, "receiptFormatOptions");
			}
		});
		
		
		simpleFormatButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ArrayList<Reservation> res = new ArrayList<Reservation>();
				Reservation r = getConfirmationPanel.getNewReservation();
				if(r != null)
					res.add(r);
				receiptPanel.setGuestReservations(res);
				receiptPanel.printReceipt(new SimpleFormatter());
				cardLayout.show(pages, "receiptPanel");
			}
		});
		
		comprehensiveFormatButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				receiptPanel.setGuestReservations(viewCancelReservations.getGuestReservations());
				receiptPanel.printReceipt(new ComprehensiveFormatter());
				cardLayout.show(pages, "receiptPanel");
			}
		});
		
		viewByRoomBackButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				reservationsByRoomPanel.resetFields();
				cardLayout.show(pages, "managerMenu");
			}
		});
		
		calendarBackButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				calendarMainPanel.resetFields();
				cardLayout.show(pages, "managerMenu");
			}
		});
		
		
	// ADD ACTIONLISTENER to each button to flip through different pages
		addListenerToFlipPage(managerButton,"managerMenu");
		addListenerToFlipPage(managerBackToMainMenuButton,"mainMenu");
		addListenerToFlipPage(guestBackToMainMenuButton,"mainMenu");
		addListenerToFlipPage(guestButton, "guestMenu");
		addListenerToFlipPage(viewReserveByRoomButton, "managerReservationByROOM");
		addListenerToFlipPage(viewReserveByDateButton, "managerReservationByDATE");
		addListenerToFlipPage(existingGuestButton,"signIn");
		addListenerToFlipPage(newGuestButton,"signUp");
		addListenerToFlipPage(makeAReservationButton, "guestBooking");
		addListenerToFlipPage(receiptBackButton, "receiptFormatOptions");
		
		
		
		
		add(pages);
    }
    
    
   
    
    private void addListenerToFlipPage(JButton button, String nextPage){
    	button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cardLayout.show(pages, nextPage);
			}
		});
    }
    
    
    private void closeFrame(){
    	super.dispose();
    }
    
    private void exportData(int dataType, String filename){
    	BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filename));
        	
            switch(dataType){
            case 1: // Reservations
            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                for(Reservation r : reservations){
                	writer.write(r.getGuest().getUsername() + " "
                				+r.getRoom().getRoom_number() + " "
                				+r.getDateInterval().getStart_date() + " "
                				+r.getDateInterval().getEnd_date() + " "
                				+r.getTotal() + " "
                				+formatter.format(r.getBookingDate()) + "\n");
                }
            	break;
            case 2: // Guests
            	Guest g;
                Set<?> keySet = guests.keySet();
                for(String key : keySet.stream().toArray(String[]::new)){ 
                	g = (Guest)guests.get(key);
                	writer.write(g.name + "\n"
                				+g.username + " "
                				+g.password + "\n");
                }
            	break;
            }
            
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }
    }
    
	
}
