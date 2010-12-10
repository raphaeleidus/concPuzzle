package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.*;

public class RushHourPanel extends JPanel {
  
	LinkedList vehiclePlacement;
	
	RushHourPanel(LinkedList l) {
		vehiclePlacement = l;
	}
	
/*
 * Method drawVehicle(pos_x, pos_y, vehicle, direction...) 
 * displays a vehicle "vehicle" (one of 'A' through 'K', 'O' through 'R' and 'X') 
 * starting at row "pos_x", column "pos_y" in direction "direction". 
 * 
 * TODO: Needs refactoring: vehicles, colors and lengths should be 
 * coded using meaningful symbolic constants.
*/
	
  private void drawVehicle(int pos_x, int pos_y, char vehicle, int direction, Graphics g) {
	  int len = 0;
	  if (vehicle == 'A') { g.setColor(Color.darkGray); len = 2; }
	  else if (vehicle == 'B') { g.setColor(Color.yellow); len = 2; }
	  else if (vehicle == 'C') { g.setColor(Color.BLUE); len = 2; }
	  else if (vehicle == 'D') { g.setColor(Color.green); len = 2; }
	  else if (vehicle == 'E') { g.setColor(Color.pink); len = 2; }
	  else if (vehicle == 'F') { g.setColor(Color.darkGray); len = 2; }
	  else if (vehicle == 'G') { g.setColor(Color.cyan); len = 2; }
	  else if (vehicle == 'H') { g.setColor(Color.green); len = 2; }
	  else if (vehicle == 'I') { g.setColor(Color.yellow); len = 2; }
	  else if (vehicle == 'J') { g.setColor(Color.BLACK); len = 2; }
	  else if (vehicle == 'K') { g.setColor(Color.magenta); len = 2; }
	  else if (vehicle == 'X') { g.setColor(Color.white); len = 2; }
	  
	  else if (vehicle == 'P') { g.setColor(Color.red); len = 3; }
	  else if (vehicle == 'R') { g.setColor(Color.red); len = 3; }
	  else if (vehicle == 'O') { g.setColor(Color.yellow); len = 3; }
	  else if (vehicle == 'Q') { g.setColor(Color.yellow); len = 3; }
	  
	  int thickness = 4;
	  
	  if (direction == 0) {
		g.fill3DRect(100 + pos_y*40, 50 + pos_x*40, len*40 - 5, 40 - 5, true);
  	    for (int i = 1; i <= thickness; i++)
  	      g.draw3DRect(100 + pos_y*40 - i, 50 + pos_x*40 - i, len*40 - 5 + 2 * i - 1, 40 - 5 + 2 * i - 1, true);
	  }
	  else if (direction == 1) {
		g.fill3DRect(100 + pos_y*40, 50 + pos_x*40, 40 - 5, len*40 - 5, true);
	    for (int i = 1; i <= thickness; i++)
  	      g.draw3DRect(100 + pos_y*40 - i, 50 + pos_x*40 - i, 40 - 5 + 2 * i - 1, len*40 - 5 + 2 * i - 1, true);
	  }
	  
  }
	
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.gray);
    
    int thickness = 4;

    for (int k = 0; k<6; k++) {
    	for (int l = 0; l<6; l++) {
    		g.fill3DRect(100 + k*40, 50 + l*40, 40, 40, true);
    	    for (int i = 1; i <= thickness; i++)
    	      g.draw3DRect(100 + k*40 - i, 50 + l*40 - i, 40 + 2 * i - 1, 40 + 2 * i - 1, true);
    	}
    }
    
    for (Object o : vehiclePlacement) {
    	rushhour.VehicleNode vNode = (rushhour.VehicleNode) o;
    	drawVehicle(vNode.getX(), vNode.getY(), vNode.getVehicle(), vNode.getDirection(), g);
    }
    
  }

}