package pathFinding;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.Random;

import pathFinding.external.OptionsPanel;
import pathFinding.input.Mouse;
import pathFinding.level.Level;
import pathFinding.level.SolidTile;
import pathFinding.level.TargetTile;
import pathFinding.level.Tile;
import pathFinding.level.WaterTile;
import pathFinding.maze.Maze;
import pathFinding.pathFindingAlgorithms.AStar;
import pathFinding.pathFindingAlgorithms.BreathFirst;
import pathFinding.pathFindingAlgorithms.Dijkstra;

public class Main extends Canvas implements Runnable {

	private int width = 1200, height = 800;

	private Thread thread;
	public Window window;

	public Level level;

	public boolean pathFind = false;

	public Vector2i start;
	public Vector2i target;

	private OptionsPanel panel;
	
	public int delay = 50;

	public enum DrawType {
		BLOCK, ERASE, WATER;
	}

	public enum PathType {
		BREADTH, DIJKSTRA, ASTAR;
	}

	public DrawType type = DrawType.BLOCK;
	public PathType path = PathType.BREADTH;

	public Main() {

		window = new Window(width, height);
		level = new Level(width, height);

		Mouse mouse = new Mouse() {
			public void mousePressed(MouseEvent e) {
				int tileX = e.getX() / Tile.TILE_SIZE;
				int tileY = e.getY() / Tile.TILE_SIZE;

				Mouse.button = e.getButton();

				if (e.getButton() == MouseEvent.BUTTON1) {

					if (type == DrawType.BLOCK)
						level.setTile(tileX, tileY, new SolidTile(tileX, tileY));
					if (type == DrawType.ERASE)
						level.setTile(tileX, tileY, new Tile(tileX, tileY));
					if (type == DrawType.WATER)
						level.setTile(tileX, tileY, new WaterTile(tileX, tileY));
				}

				if (e.getButton() == MouseEvent.BUTTON2) {
					if (start != null)
						level.setTile(start.getX(), start.getY(), new Tile(start.getX(), start.getY()));
					level.getTile(tileX, tileY).setColor(new Color(0xFFFF0000));
					start = new Vector2i(tileX, tileY);
				}

				if (e.getButton() == MouseEvent.BUTTON3 && !pressed) {
					if (target != null)
						level.setTile(target.getX(), target.getY(), new Tile(target.getX(), target.getY()));
					level.setTile(tileX, tileY, new TargetTile(tileX, tileY));
					target = new Vector2i(tileX, tileY);
				}
			}

			public void mouseDragged(MouseEvent e) {

				if (Mouse.button == MouseEvent.BUTTON1) {

					int tileX = e.getX() / Tile.TILE_SIZE;
					int tileY = e.getY() / Tile.TILE_SIZE;

					if (type == DrawType.BLOCK)
						level.setTile(tileX, tileY, new SolidTile(tileX, tileY));
					if (type == DrawType.ERASE)
						level.setTile(tileX, tileY, new Tile(tileX, tileY));
					if (type == DrawType.WATER)
						level.setTile(tileX, tileY, new WaterTile(tileX, tileY));
				}
			}
		};

		KeyListener listner = new KeyListener() {

			public void keyTyped(KeyEvent e) {

			}

			public void keyReleased(KeyEvent e) {

			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					pathFind = true;
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					level.restore();
			}
		};

		window.addKeyListener(listner);
		window.addMouseListener(mouse);
		window.addMouseMotionListener(mouse);

		panel = new OptionsPanel(200, 360, this);

	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		while (true) {
			if(Maze.generating) {
				Random random = new Random();
				
				int levelWidth = level.getWidth();
				int levelHeight = level.getHeight();
				
				new Maze(new Vector2i(random.nextInt(levelWidth), random.nextInt(levelHeight)), this);
				
				panel.maze.setText("Generate Maze");
			}
			if (pathFind) {
				if (target != null && start != null) {
					if (path == PathType.BREADTH)
						new BreathFirst(start, level, this);
					if (path == PathType.DIJKSTRA)
						new Dijkstra(start, this);
					if (path == PathType.ASTAR)
						new AStar(start, target, this);
				}
				panel.start.setText("Start");
				pathFind = false;
			}
			render();
		}
	}

	public void render() {
		window.render();
		
		Graphics g = window.getGraphics();
		
		g.setFont(new Font("Verdana", Font.PLAIN, Tile.TILE_SIZE/2 - 1));

		level.render(g);

		window.clear();

		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Main main = new Main();

		main.start();
	}

}
