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
public class HotelSystem extends JFrame{
	private Hashtable guests; // hold existing guests info 
	private ArrayList<Reservation> reservations; // hold all reservations
	private ArrayList<Room> rooms; // hold all general rooms info
	private Hashtable categorizedRooms;
	private Hashtable roomsByHashtable;
    private CardLayout cardLayout;
    private JPanel pages; // hold all "Pages"
    private ArrayList<Day> days; // Hold reservations & available rooms for each day
    private ArrayList<Integer> allRoomNumbers;
    private boolean loadStatus;
    
    
    public HotelSystem(Hashtable guests, ArrayList<Reservation> reservations,
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
    	loadStatus = false;
    	JLabel status = new JLabel();
    	
    	
    // GRIDBAG CONSTRAINT
    	GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
    // MAIN MENU - choose to use the program as a manager or guest
    	JPanel mainMenu = new JPanel(new GridBagLayout());
    	JButton guestButton= new JButton("Guest");
		JButton managerButton = new JButton ("Manager");
		mainMenu.add(guestButton, new GridBagConstraints());
		mainMenu.add(managerButton, new GridBagConstraints());
		
	// MANAGER MENU
		JPanel managerMenu = new JPanel(new GridBagLayout());
		JPanel viewRoomPanel = new JPanel();
		JButton viewReserveByRoomButton = new JButton("View Reservations By Room");
		viewRoomPanel.add(viewReserveByRoomButton);
		
		JPanel viewDatePanel = new JPanel();
		JButton viewReserveByDateButton = new JButton("View Reservations By Date");
		viewDatePanel.add(viewReserveByDateButton);
		
		JPanel managerBackPanel = new JPanel();
		JButton managerBackButton = new JButton("Main Menu");
		managerBackPanel.add(managerBackButton);
		
		JPanel loadPanel = new JPanel();
		JButton loadReservationsButton = new JButton("Load Reservation");
		
		
		loadPanel.add(loadReservationsButton);
		loadPanel.add(status);
		
		JPanel quitPanel = new JPanel();
		JButton quitProgramButton = new JButton("Quit Program");
		quitPanel.add(quitProgramButton);
		
		managerMenu.add(loadPanel, gbc);
		managerMenu.add(viewRoomPanel, gbc);
		managerMenu.add(viewDatePanel, gbc);
		managerMenu.add(managerBackPanel, gbc);
		managerMenu.add(quitPanel, gbc);
		
		
		
	// GUEST MENU
		JPanel guestMenu = new JPanel(new GridBagLayout());
		JButton existingGuestButton= new JButton("Log In");
		JButton newGuestButton = new JButton ("Sign Up");
		JButton guestBackButton = new JButton("Main Menu");
		
		guestMenu.add(existingGuestButton, gbc);
		guestMenu.add(newGuestButton, gbc);
		guestMenu.add(guestBackButton, gbc);
		
    	
    //MVC - VIEW RESERVATION BY ROOM	
		ViewByRoomModel viewByRoomModel = new ViewByRoomModel(reservations);
		ViewByRoomPanel viewByRoomPanel = new ViewByRoomPanel(viewByRoomModel, rooms);
		viewByRoomModel.addChangeListener(viewByRoomPanel);
		
		JPanel viewByRoomButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton viewByRoomBackButton = new JButton("Back");
		viewByRoomButtonPanel.add(viewByRoomBackButton);
		
		JPanel viewReservationByRoomPANEL = new JPanel(new BorderLayout());
		viewReservationByRoomPANEL.add(viewByRoomPanel, BorderLayout.CENTER);
		viewReservationByRoomPANEL.add(viewByRoomButtonPanel, BorderLayout.SOUTH);
	
	// MVC - VIEW RESERVATION BY DATE
		CalendarModel calendar = new CalendarModel(days, allRoomNumbers);
		CalendarPanel calendarMainPanel = new CalendarPanel(calendar);
		calendar.attach(calendarMainPanel);
		
		JPanel calendarBottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
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
    	JButton bookingButton= new JButton("Make A Reservation");
		JButton viewCancelReservationButton = new JButton ("View/Cancel Reservations");
		existingGuestMenu.add(bookingButton, new GridBagConstraints());
		existingGuestMenu.add(viewCancelReservationButton, new GridBagConstraints());
		
	//MVC - MAKING A RESERVATION	
		BookingModel bookingModel = new BookingModel(
				reservations, categorizedRooms, roomsByHashtable, calendar);
		
		// VIEW & CONTROLLER 1
		BookingInfoPanel bookingInfoPanel = new BookingInfoPanel(bookingModel);
		bookingModel.addChangeListener(bookingInfoPanel);
		
		// VIEW & CONTROLLER 2
		BookingConfirmsPanel bookingConfirmsPanel = new BookingConfirmsPanel(bookingModel);
		bookingModel.addChangeListener(bookingConfirmsPanel);
		
		// Button to add more reservations from the same guest
		JButton moreReservation = new JButton("More Reservation");
		moreReservation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bookingModel.resetFields();
				bookingInfoPanel.resetFields();
				bookingConfirmsPanel.resetFields();
			}
		});
		
		// DONE button to go to view receipt page
		JButton doneButton = new JButton("Done");
		
		
		JPanel endReservationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		endReservationPanel.add(moreReservation);
		endReservationPanel.add(doneButton);
		
		JPanel bookingPanel = new JPanel();
		bookingPanel.setLayout(new BorderLayout());
		bookingPanel.add(bookingInfoPanel, BorderLayout.NORTH);
		bookingPanel.add(bookingConfirmsPanel, BorderLayout.CENTER);
		bookingPanel.add(endReservationPanel, BorderLayout.SOUTH);
		
		
	//VIEW/CANCEL RESERVATIONS BY GUEST
		GuestReservations guestReservations = new GuestReservations(reservations, calendar);
		// Add a scroll
		JScrollPane scroll = new JScrollPane(guestReservations, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		
		
		JButton guestReservations_QUIT_Button = new JButton("Quit");
		JPanel guestReservationsButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		guestReservationsButtonPanel.add(guestReservations_QUIT_Button);
		
		JPanel guestReservationsPanel = new JPanel(new BorderLayout());
		guestReservationsPanel.add(scroll, BorderLayout.CENTER);
		guestReservationsPanel.add(guestReservationsButtonPanel, BorderLayout.SOUTH);

	// RECEIPT FORMAT OPTIONS
		JPanel receiptFormatOptionsPanel  = new JPanel(new GridBagLayout());
    	JButton simpleFormatButton= new JButton("Simple Receipt");
		JButton comprehensiveFormatButton = new JButton ("Comprehensive Receipt");
		receiptFormatOptionsPanel.add(simpleFormatButton, new GridBagConstraints());
		receiptFormatOptionsPanel.add(comprehensiveFormatButton, new GridBagConstraints());
		
		
	// JPANEL DISPLAY RECEIPT - general for both simple & comprehensive
		Receipt receipt = new Receipt();
		JScrollPane receiptScroll = new JScrollPane(receipt, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JButton receiptDoneButton = new JButton("Exit");
		JButton receiptBackButton = new JButton("Back");
		JPanel receiptPanel = new JPanel(new BorderLayout());
		JPanel receiptButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		receiptButtonPanel.add(receiptBackButton);
		receiptButtonPanel.add(receiptDoneButton);
		receiptPanel.add(receiptScroll, BorderLayout.CENTER);
		receiptPanel.add(receiptButtonPanel, BorderLayout.SOUTH);
		
		
		
	// PANEL to hold all PAGES
		// Layout allows us to flip through different JPANEL
		pages = new JPanel(new CardLayout());
		pages.add(mainMenu, "mainMenu");
		pages.add(managerMenu, "managerMenu");
		pages.add(guestMenu, "guestMenu");
		pages.add(guestReservationsPanel, "guestReservationPanel");
		pages.add(bookingPanel, "guestBooking");
		pages.add(viewReservationByRoomPANEL, "viewByROOM");
		pages.add(calendarPanel, "viewByDATE");
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
			public void actionPerformed(ActionEvent e) {
				if(loadStatus == true)
					status.setText("Reservation Loaded");
				else{
					status.setText("Done Loading");
					reverseStatus();
				}
			}
		});
		
		
		signInButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Guest guest = signInPanel.verifyAccount();
				if(guest != null){
					guestReservations.setCurrentGuest(guest);
					bookingModel.setCurrentGuest(guest);
					cardLayout.show(pages, "existingGuestMenu");
				}
			}
		});
		
		
		signUpButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Guest guest = signUpPanel.createAccount();
				if(guest != null){
					bookingModel.setCurrentGuest(guest);
					guestReservations.setCurrentGuest(guest);
					cardLayout.show(pages, "existingGuestMenu");
				}
			}
		});
		
		
		viewCancelReservationButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				guestReservations.setReservations(reservations);
				guestReservations.displayReservations();
				cardLayout.show(pages, "guestReservationPanel");
			}
		});
		
		guestReservations_QUIT_Button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bookingModel.setCurrentGuest(null);
				guestReservations.clearPanel();
				cardLayout.show(pages, "mainMenu");
			}
		});
		
		receiptDoneButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bookingModel.setCurrentGuest(null);
				cardLayout.show(pages, "mainMenu");
			}
		});
		
		
		doneButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bookingConfirmsPanel.resetFields();
				bookingInfoPanel.resetFields();
				cardLayout.show(pages, "receiptFormatOptions");
			}
		});
		
		
		simpleFormatButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ArrayList<Reservation> res = new ArrayList<Reservation>();
				Reservation r = bookingConfirmsPanel.getNewReservation();
				if(r != null)
					res.add(r);
				receipt.setGuestReservations(res);
				receipt.printReceipt(new SimpleFormatter());
				cardLayout.show(pages, "receiptPanel");
			}
		});
		
		comprehensiveFormatButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				receipt.setGuestReservations(guestReservations.getGuestReservations());
				receipt.printReceipt(new ComprehensiveFormatter());
				cardLayout.show(pages, "receiptPanel");
			}
		});
		
		viewByRoomBackButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				viewByRoomPanel.resetFields();
				cardLayout.show(pages, "managerMenu");
			}
		});
		
		calendarBackButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				calendarMainPanel.resetFields();
				cardLayout.show(pages, "managerMenu");
			}
		});
		
		
		
		
		managerBackButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				status.setText("");
				cardLayout.show(pages, "mainMenu");
			}
		});
		
		addListenerResetstatusLabel(viewReserveByDateButton, "viewByDATE", status);
		addListenerResetstatusLabel(viewReserveByRoomButton, "viewByROOM", status);
		
	// ADD ACTIONLISTENER to each button to flip through different pages
		addListenerToFlipPage(managerButton,"managerMenu");
		addListenerToFlipPage(guestBackButton,"mainMenu");
		addListenerToFlipPage(guestButton, "guestMenu");
		addListenerToFlipPage(existingGuestButton,"signIn");
		addListenerToFlipPage(newGuestButton,"signUp");
		addListenerToFlipPage(bookingButton, "guestBooking");
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
    
	
    private void reverseStatus(){
    	loadStatus = !loadStatus;
    }
   
    private void addListenerResetstatusLabel(JButton button, String nextPage, JLabel status){
    	button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(loadStatus == false)
					status.setText("Reservations are not loaded");
				else{
					status.setText("");
					cardLayout.show(pages, nextPage);
				}
				
			}
		});
    }
}
