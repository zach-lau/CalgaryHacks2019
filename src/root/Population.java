/**
 * 
 */
package root;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author charl
 *
 */
public class Population {
	ArrayList<ArrayList<Integer>> steps = new ArrayList<ArrayList<Integer>>();
	private static Random r = new Random();
	int init = 0;
	public Population(int plows, int nodes, int roads, Node[] nodeData) {
		int length = roads / plows;
		for (int i = 0; i < plows; i++) {
			steps.add(new ArrayList<Integer>());
			steps.get(i).add(init);
			for (int j = 0; j < length - 1; j++) {
				ArrayList<Integer> connections = nodeData[steps.get(i).get(j)].connections;
				steps.get(i).add(connections.get(r.nextInt(connections.size())));
			}
		}

	}
	public Population(Population pop) {
		for (int i = 0; i < pop.steps.size(); i++) {
			steps.add(new ArrayList<Integer>());
			for (int j = 0; j < pop.steps.get(i).size(); j++) {
				steps.get(i).add(pop.steps.get(i).get(j));
			}
		}
	}
	public String toString() {
		return steps.toString()+"\n";
		
	}
	

}
