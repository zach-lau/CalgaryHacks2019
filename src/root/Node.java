/**
 * 
 */
package root;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author charl
 *
 */
public class Node {
	public int x,y;
	public ArrayList<Integer> connections=new ArrayList<Integer>();
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Node(Node n) {
		this.x = n.x;
		this.y = n.y;
		for(Integer i:n.connections) {
			connections.add(i);
		}
	}
	public String toString() {
		return connections.toString();
	}

	
}
