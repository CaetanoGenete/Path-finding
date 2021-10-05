package pathFinding;

public class Vector2i {

	private int x = 0, y = 0;

	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2i(Vector2i vec) {
		this(vec.x, vec.y);
	}

	public Vector2i() {

	}

	public Vector2i subtract(int x, int y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	public Vector2i subtract(Vector2i vec) {
		return subtract(vec.x, vec.y);
	}

	public Vector2i add(int x, int y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public Vector2i add(Vector2i vec) {
		return add(vec.x, vec.y);
	}

	public double getAngle() {
		return Math.atan2(y, x);
	}

	public boolean equals(Vector2i vec) {
		if (vec.x == x && vec.y == y) return true;
		return false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
