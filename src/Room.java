/**
 * Hold info for a room including a room# & price
 * @author Ngan Nguyen
 *
 */
public class Room {
	private int room_number;
	private int price;
	
	/**
	 * Constructor to create a Room Object
	 * @param room_number
	 * @param price
	 */
	public Room(int room_number, int price) {
		super();
		this.room_number = room_number;
		this.price = price;
	}
	
	/**
	 * get room#
	 * @return room_number
	 */
	public int getRoom_number() {
		return room_number;
	}
	
	/**
	 * set room#
	 * @param room_number
	 */
	public void setRoom_number(int room_number) {
		this.room_number = room_number;
	}
	
	/**
	 * get price of the room
	 * @return price
	 */
	public int getPrice() {
		return price;
	}
	
	/**
	 * set the price for the room
	 * @param price
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	
	/**
	 * Get String for the room info
	 */
	public String toString(){
		return room_number + " " + price;
	}
	
	public boolean equals(Room r){
		if(this.room_number == r.getRoom_number() && this.price == r.getPrice())
			return true;
		return false;
	}
}
