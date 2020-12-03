package moore_machine;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.FontMetrics;

public class MooreEdge extends Edge {

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

        int tx = (int) getPeakPos()[0];
        int ty = (int) getPeakPos()[1];
        FontMetrics metrics = g.getFontMetrics(font);

        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(input, tx - metrics.stringWidth(input) / 2, ty + metrics.getAscent() / 2);
    }

    @Override
    public String toString() {
        return startNode + "  - " + input + " ->  " + endNode + ", " + curveHeight;
    }
}
