package framework;

import java.util.*;

/*
 * Class Node represents a link node. The linked list of nodes
 * represents the sequence of positions (i.e., puzzle configurations)
 * that leads to a solution. Method asPositionList reverses the list 
 * - it returns a linked list of positions where the first position is the 
 * starting position, followed by the position after the first move, and so on.
 * The last position is the "solved" position of the puzzle.
*/


public class Node {
	final Puzzle pos;
	final Node prev;	
		
	Node(Puzzle puzzle, Node prev) {
		this.pos = puzzle;
		this.prev = prev;
	}
	
	public Puzzle getPos() { return pos; };
	public Node getPrev() { return prev; };
	
	
	
	LinkedList asPositionList() {
		LinkedList solution = new LinkedList();
	
		for (Node n = this; n != null; n = n.prev)
			solution.add(0, n.pos);
		return solution;
	}
	
}