package framework;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
 
 * Generic sequential puzzle solver. 
 * Field puzzle holds the concrete puzzle.
 * Field seen contains the positions seen so far.

*/

/*
 * 
 * Essentially, the solver performs a depth-first search over the search space.
 * Method solve() starts the search by initializing the list of nodes with a 
 * node that contains the initial position, and a null previous node.
 * Method search(Node) examines the current node. If seen contains the 
 * position of the node, search returns a null list (i.e., the search 
 * stops at this branch). Otherwise, the search examines if the puzzle 
 * has reached the goal position, in which case it returns the solution in 
 * the form of a list of positions. If the puzzle has not reached the goal 
 * position yet, the search continues trying out each legal position that 
 * is a descendant of the current one. 
 * 
*/
class MyThread implements Runnable{
	public MyThread(Puzzle puzzle, Node node){
		
	}
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
public class ConcurrentPuzzleSolver {
	Executor e = Executors.newFixedThreadPool(3);
	private final Puzzle puzzle;
	private final HashSet seen = new HashSet();
	
	
	public ConcurrentPuzzleSolver(Puzzle puzzle) {
		this.puzzle = puzzle;
	}
	
	public LinkedList solve() {
		puzzle.initialPosition();
		return search(new Node(puzzle, null));
	}
	
	
	private LinkedList search(Node node) {

		if (!seen.contains(node.pos)) {
			seen.add(node.pos);
			if (node.pos.isGoal()) { 
				return node.asPositionList();
			}
			
			for (Object o : node.pos.legalMoves(node)) { 
				
				Puzzle puzzle = (Puzzle) o;
				
				Runnable task = new Runnable(){

					public void run() {
						
						//if (result != null) 				    										
							//return result;
						
					}
					
				};
				e.execute(task);							
				
				
					
			}
		}
		return null;
	}
	
}