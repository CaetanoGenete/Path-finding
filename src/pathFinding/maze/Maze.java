package pathFinding.maze;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pathFinding.Main;
import pathFinding.Vector2i;
import pathFinding.level.SolidTile;
import pathFinding.level.Tile;
import pathFinding.maze.Node.NodeType;

public class Maze {

	public static boolean generating = false;
	
	private int width, height;
	public Node[][] map;

	List<Wall> walls = new ArrayList<>();

	private Random random = new Random();

	public Maze(Vector2i start, Main main) {
		width = main.level.getWidth();
		height = main.level.getHeight();

		map = new Node[width][height];

		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				map[x][y] = new Wall(new Vector2i(x, y), new Vector2i());

		generating = true;
		
		buildPath(start, main);

		generating = false;
	}

	public void buildPath(Vector2i start, Main main) {
		setNode(new Node(start, NodeType.MAZE));

		setWalls(getNode(start), main);
		
		main.level.getTile(start).setColor(Color.GRAY);

		while (walls.size() != 0) {
			if(!generating) return;
			
			Wall wall = walls.get(random.nextInt(walls.size()));

			Node cell1 = getNode(new Vector2i(wall.pos).subtract(wall.dir));
			Node cell2 = getNode(new Vector2i(wall.pos).add(wall.dir));

			if (cell1 instanceof Wall && !(cell2 instanceof Wall)) {
				if (!cell1.checked) {
					Tile tile = new Tile(wall.pos);
					tile.setColor(Color.GRAY);
					main.level.setTile(wall.pos, tile);
					setNode(new Node(wall.pos));
					
					setNode(new Node(cell1.pos));
					
					Tile tile1 = new Tile(cell1.pos);
					tile1.setColor(Color.GRAY);
					main.level.setTile(cell1.pos, tile1);

					setWalls(cell1, main);
				}
			}

			if (cell2 instanceof Wall && !(cell1 instanceof Wall)) {
				if (!cell2.checked) {
					Tile tile = new Tile(wall.pos);
					tile.setColor(Color.GRAY);
					main.level.setTile(wall.pos, tile);
					setNode(new Node(wall.pos));
					
					setNode(new Node(cell2.pos));
					
					Tile tile1 = new Tile(cell2.pos);
					tile1.setColor(Color.GRAY);
					main.level.setTile(cell2.pos, tile1);

					setWalls(cell2, main);
				}
			}

			main.render();
			
			Tile tile = main.level.getTile(wall.pos);
			if(tile instanceof SolidTile) tile.setColor(tile.original);

			walls.remove(wall);
		}
	}

	public void setWalls(Node node, Main main) {
		for (int i = 0; i < 9; i++) {
			if (i == 4) continue;

			Vector2i move = new Vector2i((i % 3) - 1, (i / 3) - 1);
			Vector2i wallPos = new Vector2i(move).add(node.pos);
			Wall wall = new Wall(wallPos, move);
			Node newNode = getNode(wallPos);
			if (newNode == null) continue;

			if (i % 2 == 0) {
				if (newNode.checked) continue;

				wall.checked = true;

				setNode(wall);

				main.level.setTile(wallPos.getX(), wallPos.getY(), new SolidTile(wallPos));

				continue;
			}

			if (!newNode.checked && newNode instanceof Wall) {
				wall.checked = true;
				
				setNode(wall);

				walls.add(wall);
				
				Tile tile = new SolidTile(wallPos);
				tile.setColor(Color.RED);
				
				main.level.setTile(wallPos.getX(), wallPos.getY(), tile);

			}
		}
	}

	public void setNode(Node node) {
		if (node.pos.getX() < 0 || node.pos.getY() < 0 || node.pos.getX() >= width || node.pos.getY() >= height)
			return;

		map[node.pos.getX()][node.pos.getY()] = node;
	}

	public Node getNode(Vector2i node) {
		if (node.getX() < 0 || node.getY() < 0 || node.getX() >= width || node.getY() >= height) {
			return null;
		}

		return map[node.getX()][node.getY()];
	}

}
