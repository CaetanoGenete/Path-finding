package pathFinding.external;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pathFinding.Main;
import pathFinding.Main.DrawType;
import pathFinding.Main.PathType;
import pathFinding.level.Tile;
import pathFinding.Vector2i;
import pathFinding.maze.Maze;

public class OptionsPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private int width, height;
	private JFrame frame;

	public JButton remove, water, solid, breath, dijkstras, Astar, start, maze;
	
	private Random random = new Random();
	
	public OptionsPanel(int width, int height, Main main) {
		this.width = width;
		this.height = height;
		
		setPreferredSize(new Dimension(width, height));
		setLayout(null);
		
		solid = new JButton("BLOCK");
		solid.setBounds(5, 5, width/2 - 10, 50);

		Color colour = solid.getBackground();
		solid.setBackground(new Color(0xFF00DF00));
		
		solid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				solid.setBackground(new Color(0xFF00DF00));
				remove.setBackground(colour);
				water.setBackground(colour);
				
				main.type = DrawType.BLOCK;
			}
		});
		
		add(solid);
		
	    remove = new JButton("REMOVE");
		remove.setBounds(5, 60, width/2 - 10, 50);
		
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				solid.setBackground(colour);
				remove.setBackground(new Color(0xFF00DF00));
				water.setBackground(colour);
				
				main.type = DrawType.ERASE;
			}
		});
		
		add(remove);
		
		water = new JButton("WATER");
		water.setBounds(5, 115, width/2 - 10, 50);
		
		water.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				solid.setBackground(colour);
				remove.setBackground(colour);
				water.setBackground(new Color(0xFF00DF00));
				
				main.type = DrawType.WATER;
			}
		});
		
		add(water);
		
		breath = new JButton("Breath");
		breath.setBounds(105, 5, width/2 - 10, 50);
		breath.setBackground(new Color(0xFF00DF00));
		
		breath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				breath.setBackground(new Color(0xFF00DF00));
				dijkstras.setBackground(colour);
				Astar.setBackground(colour);
				
				main.path = PathType.BREADTH;
			}
		});
		
		add(breath);
		
	    dijkstras = new JButton("Dijkstra's");
	    dijkstras.setBounds(105, 60, width/2 - 10, 50);
		
	    dijkstras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				breath.setBackground(colour);
				dijkstras.setBackground(new Color(0xFF00DF00));
				Astar.setBackground(colour);
				
				main.path = PathType.DIJKSTRA;
			}
		});
		
		add(dijkstras);
		
		Astar = new JButton("A star");
		Astar.setBounds(105, 115, width/2 - 10, 50);
		
		Astar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				breath.setBackground(colour);
				dijkstras.setBackground(colour);
				Astar.setBackground(new Color(0xFF00DF00));
				
				main.path = PathType.ASTAR;
			}
		});
		
		add(Astar);
		
		start = new JButton("Start");
		start.setBounds(5, 280, width - 10, 50);
		
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(main.pathFind) {
					main.pathFind = false;
					
					start.setText("Start");
				} else {
					main.pathFind = true;
					
					start.setText("Stop");
				}
				
				main.level.restore();
				main.level.getTile(main.start).setColor(Color.RED);
			}
		});
		
		JButton reset = new JButton("Reset Map");
		reset.setBounds(5, 170, width - 10, 50);
		
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.level.restart();
				main.pathFind = false;
				
				main.target = null;
				main.start = null;

				
				start.setText("Start");
			}
		});
		
		add(reset);
		add(start);
		
		maze = new JButton("Generate Maze");
		maze.setBounds(5, 225, width - 10, 50);
		
		maze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!Maze.generating) {
					main.level.restart();
					
					main.pathFind = false;
					
					main.target = null;
					main.start = null;
					
					Maze.generating = true;
					
					maze.setText("Stop generating");
				} else {
					Maze.generating = false;
				}
			}
		});
		
		add(maze);
		
		JLabel delayLabel = new JLabel("TD:");
		delayLabel.setBounds(5, 335, 40, 20);
		
		JTextField delay = new JTextField("50");
		delay.setBounds(30, 335, 50, 20);
		
		delay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!delay.getText().matches("[-+]?\\d*\\.?\\d+")) return;
				
				if(Integer.parseInt(delay.getText()) < 0) delay.setText("0");
				
				main.delay = Integer.parseInt(delay.getText());
			}
		});
		
		add(delayLabel);
		add(delay);
		
		JLabel tileSizeLabel = new JLabel("TS:");
		tileSizeLabel.setBounds(100, 335, 40, 20);
		
		JTextField tileSize = new JTextField("40");
		tileSize.setBounds(130, 335, 50, 20);
		
		tileSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!tileSize.getText().matches("[-+]?\\d*\\.?\\d+")) return;
				
				int size = Integer.parseInt(tileSize.getText());
				
				if(size < 10) tileSize.setText("10");
				if(size > 40) tileSize.setText("80");
				
				if(Integer.parseInt(tileSize.getText()) == Tile.TILE_SIZE) return;
				
				main.level.recalculateSize(Integer.parseInt(tileSize.getText()));
				
				main.target = null;
				main.start = null;
			}
		});
		
		add(tileSize);
		add(tileSizeLabel);
		
		frame = new JFrame();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		frame.setAlwaysOnTop(true);
	}
	
}
