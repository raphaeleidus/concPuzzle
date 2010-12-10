package rushhour; 

/*
 * This class specifies a vehicle "vehicle". The vehicle is placed at 
 * row "pos_x", column "pos_y", in direction "direction", and has 
 * length "len". 
 */

public class VehicleNode {
	int pos_x;
	int pos_y;
	char vehicle;
	int direction;
	int len;
	
	public VehicleNode(int x, int y, char v, int l, int d) {
		pos_x = x;
		pos_y = y;
		vehicle = v;
		direction = d;
		len = l;
	}
	
	public VehicleNode(int x, int y, char v, int d) {
		pos_x = x;
		pos_y = y;
		vehicle = v;
		direction = d;
	}
	
	public int getX() { return pos_x; }
	public int getY() { return pos_y; }
	public char getVehicle() { return vehicle; }
	public int getDirection() { return direction; }
	public int getLen() { return len; }
}