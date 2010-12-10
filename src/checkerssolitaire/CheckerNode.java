package checkerssolitaire; 

/*
 * This class specifies a vehicle "vehicle". The vehicle is placed at 
 * row "pos_x", column "pos_y", in direction "direction", and has 
 * length "len". 
 */

class CheckerNode {
	int pos_x;
	int pos_y;
	char type;
	
	public CheckerNode(int x, int y, char t) {
		pos_x = x;
		pos_y = y;
		type = t;
	}
	
	public int getX() { return pos_x; }
	public int getY() { return pos_y; }
	public char getType() { return type; }
}