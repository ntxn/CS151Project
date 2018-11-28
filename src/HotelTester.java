import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import javax.swing.JFrame;

public class HotelTester {
	static Hashtable guests = new Hashtable(); 
			// hold existing guests info from guests.txt
	static ArrayList<Reservation> reservations = new ArrayList<Reservation>(); 
			// hold all reservations from reservations.txt
	static ArrayList<Room> rooms = new ArrayList<Room>(); 
			// hold all general rooms info from rooms.txt
	static Hashtable catagorizedRooms = new Hashtable();
	static Hashtable roomsByHashtable = new Hashtable();
	static ArrayList<Day> days = new ArrayList<Day>();
	static Hashtable existingDates = new Hashtable();
	static ArrayList<Integer> allRoomNumbers;
    
	public static void main(String[] args) throws FileNotFoundException{
		loadData("guests.txt", 1);
		loadData("rooms.txt", 2);
		
		allRoomNumbers = new ArrayList<Integer>();
		for(Room r : rooms)
			allRoomNumbers.add(r.getRoom_number());
		
		
		loadData("reservations.txt", 3);
		
		existingDates.clear();
		
		/* TEST CODE FOR LOADING DATA 
        System.out.println(guests.get("ngannguyen2").toString());
		
        for(Room room : rooms)
        	System.out.println(room.toString());
        
        for(Reservation r: reservations)
        	System.out.println(r.toString());
        
        for(Day d : days){
        	System.out.println(d.getDate() + " " + d.getReservations().size());
        }*/
        
        
        
		
		HotelSystem system = new HotelSystem(
				guests, reservations, rooms, catagorizedRooms, roomsByHashtable, 
				days, allRoomNumbers);
		
		// FRAME SET UP 
		system.setTitle("Hotel SEN");
		system.setVisible(true);
		system.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		system.setSize(700, 500);
    }
	
	/**
	 * Load data from a text file to data structure in Java
	 * @param fileName
	 * @param option 1: guests.txt, 2: rooms.txt, 3: reservations.txt
	 * @throws FileNotFoundException
	 */
	private static void loadData(String fileName, int option) throws FileNotFoundException{
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
				Reservation r = new Reservation((Guest)guests.get(str[0]), (Room)roomsByHashtable.get(room_number), 
						dateInterval, Integer.parseInt(str[4]), bookingDate);
				reservations.add(r);
				
				// Load to ArrayList<Day> days
				
				LocalDate date1 = start_date;
				Day d = new Day(allRoomNumbers, date1);
				while(date1.isBefore(end_date)){
					addReservationToEachDay(d, date1, r, room_number);
					date1 = date1.plusDays(1);
				}
					
			} 
			break;
		}
		
		
		fin.close();
	}
	

	private static void addReservationToEachDay(Day d, LocalDate date, Reservation r, int room_number){
		d = new Day(allRoomNumbers, date); 
		int index = days.size();
		if(index == 0 || !existingDates.contains(date)){
			d.addReservation(r);
			index =  addSortedDay(d);
			existingDates.put(date, index);
		}else{
			index = (int)existingDates.get(date);
			days.get(index).addReservation(r);
		}
	}
	
	
	private static int addSortedDay(Day d){
		if(days.size() == 0){
			days.add(d);
			return 0;
		}
		
		int index =0 ;
		for(int i = 0; i<days.size(); i++){
			if(d.getDate().isAfter(days.get(i).getDate()))
				index++;
			else
				break;
		}
		days.add(index, d);
		return index;
	}
	
}
