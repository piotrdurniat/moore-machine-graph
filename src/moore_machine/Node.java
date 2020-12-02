package moore_machine;

import java.awt.Color;
import java.awt.Graphics;

public class Node {

	protected int x;
	protected int y;
	protected int r = 40;

	public Node(int x, int y) {
		setX(x);
		setY(y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public boolean isMouseOver(int mx, int my) {
		return (x - mx) * (x - mx) + (y - my) * (y - my) <= r * r;
	}

	void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawOval(x - r, y - r, 2 * r, 2 * r);
	}

	@Override
	public String toString() {
		return ("(x: " + x + ", y: " + y + ", r: " + r + ")");
	}
}
