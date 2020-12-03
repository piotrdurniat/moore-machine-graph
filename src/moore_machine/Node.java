/*
 *  Application for editing and graphical visualization of Moore Machine Graphs
 * 
 *  Author: Piotr Durniat
 *  Date: 03.12.2020
 *  Group: E03-11f
 */

package moore_machine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.io.Serializable;

public class Node implements Serializable {

	private static final long serialVersionUID = 1L;
	protected double x;
	protected double y;
	protected int r = 40;
	boolean mouseOver;

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
		mouseOver = (x - mx) * (x - mx) + (y - my) * (y - my) <= r * r;
		return mouseOver;
	}

	void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (mouseOver) {
			Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
			g2d.setStroke(dashed);
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke());
		}

		g.drawOval((int) x - r, (int) y - r, 2 * r, 2 * r);
	}

	@Override
	public String toString() {
		return ("(x: " + x + ", y: " + y + ", r: " + r + ")");
	}
}
