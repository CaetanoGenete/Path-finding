package pathFinding.pathFindingAlgorithms;

import pathFinding.Vector2i;

public class Node {

	public Node parent;
	public Vector2i pos;
	public int cost = 0;
	
	public Node(Vector2i pos, Node parent, int cost) {
		this.parent = parent;
		this.cost = cost;
		this.pos = pos;
	}
}
