package framework;

import java.util.*;
import java.util.concurrent.*;

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

public class ConcurrentPuzzleSolver {
	
	class MyThread implements Callable{
		Node n;
		int level;
		public MyThread(Node node, int deep){
			n = node;
		 	level = deep;
		}
		public LinkedList call() {	
			
			
			return search(n, level);
			
			
		}
		

		
		
	}
	//put a lock on it
	
	private final Puzzle puzzle;
	private final ConcurrentHashMap<Puzzle, Node> seen = new ConcurrentHashMap<Puzzle, Node>();
	
	
	public ConcurrentPuzzleSolver(Puzzle puzzle) {
		this.puzzle = puzzle;
	}
	
	public LinkedList solve() {
		puzzle.initialPosition();
		return search(new Node(puzzle, null), 1);
	}
	
	
	public LinkedList search(Node node, int level) {		
		ExecutorService e = Executors.newFixedThreadPool(2);
		Set<Future<LinkedList>> set = new HashSet<Future<LinkedList>>();
		if (seen.putIfAbsent(node.pos, node) == null) {
			if (node.pos.isGoal()) { 
				return node.asPositionList();
			}
			
			for (Object o : node.pos.legalMoves(node)) { 
				
				Puzzle puzzle = (Puzzle) o;
				Node child = new Node(puzzle, node);
				
				Callable<LinkedList> task = new MyThread(child, level+1);
				Future<LinkedList> future = e.submit(task);
				set.add(future);
						
			}
			e.shutdown();
			try {
				e.awaitTermination(60, TimeUnit.SECONDS);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (Future<LinkedList> future : set) {
				LinkedList result = null;
				try {
					result = future.get();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(result == null) continue;
				if(result.isEmpty()) continue;
				else return result;
			}
		}
		
		return null;
	}
	
}