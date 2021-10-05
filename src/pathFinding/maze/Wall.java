package pathFinding.maze;

import pathFinding.Vector2i;

public class Wall extends Node {

	public Vector2i dir;
	
	public Wall(Vector2i pos, Vector2i dir) {
		super(pos, NodeType.WALL);
		
		this.dir = dir;
	}

}
