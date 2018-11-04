public class Room {
	private int room_number;
	private int price;
	
	
	
	public Room(int room_number, int price) {
		super();
		this.room_number = room_number;
		this.price = price;
	}
	
	public int getRoom_number() {
		return room_number;
	}
	public void setRoom_number(int room_number) {
		this.room_number = room_number;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public boolean isEqual(Room r){
		return (this.room_number == r.room_number && this.price == r.price) ? true : false;
	}
	
	public String toString(){
		return room_number + " " + price;
	}
}
