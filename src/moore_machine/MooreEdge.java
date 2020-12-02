package moore_machine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

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

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
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
    }


    @Override
    public String toString() {
        return startNode + "- " + input + " ->" + endNode;
    }
}
