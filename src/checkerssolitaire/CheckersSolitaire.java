package checkerssolitaire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import java.util.Set;

import java.util.HashSet;

import javax.swing.JFrame;

import checkerssolitaire.Configurations;

import checkerssolitaire.CheckerNode;

import checkerssolitaire.CheckersSolitaire.Move;

import java.awt.Point;

import framework.*;

/*
 * A CheckersSolitaire puzzle is a 7x7 matrix of characters.
 * The CheckersSolitaire puzzle implements the Puzzle interface methods:
 * 		void initialPosition() 
 * 		isGoal() 
 *      LinkedList legalMoves(Node node)
 *                   
 * The CheckersSolitaire puzzle implements several helper methods
 *      CheckersSolitaire move(Move m)
 *      boolean hasChecker(pos_x, pos_y)
 *      int getLastDirection(node)
 *      checkersSolitaire clone()
 *      void addChecker(pos_x, pos_y)
 *      public LinkedList transform()
 *            
 * The CheckersSolitaire puzzle implements equal and hashCode. The implementations override the 
 * default ones inherited from Object.                      
 * 	              
 */


public class CheckersSolitaire implements Puzzle {
	char[][] matrix = new char[7][7];

	
	public CheckersSolitaire() {
		for (int i = 0; i<7; i++) {
			for (int j = 0; j<7; j++) {
				if((j<2 || j>4) && (i<2 || i>4)) {
					matrix[i][j] = 'B';
				} else {
					matrix[i][j] = 'O';
				}
			}
		}
		
	}
	
	
	public void addChecker(int x, int y) {
		matrix[x][y] = 'C';
	}
	/*
	 * Reads in the initial position of the checkers based on the
	 * version of the puzzle being used as indicated in the 
	 * initialization of Linkedlist l
	 */
	public void initialPosition() {
		Configurations.addAll();
		
		LinkedList l = (LinkedList) Configurations.theConfigs.get(6);
		for (Object o : l) {
			CheckerNode cNode = (CheckerNode) o;
			addChecker(cNode.getX(), cNode.getY());
		}	
	}
	/*
	 * Checks to see if a checker is at the given position
	 */
	
	private boolean hasChecker(int pos_x, int pos_y) {
		if(pos_x<0 || pos_y<0 || pos_x>6 || pos_y>6)
			return false;
		return matrix[pos_x][pos_y] == 'C';
	}
	
	
	/*
	 * Checks if the puzzle is in a "solved" position.
	 * Checks if the puzzle is empty except for the middle position
	 */	
	public boolean isGoal() {
		if (this.hasChecker(3, 3)) {
			for(int i=0; i<7; i++){
				for(int j=0; j<7; j++){
					if(i==3 && j==3) continue;
					if(this.hasChecker(i, j)) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
		
//		int checkers = 0;
//		int limit = 10;
//		for(int i=0;i<7;i++) {
//			for(int j=0;j<7;j++){
//				if(this.hasChecker(i,j)) checkers++;
//				if(checkers >= limit) return false;
//			}
//		}
//		return checkers < limit;
	}

	/*
	 * Transforms the current matrix into a linked list of CheckerNodes
	 * Each CheckerNode contains the row, column, vehicle and direction 
	 * of vehicle placement. Needed by CheckersSolitairePanel.  
	 */
	
	private LinkedList transform() {
		LinkedList theList = new LinkedList();
		
		for (int i=0; i<7; i++) {
			for (int j=0; j<7; j++) {
				if ((j<2 || j>4) && (i<2 || i>4)) {
					continue;
				}
				CheckerNode cNode = new CheckerNode(i,j,matrix[i][j]);
				theList.add(cNode);
			}
		}
		
		return theList;
	}

	
	/*
	 * drawSolution takes a linked list of Nodes representing the solution, 
	 * crates a frame and draws the solution.
	 */
	
	public void drawSolution(LinkedList theList) {
		
		JFrame frame = new JFrame("CheckersSolitare");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        
        frame.setVisible(true);
        
        for (Object o : theList) {
        	CheckersSolitaire puzzle = (CheckersSolitaire) o;
        	CheckersSolitairePanel checkersSolitarePanel = new CheckersSolitairePanel(puzzle.transform());
        	frame.getContentPane().add(checkersSolitarePanel);
        	frame.show();
        	        			
        	// an idle loop that delays the display of a position; makes moves visible
        	long t0,t1;
        	     
        	t0=System.currentTimeMillis();
        	     
        	do {
        		t1=System.currentTimeMillis();
            }
     	    while (t1-t0<500);
        	        
        }
	}
	
	/*
	 * Gets the direction of the previous move at the node, and then reorders the choices of direction randomly
	 * placing the direction of the previous move at the end of the list
	 */
	private int getLastDirection(Node node) {
		Node pNode = node.getPrev();
		if (pNode == null) {
			return 3;
		}
		CheckersSolitaire prev = (CheckersSolitaire) pNode.getPos();
		for (int i=0; i<7; i++){
			for (int j=0; j<7; j++){
				if (prev.matrix[i][j]=='O' && this.matrix[i][j]=='C') {
					//check for left move
					if (i<5||(i<3 && (j<2 || j>4))){ //possible for the move to have come from the right
						if(prev.matrix[i+1][j] == 'C' && prev.matrix[i+2][j] == 'C' && this.matrix[i+1][j] == 'O' && this.matrix[i+2][j] == 'O'){
							return 0;
						}
					}
					//check for right move
					if (i>1||(i>3 && (j<2 || j>4))){ //possible for the move to have come from the left
						if(prev.matrix[i-1][j] == 'C' && prev.matrix[i-2][j] == 'C' && this.matrix[i-1][j] == 'O' && this.matrix[i-2][j] == 'O'){
							return 1;
						}
					}
					//check for down move
					if (j<5||(j<3 && (i<2 || i>4))){ //possible for the move to have come from above
						if(prev.matrix[i][j+1] == 'C' && prev.matrix[i][j+2] == 'C' && this.matrix[i][j+1] == 'O' && this.matrix[i][j+2] == 'O'){
							return 3;
						}
					}
					//check for up move
					if (j>1||(j>3 && (i<2 || i>4))){ //possible for the move to have come from the right
						if(prev.matrix[i][j-1] == 'C' && prev.matrix[i][j-2] == 'C' && this.matrix[i][j-1] == 'O' && this.matrix[i][j-2] == 'O'){
							return 2;
						}
					} else {
						System.out.println("THERE IS NO REASON FOR IT TO BE HERE!!!! WTF????");
						return 3;
					}
					return 3;
				}
			}
		}
		return 3;
	}
	
	/*
	 * Returns a list of all positions that can be reached from the current position 
	 * by taking one legal move. Parameter "node" is needed only for the purposes of
	 * emptyMove (if a potential move is determined to be empty, then it is not 
	 * placed on the list).
	 */
	
	
	public LinkedList legalMoves(Node node) {
		int dire= getLastDirection(node);
		
		List moveOrder = new ArrayList(); 
		moveOrder.add(0); moveOrder.add(1); moveOrder.add(2); moveOrder.add(3);
		moveOrder.remove(dire);
		Collections.shuffle(moveOrder);
		moveOrder.add(dire);
		
		LinkedList theSet = new LinkedList();
		
		for (int k=0; k<4; k++) {
			int d = (Integer) moveOrder.get(k);
			for (int i=0; i<7; i++) {
				for (int j=0; j<7; j++) { 
					if (this.hasChecker(i, j)){
						switch(d){
						case 0:
							if (i>=2 && matrix[i-1][j] == 'C' && matrix[i-2][j] == 'O') {
								Move theMove = new Move(i,j,0);
								CheckersSolitaire position = move(theMove);
								theSet.add(position);
							}
							break;
						case 1:
							if (i<=4 && matrix[i+1][j] == 'C' && matrix[i+2][j] == 'O') {
								Move theMove = new Move(i,j,1);
								CheckersSolitaire position = move(theMove);
								theSet.add(position);
							}
							break;
						case 2:
							if (j>=2 && matrix[i][j-1] == 'C' && matrix[i][j-2] == 'O') {
								Move theMove = new Move(i,j,2);
								CheckersSolitaire position = move(theMove);
								theSet.add(position);
							}
							break;
						case 3:
							if (j<=4 && matrix[i][j+1] == 'C' && matrix[i][j+2] == 'O') {
								Move theMove = new Move(i,j,3);
								CheckersSolitaire position = move(theMove);
								theSet.add(position);
							}
							break;
						}
					}
				}
			}
		}
		return theSet;
	}

	
	/*
	 * Creates a new CheckersSolitaire puzzle, which represents the new position resulting 
	 * from taking move "theMove".
	 * The returned CheckersSolitaire puzzle (newPosition) is the same as the current one, except in 
	 * exactly one checker which is moved left, right, up or down by one cell. 
	 */
	
	private CheckersSolitaire move(Move theMove) {
		CheckersSolitaire newPosition = this.clone();
		
		switch (theMove.direction) {
		case 0: //left
			newPosition.matrix[theMove.pos_x][theMove.pos_y] = 'O';
			newPosition.matrix[theMove.pos_x-1][theMove.pos_y] = 'O';
			newPosition.matrix[theMove.pos_x-2][theMove.pos_y] = 'C';
			break;
		case 1: //right
			newPosition.matrix[theMove.pos_x][theMove.pos_y] = 'O';
			newPosition.matrix[theMove.pos_x+1][theMove.pos_y] = 'O';
			newPosition.matrix[theMove.pos_x+2][theMove.pos_y] = 'C';
			break;
		case 2: //up
			newPosition.matrix[theMove.pos_x][theMove.pos_y] = 'O';
			newPosition.matrix[theMove.pos_x][theMove.pos_y-1] = 'O';
			newPosition.matrix[theMove.pos_x][theMove.pos_y-2] = 'C';
			break;
		case 3: //down
			newPosition.matrix[theMove.pos_x][theMove.pos_y] = 'O';
			newPosition.matrix[theMove.pos_x][theMove.pos_y+1] = 'O';
			newPosition.matrix[theMove.pos_x][theMove.pos_y+2] = 'C';
			break;
		}
		
		return newPosition;
	}
	/*
	 * 
	 * 
	 */
	public CheckersSolitaire clone() {
		
		CheckersSolitaire newPosition = new CheckersSolitaire();
		for (int i=0; i<7; i++)
			for (int j=0; j<7; j++) 
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
	    
		CheckersSolitaire other = (CheckersSolitaire) o;
		for (int i=0; i<7; i++) {
			for (int j=0; j<7; j++) { 
				if (this.matrix[i][j] != other.matrix[i][j]) return false;
			}
		}
		
		return true;
	}
	
	public int hashCode() {
		int code=0;
		for (int i=0; i<7; i++) {
			for (int j=0; j<7; j++) { 
				code = code+matrix[i][j];
			}
		}
		return code;
	}
	

	/*
	 * The class encapsulates a move. Each move is defined as
	 * row (pos_x), column (pos_y), direction (0 - left, 1 - right, 2 - up, 3 - down).
	 * The Checker at pos_x, pos_y is moved one block in direction direction.
	 * TODO: direction should be coded with meaningful symbolic constants!
	 */
	
	class Move {
		int pos_x;
		int pos_y;
		int direction;
		
		Move(int pos_x, int pos_y, int direction) {
			this.pos_x = pos_x;
			this.pos_y = pos_y;
			this.direction = direction;
		}
		
	}

	
}