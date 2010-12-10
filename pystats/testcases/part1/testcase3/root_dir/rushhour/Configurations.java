package rushhour;

import java.util.ArrayList;
import java.util.LinkedList;

/*
 * Provides several preset initial configurations
 * for the RushHour puzzle. All are at Expert level.
 */

public class Configurations {
	
	public static ArrayList theConfigs = new ArrayList();
		
	public static void addAll() {
	
		
	   LinkedList set1 = new LinkedList();
	   set1.add(new VehicleNode(2,0,'X',2,0));
	   set1.add(new VehicleNode(0,2,'O',3,1));
	   set1.add(new VehicleNode(1,3,'A',2,1));
	   set1.add(new VehicleNode(1,4,'H',2,0));
	   set1.add(new VehicleNode(3,2,'Q',3,0));
	   set1.add(new VehicleNode(2,5,'P',3,1));
	   set1.add(new VehicleNode(5,3,'R',3,0));
	   theConfigs.add(set1);
	   
	   LinkedList set2 = new LinkedList();
	   set2.add(new VehicleNode(2,3,'X',2,0));
	   set2.add(new VehicleNode(0,2,'O',3,1));
	   set2.add(new VehicleNode(0,3,'A',2,0));
	   set2.add(new VehicleNode(1,3,'B',2,0));
	   set2.add(new VehicleNode(0,5,'P',3,1));
	   set2.add(new VehicleNode(3,3,'Q',3,0));
	   set2.add(new VehicleNode(4,1,'C',2,0));
	   set2.add(new VehicleNode(5,1,'F',2,0));
	   set2.add(new VehicleNode(4,4,'E',2,0));
	   set2.add(new VehicleNode(5,4,'G',2,0));
	   set2.add(new VehicleNode(4,3,'D',2,1));
	   theConfigs.add(set2);
	   
	   LinkedList set3 = new LinkedList();
	   set3.add(new VehicleNode(2,1,'X',2,0));
	   set3.add(new VehicleNode(0,2,'O',3,0));
	   set3.add(new VehicleNode(0,1,'A',2,1));
	   set3.add(new VehicleNode(0,5,'K',2,1)); 
	   set3.add(new VehicleNode(2,4,'P',3,1));
	   set3.add(new VehicleNode(3,1,'Q',3,0));
	   set3.add(new VehicleNode(5,3,'H',2,0)); 
	   set3.add(new VehicleNode(4,2,'F',2,1));
	   set3.add(new VehicleNode(3,0,'E',2,1));
	   set3.add(new VehicleNode(4,5,'G',2,1));
	   set3.add(new VehicleNode(1,3,'D',2,1));
	   set3.add(new VehicleNode(1,0,'C',2,1));
	   theConfigs.add(set3);
	   
	   
	}
}