package pathFinding.level;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import pathFinding.Vector2i;

public class Tile {

	public static int TILE_SIZE = 40;

	public int cost = 1;
	public int x, y;
	public String text = "";

	private Color colour = new Color(0xFF7F44);
	public Color original = new Color(0xFF7F44);

	public Tile(int x, int y) {
		this.x = x * TILE_SIZE;
		this.y = y * TILE_SIZE;
	}

	public Tile(Vector2i vec) {
		this.x = vec.getX() * TILE_SIZE;
		this.y = vec.getY() * TILE_SIZE;
	}

	public void render(Graphics g) {
		g.setColor(colour);

		g.fillRect(x + 1, y + 1, TILE_SIZE - 2, TILE_SIZE - 2);

		FontMetrics metrics = g.getFontMetrics();

		g.setColor(Color.BLACK);
		if (text != null)
			g.drawString(text, x - metrics.stringWidth(text) / 2 + (Tile.TILE_SIZE/2), y + (Tile.TILE_SIZE/2) - metrics.getHeight() / 2 + metrics.getAscent());
	}
	
	public Tile recalculatePos(int prevSize) {
		int x = this.x / prevSize;
		int y = this.y / prevSize;
		
		this.x = x * TILE_SIZE;
		this.y = y * TILE_SIZE;
		
		return this;
	}

	public void darken() {
		this.colour = colour.darker();
	}

	public void setColor(Color colour) {
		this.colour = colour;
	}

	public boolean solid() {
		return false;
	}

}
