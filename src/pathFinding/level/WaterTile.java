package pathFinding.level;

import java.awt.Color;

public class WaterTile extends Tile {

	public WaterTile(int x, int y) {
		super(x, y);
		
		setColor(new Color(0xFF00007F));
		original = new Color(0xFF00007F);
		
		cost = 5;
	}

}
