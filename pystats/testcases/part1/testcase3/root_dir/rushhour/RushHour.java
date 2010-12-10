package rushhour;

import java.util.LinkedList;

import framework.*;

/*
 * A RushHour puzzle is a 6x6 matrix of characters.
 * The RushHour puzzle implements the Puzzle interface methods:
 * 		void initialPosition() 
 * 		isGoal() 
 *      LinkedList legalMoves(Node node)
 *                   
 * The RushHour puzzle implements several helper methods
 *      RushHour move(Move m)
 *      boolean emptyMove(Move m, Node node)
 *      boolean hasVehicle(pos_x, pos_y, vehicle, length, direction)
 *      void placeVehicle(pos_x, pos_y, vehicle, length, direction)
 *      public LinkedList transform()
 *            
 * The RushHour puzzle implements equal and hashCode. The implementations override the 
 * default ones inherited from Object.                      
 * 	              
 */

public class RushHour implements Puzzle {
	char[][] matrix = new char[6][6];

	
	public RushHour() {
		for (int i = 0; i<6; i++) {
			for (int j = 0; j<6; j++) {
				matrix[i][j] = '0';
			}
		}
	}
	/*
	 * Reads an initial position from a list of predefined configurations.
	 * The initial position comes as a list of vehicle placements.
	 * An iterator over the list of vehicle placements reads each 
	 * vehicle placement (given in a VehicleNode object) and places
	 * the vehicle on the matrix by calling helper placeVehicle(...). 
	 * @see framework.Puzzle#initialPosition()
	 */
	public void initialPosition() {
		
		/*
	    placeVehicle(0,2,'O',3,1);
		placeVehicle(2,3,'X',2,0);
		placeVehicle(2,5,'P',3,1);
		placeVehicle(3,1,'A',2,0);
		placeVehicle(3,3,'Q',3,1);
		placeVehicle(4,1,'J',2,1);
		placeVehicle(5,4,'C',2,0);
		*/
		
		Configurations.addAll();
		
		LinkedList l = (LinkedList) Configurations.theConfigs.get(0);
		for (Object o : l) {
			VehicleNode vNode = (VehicleNode) o;
	    	placeVehicle(vNode.getX(), vNode.getY(), vNode.getVehicle(), vNode.getLen(), vNode.getDirection());
	    }
		
		
	
	}
	/*
	 * Places vehicle "vehicle" of length "length" in direction "direction"
	 * at row "pos_x", column "pos_y" 
	 * 
	 * direction (0,1): 0 means horizontal and 1 means vertical
	 */
	// TODO: direction should be coded with symbolic constants
	
	private void placeVehicle(int pos_x, int pos_y, char vehicle, int length, int direction) {
		if (direction == 0) { // horizontal placement
			for (int i = pos_y; i < pos_y+length; i++) 
				matrix[pos_x][i] = vehicle;			
		}
		else { // vertical placement
			for (int i = pos_x; i < pos_x+length; i++)
				matrix[i][pos_y] = vehicle;
		}
	}
	
	/*
	 * Checks if there is a vehicle "vehicle" of length "length" placed at row "pos_x", column "pos_y"
	 * in direction "direction" 
	 */
	
	private boolean hasVehicle(int pos_x, int pos_y, char vehicle, int length, int direction) {
		if (direction == 0) { // horizontal placement
			for (int i = pos_y; i < pos_y+length; i++) 
				if (matrix[pos_x][i] != vehicle) return false;			
		}
		else { // vertical placement
			for (int i = pos_x; i < pos_x+length; i++)
				if (matrix[i][pos_y] != vehicle) return false;
		}
		
		return true;
	}
	
	
	/*
	 * Checks if the puzzle is in a "solved" position.
	 * Checks if the Ice Cream truck (vehicle 'X'), which occupies 
	 * cells pos_X-1 and pos_X at row 2, is free to go --- 
	 * i.e., cells 2,pos_X+1 through 2,5 are empty 
	 */	
	public boolean isGoal() {
		int pos_X = -1;
		for (int i=0; i<6; i++) {
			if (matrix[2][i] == 'X') {
				pos_X = i+1;
				break;
			}
		}
		if (pos_X == -1) return false;
		for (int i = pos_X+1; i<6; i++ ) {
			if (matrix[2][i] != '0') return false;
		}
		return true;
	}

	/*
	 * Transforms the current matrix into a list of VehicleNodes
	 * Each VehicleNode contains the row, column, vehicle and direction 
	 * of vehicle placement. Needed by RushHourPanel.  
	 */
	
	public LinkedList transform() {
		LinkedList theList = new LinkedList();
		
		for (int i=0; i<6; i++)
			for (int j=0; j<6; j++) {
				//if (matrix[i][j] == '0') continue; 
				
				if ((j<5) && ((j==0) || (matrix[i][j-1] != matrix[i][j])) && (matrix[i][j] != '0') && (matrix[i][j] == matrix[i][j+1])) {
					VehicleNode vNode = new VehicleNode(i,j,matrix[i][j],0);					
					theList.add(vNode);
				}
				if ((i<5) && ((i==0) || (matrix[i-1][j] != matrix[i][j])) && (matrix[i][j] != '0') && (matrix[i+1][j] == matrix[i][j])) {
					VehicleNode vNode = new VehicleNode(i,j,matrix[i][j],1);					
					theList.add(vNode);
				}
			}
		
		
		return theList;
	}
	
	/*
	 * Checks if move "move" is worth taking; this is determined by looking back at the history of moves
	 * provided by linked list "node".  Has substantial effect on the length of the computed solution.
	 * 
	 */
	
	private boolean emptyMove(Node node, Move move) {		
	
		// some moves are not worth, and this can be determined by looking carefully at the history of moves			
		
		if (move.direction == 0) { // if taking a left move
			int len = ((move.pos_y+1<6) && (matrix[move.pos_x][move.pos_y-1] == matrix[move.pos_x][move.pos_y+1])) ? 3 : 2;
			char vehicle = matrix[move.pos_x][move.pos_y];
			
			for (Node n = node; n != null; n = n.getPrev()) {
				RushHour rhNode = (RushHour) n.getPos();
				if ((rhNode.matrix[move.pos_x][move.pos_y-1] == '0') && rhNode.hasVehicle(move.pos_x,move.pos_y,vehicle,len,0)) continue;
				else if (rhNode.hasVehicle(move.pos_x,move.pos_y-1,vehicle,len,0) && (rhNode.matrix[move.pos_x][move.pos_y+len-1] == '0')) return true;
				else return false;
			}
		}
		else if (move.direction == 1) { // right
			int len = ((move.pos_y-1>-1) && (matrix[move.pos_x][move.pos_y+1] == matrix[move.pos_x][move.pos_y-1])) ? 3 : 2;
			char vehicle = matrix[move.pos_x][move.pos_y];
			
			for (Node n = node; n != null; n = n.getPrev()) {
				RushHour rhNode = (RushHour) n.getPos();
				if ((rhNode.matrix[move.pos_x][move.pos_y+1] == '0') && rhNode.hasVehicle(move.pos_x,move.pos_y-len+1,vehicle,len,0)) continue;
				else if (rhNode.hasVehicle(move.pos_x,move.pos_y-len+2,vehicle,len,0) && (rhNode.matrix[move.pos_x][move.pos_y-len+1] == '0')) return true;
				else return false;
			}
		}
		else if (move.direction == 2) { // up 
			int len = ((move.pos_x+1<6) && (matrix[move.pos_x-1][move.pos_y] == matrix[move.pos_x+1][move.pos_y])) ? 3 : 2;
			char vehicle = matrix[move.pos_x][move.pos_y];
			
			for (Node n = node; n != null; n = n.getPrev()) {
				RushHour rhNode = (RushHour) n.getPos();
				if ((rhNode.matrix[move.pos_x-1][move.pos_y] == '0') && rhNode.hasVehicle(move.pos_x,move.pos_y,vehicle,len,1)) continue;
				else if (rhNode.hasVehicle(move.pos_x-1,move.pos_y,vehicle,len,1) && (rhNode.matrix[move.pos_x+len-1][move.pos_y] == '0')) return true;
				else return false;
			}
		}
		else if (move.direction == 3) { // down
			int len = ((move.pos_x-1>-1) && (matrix[move.pos_x+1][move.pos_y] == matrix[move.pos_x-1][move.pos_y])) ? 3 : 2;
			char vehicle = matrix[move.pos_x][move.pos_y];
			
			for (Node n = node; n != null; n = n.getPrev()) {
				RushHour rhNode = (RushHour) n.getPos();
				if ((rhNode.matrix[move.pos_x+1][move.pos_y] == '0') && rhNode.hasVehicle(move.pos_x-len+1,move.pos_y,vehicle,len,1)) continue;
				else if (rhNode.hasVehicle(move.pos_x-len+2,move.pos_y,vehicle,len,1) && (rhNode.matrix[move.pos_x-len+1][move.pos_y] == '0')) return true;
				else return false;
			}
		}
		
		
		return false;
	}
	
	
	/*
	 * Returns a list of all positions that can be reached from the current position 
	 * by taking one legal move. Parameter "node" is needed only for the purposes of
	 * emptyMove (if a potential move is determined to be empty, then it is not 
	 * placed on the list).
	 */
	
	public LinkedList legalMoves(Node node) {
		
		
		LinkedList theSet = new LinkedList();
		for (int i=0; i<6; i++) {
			for (int j=1; j<5; j++) { 
				if ((matrix[i][j-1]=='0') && (matrix[i][j]!='0') && (matrix[i][j]==matrix[i][j+1])) {
				    Move theMove = new Move(i,j,0);
					RushHour position = move(theMove);	
					if (!position.emptyMove(node, theMove)) theSet.add(position); // a left move
				}
			}
		}
		for (int i=0; i<6; i++) {
			for (int j=4; j>0; j--) {
				if ((matrix[i][j+1]=='0') && (matrix[i][j]!='0') && (matrix[i][j]==matrix[i][j-1])) {
					Move theMove = new Move(i,j,1);
					RushHour position = move(theMove);
					if (!position.emptyMove(node, theMove)) theSet.add(position); // a right move	
				}
			}
		}
		
		for (int i=1; i<5; i++) {
			for (int j=0; j<6; j++) { 
				if ((matrix[i-1][j]=='0') && (matrix[i][j]!='0') && (matrix[i][j]==matrix[i+1][j])) {
					Move theMove = new Move(i,j,2);
					RushHour position = move(theMove);
					if (!position.emptyMove(node, theMove)) theSet.add(position); // a up move
				}
			}
		}
		for (int i=4; i>0; i--) {
			for (int j=0; j<6; j++) {
				if ((matrix[i+1][j]=='0') && (matrix[i][j]!='0') && (matrix[i][j]==matrix[i-1][j])) {
					Move theMove = new Move(i,j,3);
					RushHour position = move(theMove);
					if (!position.emptyMove(node, theMove)) theSet.add(position); // a down move	
				}
			}
		}
		return theSet;
	}

	
	/*
	 * Creates a new RushHour puzzle, which represents the new position resulting 
	 * from taking move "theMove".
	 * The returned RushHour puzzle (newPosition) is the same as the current one, except in 
	 * exactly one vehicle which is moved left, right, up or down by one cell. 
	 */
	
	private RushHour move(Move theMove) {
		RushHour newPosition = this.clone();
		
		if (theMove.direction == 0) {
			int len;
			if ((theMove.pos_y+2<6) &&
			    (newPosition.matrix[theMove.pos_x][theMove.pos_y] == newPosition.matrix[theMove.pos_x][theMove.pos_y+2]))
				len = 3;
			else 
				len = 2;
			char vehicle = matrix[theMove.pos_x][theMove.pos_y];
			newPosition.placeVehicle(theMove.pos_x,theMove.pos_y-1,vehicle,len,0);
			newPosition.matrix[theMove.pos_x][theMove.pos_y+len-1] = '0';
		}
		else if (theMove.direction == 1) {
			int len;
			if ((theMove.pos_y-2>-1) && 
			    (newPosition.matrix[theMove.pos_x][theMove.pos_y] == newPosition.matrix[theMove.pos_x][theMove.pos_y-2]))
				len = 3;
			else 
				len = 2;
			char vehicle = matrix[theMove.pos_x][theMove.pos_y];
			newPosition.placeVehicle(theMove.pos_x,theMove.pos_y-len+2,vehicle,len,0);
			newPosition.matrix[theMove.pos_x][theMove.pos_y-len+1] = '0';
		}
		else if (theMove.direction == 2) {
			int len;
			if ((theMove.pos_x+2<6) && 
			    (newPosition.matrix[theMove.pos_x][theMove.pos_y] == newPosition.matrix[theMove.pos_x+2][theMove.pos_y]))
				len = 3;
			else 
				len = 2;
			char vehicle = matrix[theMove.pos_x][theMove.pos_y];
			newPosition.placeVehicle(theMove.pos_x-1,theMove.pos_y,vehicle,len,1);
			newPosition.matrix[theMove.pos_x+len-1][theMove.pos_y] = '0';
		}
		else if (theMove.direction == 3) {
			int len;
			if ((theMove.pos_x-2>-1) && 
			    (newPosition.matrix[theMove.pos_x][theMove.pos_y] == newPosition.matrix[theMove.pos_x-2][theMove.pos_y]))
				len = 3;
			else 
				len = 2;
			char vehicle = matrix[theMove.pos_x][theMove.pos_y];
			newPosition.placeVehicle(theMove.pos_x-len+2,theMove.pos_y,vehicle,len,1);
			newPosition.matrix[theMove.pos_x-len+1][theMove.pos_y] = '0';
		}
		
		
		
		
		return newPosition;
	}
	
	public RushHour clone() {
		
		RushHour newPosition = new RushHour();
		for (int i=0; i<6; i++)
			for (int j=0; j<6; j++) 
				newPosition.matrix[i][j] = matrix[i][j];
		
		return newPosition;
	}
	
	/*
	 * equals and hashCode implement value equality for RushHour objects.
	 * Two objects are equal if their matrices are equal. The methods override
	 * the default Object.equals and hashCode which implement reference equality.
	 * Note: every class that overrides equal must override hashCode as well!!!
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	
	public boolean equals(Object o) {
	    
		RushHour other = (RushHour) o;
		for (int i=0; i<6; i++)
			for (int j=0; j<6; j++) 
				if (matrix[i][j] != other.matrix[i][j]) return false;
		
		return true;
	}
	
	public int hashCode() {
		int code=0;
		for (int i=0; i<6; i++)
			for (int j=0; j<6; j++) 
				code = code+matrix[i][j];
		return code;
	}
	

	/*
	 * The class encapsulates a move. Each move is defined as
	 * row (pos_x), column (pos_y), direction (0 - left, 1 - right, 2 - up, 3 - down).
	 * The vehicle at pos_x, pos_y is moved one block in direction direction.
	 * TODO: direction should be coded with meaningful symbolic constants!
	 */
	
	class Move {
		int pos_x;
		int pos_y;
		int direction;
		int step = 1;
		
		Move(int pos_x, int pos_y, int direction) {
			this.pos_x = pos_x;
			this.pos_y = pos_y;
			this.direction = direction;
		}
		
	}

	
}