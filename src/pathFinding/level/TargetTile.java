package pathFinding.level;

import java.awt.Color;

public class TargetTile extends Tile {

	public TargetTile(int x, int y) {
		super(x, y);
		
		setColor(Color.green);
		
		original = Color.GREEN;
	}

}
