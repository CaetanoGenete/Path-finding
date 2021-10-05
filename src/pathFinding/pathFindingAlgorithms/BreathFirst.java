package pathFinding.pathFindingAlgorithms;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import pathFinding.Main;
import pathFinding.Vector2i;
import pathFinding.level.Level;
import pathFinding.level.TargetTile;
import pathFinding.level.Tile;

public class BreathFirst {

	private List<Vector2i> frontier = new ArrayList<>();
	private Vector2i[][] closed;

	public BreathFirst(Vector2i start, Level level, Main main) {
		closed = new Vector2i[level.getWidth()][level.getHeight()];
		frontier.add(start);

		Vector2i current, target = null;

		outer:
		while (frontier.size() > 0) {
			if(!main.pathFind) return;
			
			current = frontier.get(0);

			if (!current.equals(start))
				level.getTile(current).setColor(level.getTile(current).original.darker());

			for (Vector2i neighbour : getNeighbours(current, level)) {
				Tile tile = level.getTile(neighbour);

				if (tile == null) continue;
				if (tile.solid()) continue;

				closed[neighbour.getX()][neighbour.getY()] = new Vector2i(current);
				frontier.add(neighbour);

				if (tile instanceof TargetTile) {
					target = neighbour;
					break outer;
				}

				if (!neighbour.equals(start))
					tile.setColor(Color.BLUE);
				else tile.setColor(Color.red);

			}

			frontier.remove(0);

			main.render();
		}
		
		if(target == null) return;

		while (!target.equals(start)) {
			target = closed[target.getX()][target.getY()];

			if (target.equals(start)) return;

			level.getTile(target).setColor(Color.PINK);

			main.render();
		}
	}

	public List<Vector2i> getNeighbours(Vector2i start, Level level) {
		List<Vector2i> result = new ArrayList<>();

		Vector2i north = new Vector2i(start).add(0, -1);
		Vector2i south = new Vector2i(start).add(0, 1);
		Vector2i east = new Vector2i(start).add(1, 0);
		Vector2i west = new Vector2i(start).add(-1, 0);

		if (!closed(north, level)) result.add(north);
		if (!closed(east, level)) result.add(east);
		if (!closed(south, level)) result.add(south);
		if (!closed(west, level)) result.add(west);

		return result;
	}

	public boolean closed(Vector2i pos, Level level) {
		if (pos.getX() < 0 || pos.getY() < 0 || pos.getX() >= level.getWidth() || pos.getY() >= level.getHeight())
			return true;

		if (closed[pos.getX()][pos.getY()] != null) return true;
		return false;
	}

}
