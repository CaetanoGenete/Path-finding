package pathFinding.level;

import java.awt.Color;

import pathFinding.Vector2i;

public class SolidTile extends Tile {

	public SolidTile(int x, int y) {
		super(x, y);
		
		setColor(Color.DARK_GRAY);
		
		original = Color.DARK_GRAY;
	}
	
	public SolidTile(Vector2i vec) {
		super(vec);
		
		setColor(Color.DARK_GRAY);
		
		original = Color.DARK_GRAY;
	}
	
	public SolidTile recalculatePos(int prevSize) {
		super.recalculatePos(prevSize);
		return this;
	}
	
	public boolean solid() {
		return true;
	}

}
