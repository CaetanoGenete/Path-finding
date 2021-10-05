package pathFinding.maze;

import pathFinding.Vector2i;

public class Node {

	public enum NodeType {
		MAZE, WALL;
	}
	
	public NodeType type = NodeType.WALL;
	
	public boolean checked = false;
	public Vector2i pos;
	
	public Node(Vector2i pos, NodeType type) {
		this.pos = pos;
	}
	
	public Node(Vector2i pos) {
		this(pos, NodeType.MAZE);
	}
	
}
