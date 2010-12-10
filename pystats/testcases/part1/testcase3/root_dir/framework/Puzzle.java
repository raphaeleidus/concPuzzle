package framework;	

import java.util.*;


/*
 * Generic Puzzle interface
 * Method initialPosition() initializes the puzzle to its initial position.
 * Method isGoal() returns true if the puzzle is in the "solved" position;
 * it returns false otherwise.
 * Method legalMoves(Node) returns a list of legal positions achievable after
 * one legal move from the current position; Node "node" (the history 
 * of positions/moves so far) is passed to the puzzle so that the 
 * puzzle could prune away certain legal positions/moves and reduce 
 * the search space. An implementation of Puzzle may never use the history 
 * provided by "node".
 */

public interface Puzzle {
	
   public void initialPosition();
   public boolean isGoal();
   public LinkedList legalMoves(Node node);
   
}
