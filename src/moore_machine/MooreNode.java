/*
 *  Application for editing and graphical visualization of Moore Machine Graphs
 * 
 *  Author: Piotr Durniat
 *  Date: 03.12.2020
 *  Group: E03-11f
 */

package moore_machine;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;

public class MooreNode extends Node {

	private static final long serialVersionUID = 1L;
	private String state;
	private String output;

	public MooreNode(int x, int y, String state, String output) {
		super(x, y);
		setState(state);
		setOutput(output);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	@Override
	void draw(Graphics g) {
		super.draw(g);
		Font font = new Font("TimesRoman", Font.BOLD, r / 2);
		drawCenteredText(g, state, output, font, (int) pos.x, (int) pos.y);
	}

	public void drawCenteredText(Graphics g, String state, String output, Font font, int x, int y) {
		FontMetrics metrics = g.getFontMetrics(font);
		int textX = x - metrics.stringWidth(state) / 2;
		int textY = y - metrics.getAscent() / 2;
		g.setFont(font);
		g.drawString(state, textX, textY);

		textX = x - metrics.stringWidth(output) / 2;
		textY = y + metrics.getAscent();
		g.setFont(font);
		g.drawString(output, textX, textY);
	}

	@Override
	public String toString() {
		return ("(" + pos.x + ", " + pos.y + ", " + state + ", " + output + ")");
	}

}
