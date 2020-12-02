package moore_machine;

import java.awt.Color;
import java.awt.Graphics;

public class Node {

	protected double x;
	protected double y;
	protected int r = 40;

	public Node(double x, double y) {
		setX(x);
		setY(y);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public void move(double dx, double dy) {
		setX(x + dx);
		setY(y + dy);
	}

	public boolean isMouseOver(int mx, int my) {
		return (x - mx) * (x - mx) + (y - my) * (y - my) <= r * r;
	}

	void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawOval((int) x - r, (int) y - r, 2 * r, 2 * r);
	}

	@Override
	public String toString() {
		return ("(x: " + x + ", y: " + y + ", r: " + r + ")");
	}
}
