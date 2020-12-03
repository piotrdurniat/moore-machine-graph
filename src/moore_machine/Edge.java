package moore_machine;

import java.awt.Color;
import java.awt.Graphics;

class Edge {
    protected Node startNode;
    protected Node endNode;

    Edge(Node startNode, Node endNode) {
        setStartNode(startNode);
        setEndNode(endNode);
    }

    Edge() {

    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    protected int[] getStartPoint() {

        double sx = startNode.getX();
        double sy = startNode.getY();
        double ex = endNode.getX();
        double ey = endNode.getY();

        double xDiff = sx - ex;
        double yDiff = sy - ey;

        int r = startNode.getR();

        double dist = Math.sqrt(xDiff * xDiff + yDiff * yDiff);

        int x = (int) (sx - (r * xDiff) / dist);
        int y = (int) (sy - (r * yDiff) / dist);

        int[] point = { x, y };
        return point;
    }

    protected int[] getEndPoint() {

        double sx = startNode.getX();
        double sy = startNode.getY();
        double ex = endNode.getX();
        double ey = endNode.getY();

        double xDiff = ex - sx;
        double yDiff = ey - sy;

        int r = endNode.getR();

        double dist = Math.sqrt(xDiff * xDiff + yDiff * yDiff);

        int x = (int) (ex - (r * xDiff) / dist);
        int y = (int) (ey - (r * yDiff) / dist);

        int[] point = { x, y };
        return point;
    }

    boolean isMouseOver(int mx, int my) {
        double sx = startNode.getX();
        double sy = startNode.getY();
        double ex = endNode.getX();
        double ey = endNode.getY();
        return linePoint(sx, sy, ex, ey, mx, my);
    }

    boolean linePoint(double x1, double y1, double x2, double y2, double px, double py) {
        double d1 = dist(px, py, x1, y1);
        double d2 = dist(px, py, x2, y2);
        double lineLen = dist(x1, y1, x2, y2);
        double buffer = 0.2; // higher # = less accurate
        if (d1 + d2 >= lineLen - buffer && d1 + d2 <= lineLen + buffer) {
            return true;
        }
        return false;
    }

    private double dist(double x1, double y1, double x2, double y2) {
        double xDiff = x1 - x2;
        double yDiff = y1 - y2;
        return  Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(getStartPoint()[0], getStartPoint()[1], getEndPoint()[0], getEndPoint()[1]);
    }

    @Override
    public String toString() {
        return startNode + "-" + endNode;
    }

}