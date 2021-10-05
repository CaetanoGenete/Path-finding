package pathFinding.pathFindingAlgorithms;

import pathFinding.Vector2i;

public class ANode {

	public Vector2i pos;
	public ANode parent;
	public int fCost, hCost, gCost;

	public ANode(Vector2i pos, ANode current, int gCost, int hCost) {
		this.parent = current;
		this.pos = pos;
		this.gCost = gCost;
		this.hCost = hCost;

		fCost = gCost + hCost;
	}

}
