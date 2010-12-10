package checkerssolitaire; 

/*
 * This class specifies a new checker. The checker is placed at 
 * row "pos_x", column "pos_y".
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