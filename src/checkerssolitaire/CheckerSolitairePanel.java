package checkerssolitaire;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


import java.util.*;

class CheckersSolitairePanel extends JPanel {
  
	LinkedList checkerPlacement;
	
	CheckersSolitairePanel(LinkedList l) {
		checkerPlacement = l;
	}
	
/*
 * Method drawChecker(pos_x, pos_y, type, g) 
 * Draws a checker either white for open  blue for closed (occupied)
 * 
 * TODO: Needs refactoring: vehicles, colors and lengths should be 
 * coded using meaningful symbolic constants.
*/
	
  private void drawChecker(int pos_x, int pos_y, char type, Graphics g) {
	  if (type == 'O') g.setColor(Color.white);
	  else if (type == 'C') g.setColor(Color.blue);
	  
	  int thickness = 38;
	  
	  g.fillOval(pos_x*thickness+100+(pos_x*2), pos_y*thickness+50+(pos_y*2), thickness, thickness);
	  
  }
	
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.gray);
    
    int thickness = 4;

    for (int k = 0; k<7; k++) {
    	for (int l = 0; l<7; l++) {
    		g.fill3DRect(100 + k*40, 50 + l*40, 40, 40, true);
    	    for (int i = 1; i <= thickness; i++)
    	      g.draw3DRect(100 + k*40 - i, 50 + l*40 - i, 40 + 2 * i - 1, 40 + 2 * i - 1, true);
    	}
    }
    
    for (Object o : checkerPlacement) {
    	checkerssolitaire.CheckerNode cNode = (checkerssolitaire.CheckerNode) o;
    	drawChecker(cNode.getX(), cNode.getY(), cNode.getType(), g);
    }
    
  }

}
