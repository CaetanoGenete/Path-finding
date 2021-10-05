package pathFinding.level;

import java.awt.Graphics;

import pathFinding.Vector2i;

public class Level {

	private int width, height;
	private double prevWidth, prevHeight;
	
	private Tile[][] tiles;
	
	public Level(int width, int height) {
		this.height = height / Tile.TILE_SIZE;
		this.width = width / Tile.TILE_SIZE;
		
		this.prevHeight = this.height;
		this.prevWidth = this.width;
				
		tiles = new Tile[this.width][this.height];
		
		for(int y = 0; y < this.height; y++)
			for(int x = 0; x < this.width; x++) {
				tiles[x][y] = new Tile(x, y);
			}
	}
	
	public void render(Graphics g) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				tiles[x][y].render(g);
			}
		}
	}
	
	public void setTile(int x, int y, Tile tile) {
		if(x < 0 || y < 0 || x >= width || y >= height) return;
		
		tiles[x][y] = tile;
	}
	
	public void setTile(Vector2i vec, Tile tile) {
		setTile(vec.getX(), vec.getY(), tile);
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) return null;
		
		return tiles[x][y];
	}
	
	public Tile getTile(Vector2i vec) {
		return getTile(vec.getX(), vec.getY());
	}
	
	public void restore() {
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++) {
				tiles[x][y].setColor(tiles[x][y].original);
				tiles[x][y].text = "";
			}
	}
	
	public void restart() {
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				tiles[x][y] = new Tile(x, y);
		}
	
	public void recalculateSize(int tileSize) {
		int prevSize = Tile.TILE_SIZE;
		
		prevWidth = (prevWidth * Tile.TILE_SIZE) / tileSize;
		prevHeight = (prevHeight * Tile.TILE_SIZE) / tileSize;
		
		int w = (int)Math.ceil(prevWidth);
		int h = (int)Math.ceil(prevHeight);
				
		Tile[][] result = new Tile[w][h];
		
		Tile.TILE_SIZE = tileSize;
		
		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++) {
				if(x < width && y < height) {
					result[x][y] = tiles[x][y].recalculatePos(prevSize);
				} else {
					result[x][y] = new Tile(x, y);
				}
			}
		}
		
		this.width = w;
		this.height = h;
		this.tiles = result;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
