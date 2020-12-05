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
import java.awt.geom.Point2D;

public class Node implements Serializable {

	private static final long serialVersionUID = 1L;
	protected Point2D.Double pos;
	protected int r = 40;
	private boolean mouseOver;

	public Node(double x, double y) {
		pos = new Point2D.Double(x, y);
	}

	public Point2D.Double getPos() {
		return pos;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public void move(double dx, double dy) {
		pos.setLocation(pos.x + dx, pos.y + dy);
	}

	public boolean isMouseOver(int mx, int my) {
		mouseOver = (pos.x - mx) * (pos.x - mx) + (pos.y - my) * (pos.y - my) <= r * r;
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

		g.drawOval((int) pos.x - r, (int) pos.y - r, 2 * r, 2 * r);
	}

	@Override
	public String toString() {
		return ("(x: " + pos.x + ", y: " + pos.y + ", r: " + r + ")");
	}
}
