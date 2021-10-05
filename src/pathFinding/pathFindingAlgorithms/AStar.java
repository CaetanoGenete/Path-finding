package pathFinding.pathFindingAlgorithms;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import pathFinding.Main;
import pathFinding.Vector2i;
import pathFinding.level.Level;
import pathFinding.level.Tile;

public class AStar {

	private List<ANode> closed = new ArrayList<>();
	private List<ANode> open = new ArrayList<>();

	private Vector2i goal;

	public AStar(Vector2i start, Vector2i goal, Main main) {
		this.goal = goal;
		
		ANode current = new ANode(start, null, 0, getManhattanDistance(start, goal));
		open.add(current);
		
		while(open.size() > 0) {
			if(!main.pathFind) return;
			
			current = getLowestCost();
			
			if(current.pos.equals(goal)) {
				while(current.parent != null) {
					main.level.getTile(current.pos).setColor(Color.PINK);
					
					current = current.parent;
					
					main.render();
				}
				break;
			}
			
			main.level.getTile(current.pos).setColor(Color.RED);
			
			open.remove(current);
			closed.add(current);
			
			for(ANode neighbour : getNeighbours(current, main.level)) {
				Tile tile = main.level.getTile(neighbour.pos);
				
				if(!vecInList(neighbour.pos, closed) && !vecInList(neighbour.pos, open)) {
					tile.setColor(Color.BLUE);
					tile.text = String.valueOf(neighbour.fCost);
					open.add(neighbour);
					
				}
				
			}
			
			main.render();
			
			main.level.getTile(current.pos).setColor(main.level.getTile(current.pos).original.darker());
			
		}
		
	}

	public int getManhattanDistance(Vector2i start, Vector2i tile) {
		return Math.abs(start.getX() - tile.getX()) + Math.abs(start.getY() - tile.getY());
	}

	public List<ANode> getNeighbours(ANode current, Level level) {
		List<ANode> result = new ArrayList<>();

		ANode north = createNode(current, new Vector2i(current.pos).add(0, -1), level);
		ANode south = createNode(current, new Vector2i(current.pos).add(0, 1), level);
		ANode east = createNode(current, new Vector2i(current.pos).add(1, 0), level);
		ANode west = createNode(current, new Vector2i(current.pos).add(-1, 0), level);

		if (north != null) result.add(north);
		if (south != null) result.add(south);
		if (east != null) result.add(east);
		if (west != null) result.add(west);

		return result;
	}

	public ANode createNode(ANode current, Vector2i pos, Level level) {
		Tile tile = level.getTile(pos);

		if (tile == null) return null;
		if (tile.solid()) return null;
		
		ANode node = new ANode(pos, current, current.gCost + tile.cost, getManhattanDistance(pos, goal));
		
		return node;
	}
	
	public ANode getLowestCost() {
		ANode lowest = open.get(0);
		
		for(int i = 0; i < open.size(); i++) {
			if(open.get(i).fCost == lowest.fCost) {
				if(open.get(i).hCost < lowest.hCost)
					lowest = open.get(i);
			} else if(open.get(i).fCost < lowest.fCost)
				lowest = open.get(i);
		}
		
		return lowest;
	}
	
	public boolean vecInList(Vector2i vec, List<ANode> nodes) {
		for(ANode node : nodes) {
			if(node.pos.equals(vec)) return true;
		}
		return false;
	}

}
