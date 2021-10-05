package pathFinding.pathFindingAlgorithms;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import pathFinding.Main;
import pathFinding.Vector2i;
import pathFinding.level.Level;
import pathFinding.level.TargetTile;
import pathFinding.level.Tile;

public class Dijkstra {

	private List<Node> frontier = new ArrayList<>();
	private boolean[][] closed;

	public Dijkstra(Vector2i start, Main main) {
		closed = new boolean[main.level.getWidth()][main.level.getHeight()];
		closed[start.getX()][start.getY()] = true;
		
		main.level.getTile(start).text = "0";
		
		Node current = new Node(start, null, 0);
		frontier.add(current);

		Node target = null;

		outerLoop:
		while (frontier.size() > 0) {
			if (!main.pathFind) return;

			current = getLowestCost();

			if (!current.pos.equals(start))
				main.level.getTile(current.pos).setColor(main.level.getTile(current.pos).original.darker());

			for (Node neighbour : getNeighbours(current, main.level)) {
				Tile tile = main.level.getTile(neighbour.pos.getX(), neighbour.pos.getY());

				closed[neighbour.pos.getX()][neighbour.pos.getY()] = true;
				frontier.add(neighbour);

				if (!neighbour.pos.equals(start))
					tile.setColor(Color.BLUE);
				else tile.setColor(Color.red);

				if (tile instanceof TargetTile) {
					target = neighbour;
					tile.setColor(new Color(0xFF00FF00));
					break outerLoop;
				}
			}

			frontier.remove(current);

			main.render();
		}

		if (target == null) return;

		while (target.parent != null) {
			if (target.pos.equals(start)) return;

			main.level.getTile(target.pos).setColor(Color.PINK);

			target = target.parent;

			main.render();
		}
	}

	public Node getLowestCost() {
		Node lowest = frontier.get(0);

		for (Node node : frontier)
			if (node.cost < lowest.cost) lowest = node;

		return lowest;
	}

	public List<Node> getNeighbours(Node current, Level level) {
		List<Node> result = new ArrayList<>();

		Vector2i northVec = new Vector2i(current.pos).add(0, -1);
		Vector2i southVec = new Vector2i(current.pos).add(0, 1);
		Vector2i eastVec = new Vector2i(current.pos).add(1, 0);
		Vector2i westVec = new Vector2i(current.pos).add(-1, 0);

		Tile northTile = level.getTile(northVec);
		Tile southTile = level.getTile(southVec);
		Tile eastTile = level.getTile(eastVec);
		Tile westTile = level.getTile(westVec);

		Node north = null, south = null, east = null, west = null;

		if (northTile != null && !northTile.solid())
			north = new Node(northVec, current, current.cost + northTile.cost);
		if (southTile != null && !southTile.solid())
			south = new Node(southVec, current, current.cost + southTile.cost);
		if (eastTile != null && !eastTile.solid())
			east = new Node(eastVec, current, current.cost + eastTile.cost);
		if (westTile != null && !westTile.solid())
			west = new Node(westVec, current, current.cost + westTile.cost);

		if (!closed(north, level)) {
			northTile.text = String.valueOf(north.cost);
			result.add(north);
		}
		if (!closed(east, level)) {
			eastTile.text = String.valueOf(east.cost);
			result.add(east);
		}
		if (!closed(south, level)) {
			southTile.text = String.valueOf(south.cost);
			result.add(south);
		}
		if (!closed(west, level)) {
			westTile.text = String.valueOf(west.cost);
			result.add(west);
		}

		return result;
	}

	public boolean closed(Node node, Level level) {
		if (node == null) return true;

		Vector2i pos = node.pos;

		if (pos.getX() < 0 || pos.getY() < 0 || pos.getX() >= level.getWidth() || pos.getY() >= level.getHeight())
			return true;

		if (closed[pos.getX()][pos.getY()]) return true;
		return false;
	}

}
