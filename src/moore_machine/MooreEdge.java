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
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.geom.Point2D;

public class MooreEdge extends Edge {

    private static final long serialVersionUID = 1L;
    private String input;

    MooreEdge(MooreNode startNode, MooreNode endNode, String input, int curveHeight) {
        this(startNode, endNode, input);
        setCurveHeight(curveHeight);
    }

    MooreEdge(MooreNode startNode, MooreNode endNode, String input) {
        super(startNode, endNode);
        setInput(input);
    }

    MooreEdge() {
        super();
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    void draw(Graphics g) {
        super.draw(g);

        Font font = new Font("TimesRoman", Font.BOLD, 20);

        Point2D.Double t = getPeakPos();

        // int tx = (int) getPeakPos()[0];
        // int ty = (int) getPeakPos()[1];
        FontMetrics metrics = g.getFontMetrics(font);

        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(input,(int) (t.x - metrics.stringWidth(input) / 2),(int) (t.y + metrics.getAscent() / 2));
    }

    @Override
    public String toString() {
        return startNode + "  - " + input + " ->  " + endNode + ", " + curveHeight;
    }
}
