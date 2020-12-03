package moore_machine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Font;
import java.awt.Color;

import java.awt.FontMetrics;


public class MooreEdge extends Edge {

    private String input;

    private void drawArrowHead(Graphics g, int x1, int y1, int x2, int y2) {

        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(0, 5);
        arrowHead.addPoint(-5, -5);
        arrowHead.addPoint(5, -5);

        Graphics2D g2d = (Graphics2D) g;
        double angle = Math.atan2(y2 - y1, x2 - x1);
        g2d.translate(x2, y2);
        g2d.rotate((angle - Math.PI / 2d));

        g2d.fill(arrowHead);

        g2d.rotate(-(angle - Math.PI / 2d));
        g2d.translate(-x2, -y2);
    }

    MooreEdge(MooreNode startNode, MooreNode endNode, String input) {
        super(startNode, endNode);
        this.input = input;
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

    private int[] getTextPosition() {

        double sx = startNode.getX();
        double sy = startNode.getY();
        double ex = endNode.getX();
        double ey = endNode.getY();

        double xDiff = sx - ex;
        double yDiff = sy - ey;

        double dist = Math.sqrt(xDiff * xDiff + yDiff * yDiff);

        int x = (int) (sx - (dist / 2 * xDiff) / dist);
        int y = (int) (sy - (dist / 2 * yDiff) / dist);

        int[] point = { x, y };
        return point;

    }

    @Override
    void draw(Graphics g) {
        super.draw(g);

        int[] startPoint = getStartPoint();
        int[] endPoint = getEndPoint();

        int x1 = startPoint[0];
        int y1 = startPoint[1];
        int x2 = endPoint[0];
        int y2 = endPoint[1];

        drawArrowHead(g, x1, y1, x2, y2);

        Font font = new Font("TimesRoman", Font.BOLD, 20 );

        int centerX = getTextPosition()[0];
        int centerY = getTextPosition()[1];
        FontMetrics metrics = g.getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.fillOval(centerX - 20, centerY -20, 40, 40);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(input, centerX - metrics.stringWidth(input) / 2, centerY + metrics.getAscent() / 2);

    }

    @Override
    public String toString() {
        return startNode + "- " + input + " ->" + endNode;
    }
}
