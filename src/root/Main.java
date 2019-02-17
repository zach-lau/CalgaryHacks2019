/**
 * 
 */
package root;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

/**
 * @author charl
 */
public class Main {

	/**
	 * @param args
	 */
	private static Link[][] streetConnections;
	private static Node[] nodeData;
	private static int plows;
	private static int nodes;
	private static int roads;
	private static final double change = 0.5;
	private static Random r = new Random();
	private static final int populationSize = 5000;
	private static final int iterations = 200;
	private static final double elite = 0.2;

	public static void main(String[] args) {
		read();
		// System.out.println("Generating population...");
		Population[] pop = generatePop();
		// System.out.println("population generated, evolving population");
		for (int i = 0; i < iterations; i++) {
			pop = evolve(pop);
			System.out.println("Evolved " + i + " times");
			System.out.println("Fitness: " + fitness(pop[0]));
			// for (int j = 0; j < populationSize; j++) {
			// System.out.println("Fitness: " + fitness(pop[j]));
			// output(pop[j]);
			// }
		}
		Arrays.sort(pop, new Comparator<Population>() {

			@Override
			public int compare(Population o1, Population o2) {
				if ((fitness(o1) - fitness(o2)) < 0) {
					return -1;
				} else if (fitness(o1) - fitness(o2) == 0) {
					return 0;
				} else {
					return 1;
				}

			}
		});
		for (int i = 0; i < 1; i++) {
			System.out.println("Fitness: " + fitness(pop[i]));
			output(pop[i]);
		}
	}

	private static void output(Population pop) {
		for (ArrayList<Integer> list : pop.steps) {
			for (Integer node : list) {
				System.out.print(node + " ");
			}
			System.out.println();
		}
	}

	private static void read() {
		Scanner s = new Scanner(System.in);
		plows = s.nextInt();
		nodes = s.nextInt();
		roads = s.nextInt();
		nodeData = new Node[nodes];
		streetConnections = new Link[nodes][nodes];
		for (int i = 0; i < nodes; i++) {
			s.nextInt();
			s.nextInt();
			s.nextInt();
		}
		
		for (int i = 0; i < roads; i++) {
			int src = s.nextInt();
			int dest = s.nextInt();
			streetConnections[src][dest] = new Link(s.nextInt(), s.nextInt());
			if (nodeData[src] == null) {
				nodeData[src] = new Node(0, 0);
			}
			nodeData[src].connections.add(dest);
		}
	}

	private static void read(String filename) {
		try {
			File file = new File(filename);
			Scanner s = new Scanner(file);
			plows = s.nextInt();
			nodes = s.nextInt();
			roads = s.nextInt();
			nodeData = new Node[nodes];
			streetConnections = new Link[nodes][nodes];
			for (int i = 0; i < roads; i++) {
				int src = s.nextInt();
				int dest = s.nextInt();
				streetConnections[src][dest] = new Link(s.nextInt(), s.nextInt());
				nodeData[src] = new Node(0, 0);
				nodeData[src].connections.add(dest);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Population[] generatePop() {
		Population[] pop = new Population[populationSize];
		for (int i = 0; i < populationSize; i++) {
			pop[i] = new Population(plows, nodes, roads, nodeData);
		}
//		Integer[] temp= {0,1,2,5,2,1,0,3,6,7,8,7,6,3,4,5,4,1,4,3,0};
//		pop[0].steps.get(0).clear();
//		pop[0].steps.get(0).addAll(Arrays.asList(temp));
		return pop;
	}

	private static Population[] evolve(Population[] pop) {
		System.out.println("Sorting");
		// System.out.println(Arrays.deepToString(pop));
		Arrays.sort(pop, new Comparator<Population>() {

			@Override
			public int compare(Population o1, Population o2) {
				if ((fitness(o1) - fitness(o2)) < 0) {
					return -1;
				} else if (fitness(o1) - fitness(o2) == 0) {
					return 0;
				} else {
					return 1;
				}

			}
		});
		System.out.println("Choosing elites");
		Population[] newPop = new Population[populationSize];
		for (int i = 0; i < (int) (populationSize * elite/3); i++) {
			newPop[i] = new Population(pop[i]);
		}
		Population[] baseMutations = new Population[(int) (populationSize*elite)];
		for (int i = 0; i < (int) (populationSize * elite); i++) {
			baseMutations[i] = new Population(pop[i]);
		}
		System.out.println("Mutating");
		int ratio = (int) (populationSize*elite-1);
		for (int i = (int) (populationSize * elite/2); i < populationSize; i++) {
			if(i<populationSize*(1-elite/2))
				newPop[i] = mutate(new Population(baseMutations[i % ratio]));
			else
				newPop[i] = mutate(new Population(plows,nodes,roads,nodeData));
		}

		return newPop;
	}

	private static Population mutate(Population pop) {
		Population newPop = new Population(pop);
		for (int i = 0; i < plows; i++) {
			double prob = r.nextDouble();
			int addRoad = r.nextInt(roads/nodes*2)-roads/nodes;
			// System.out.println(addRoad);
			int cutIndex = r.nextInt(pop.steps.get(i).size()+1);
			int size = pop.steps.get(i).size();
		

			if (cutIndex == 0) {
//				ArrayList<Integer> connections = nodeData[pop.steps.get(i).get(0)].connections;
//				newPop.steps.get(i).add(0,connections.get(r.nextInt(connections.size())));
			} else {
				for (int j = 0; j < size - cutIndex; j++) {
					newPop.steps.get(i).remove(newPop.steps.get(i).size() - 1);
				}
				ArrayList<Integer> connections = nodeData[pop.steps.get(i).get(cutIndex - 1)].connections;
				newPop.steps.get(i).add(connections.get(r.nextInt(connections.size())));
				for (int j = cutIndex + 1; j < size + addRoad; j++) {
					connections = nodeData[newPop.steps.get(i).get(j - 1)].connections;
					newPop.steps.get(i).add(connections.get(r.nextInt(connections.size())));
				}
			}
			
		}
		return newPop;
	}

	private static double fitness(Population pop) {
		if (pop != null) {
			int totalTime = 0;
			int[][] visited = new int[nodes][nodes];
			for (int i = 0; i < nodes; i++) {
				for (int j = 0; j < nodes; j++) {
					visited[i][j] = -1;
				}
			}
			double fit = 0;
			for (int i = 0; i < plows; i++) {
				int src = -1;
				int dest = -1;
				int time = 0;
				for (int j = 0; j < pop.steps.get(i).size(); j++) {
					src = dest;
					dest = pop.steps.get(i).get(j);

					if (src != -1) {
						time += streetConnections[src][dest].time;
						if (visited[src][dest] > time) {
							fit -= visited[src][dest] * streetConnections[src][dest].priority;
							visited[src][dest] = time;
							fit += time * streetConnections[src][dest].priority;
						} else if (visited[src][dest] == -1) {
							visited[src][dest] = time;
							fit += time * streetConnections[src][dest].priority;
						}
					}
				}
				totalTime += time;
			}
			for (int i = 0; i < nodes; i++) {
				for (int j = 0; j < nodes; j++) {
					if (streetConnections[i][j] != null && visited[i][j] == -1) {
						fit += streetConnections[i][j].priority * 24 * 6000;
					}
				}
			}

			return fit + totalTime;
		}
		return Long.MAX_VALUE;
	}

}
