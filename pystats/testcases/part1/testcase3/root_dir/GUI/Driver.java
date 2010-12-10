package GUI;

import javax.swing.*;        
import java.util.*;


/*
 * A driver and a primitive GUI that displays the solution.
 * The driver has three parts: creating the puzzle, solving the puzzle
 * and displaying the solution.
*/

public class Driver {

	static JFrame frame; 
	
    public static void main(String[] args) {

    	// Creates the concrete puzzle, RushHour, creates a SequentialPuzzleSolver
    	// and initializes the SequentialPuzzleSolver with RushHour.
   
    	framework.Puzzle thePuzzle = new rushhour.RushHour();
        framework.SequentialPuzzleSolver theSolver = new framework.SequentialPuzzleSolver(thePuzzle);
    	
    	// YOUR CODE HERE: creates RushHour, creates a ConcurrentPuzzleSolver, and initializes
    	// the ConcurrentPuzzleSolver with RushHour.
    	
    	// framework.Puzzle thePuzzle = new rushhour.RushHour();
    	// framework.ConcurrentPuzzleSolver theSolver = new framework.ConcurrentPuzzleSolver(thePuzzle);  	
    	
    	// Solves the puzzle
    	LinkedList theList = theSolver.solve();
    	
        // Creates and shows a GUI frame; displays the solution in frame, position by position.
        frame = new JFrame("RushHour");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        
        frame.setVisible(true);
        
        for (Object o : theList) {
        	rushhour.RushHour puzzle = (rushhour.RushHour) o;
        	frame.getContentPane().add(new RushHourPanel(puzzle.transform()));
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
          
}