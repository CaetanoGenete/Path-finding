package pathFinding;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window extends Canvas {

	private BufferStrategy bs;
	private Graphics g;
	
	private int width, height;
	
	public Window(int width, int height) {
		this.width = width;
		this.height = height;
		
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		
		JFrame frame = new JFrame("Shapes Playground");
		
		frame.setVisible(true);
		frame.setResizable(false);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		createBufferStrategy(2);
		bs = this.getBufferStrategy();
	}
	
	
	public void render() {
		g = bs.getDrawGraphics();
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, width, height);
	}
	
	public void clear() {
		g.dispose();
		bs.show();
	}
	
	public Graphics getGraphics() {
		return g;
	}
	
}
