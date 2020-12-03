package moore_machine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.QuadCurve2D;
import java.awt.BasicStroke;
import java.awt.Stroke;

class Edge {
    protected Node startNode;
    protected Node endNode;

    protected int height = 100;

    boolean mouseOver = false;

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

    protected double[] getStartPoint() {
        double sx = getPeakPos()[0];
        double sy = getPeakPos()[1];
        double ex = startNode.getX();
        double ey = startNode.getY();

        return getIntersectWithCircle(sx, sy, ex, ey);
    }

    protected double[] getEndPoint() {
        double sx = getPeakPos()[0];
        double sy = getPeakPos()[1];
        double ex = endNode.getX();
        double ey = endNode.getY();

        return getIntersectWithCircle(sx, sy, ex, ey);
    }

    private double[] getIntersectWithCircle(double sx, double sy, double ex, double ey) {
        double xDiff = ex - sx;
        double yDiff = ey - sy;
        int r = endNode.getR();

        double dist = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
        double x = (ex - (r * xDiff) / dist);
        double y = (ey - (r * yDiff) / dist);
        double[] point = { x, y };
        return point;
    }

    boolean linePoint(double x1, double y1, double x2, double y2, double px, double py) {
        double d1 = dist(px, py, x1, y1);
        double d2 = dist(px, py, x2, y2);
        double lineLen = dist(x1, y1, x2, y2);
        double buffer = 0.2;
        if (d1 + d2 >= lineLen - buffer && d1 + d2 <= lineLen + buffer) {
            return true;
        }
        return false;
    }

    private void drawArc(Graphics g, int sx, int sy, int px, int py, int ex, int ey) {
        Graphics2D g2d = (Graphics2D) g;
        QuadCurve2D q = new QuadCurve2D.Float();

        q.setCurve(sx, sy, px, py, ex, ey);
        g2d.draw(q);
    }

    protected double[] getPeakPos() {
        double sx = startNode.getX();
        double sy = startNode.getY();
        double ex = endNode.getX();
        double ey = endNode.getY();
        double angle = Math.atan2(ey - sy, ex - sx);

        double[] middle = getMiddlePoint(sx, sy, ex, ey);

        double peakX = middle[0] + height * Math.cos(angle - Math.PI / 2);
        double peakY = middle[1] + height * Math.sin(angle - Math.PI / 2);

        double[] peak = { peakX, peakY };
        return peak;
    }

    protected double[] getMiddlePoint(double sx, double sy, double ex, double ey) {
        double middleX = (sx + ex) / 2;
        double middleY = (sy + ey) / 2;
        double[] middle = {middleX, middleY};
        return middle;
    }

    private void drawArrowHead(Graphics g, double x1, double y1, double x2, double y2) {
        Graphics2D g2d = (Graphics2D) g;

        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(0, 5);
        arrowHead.addPoint(-5, -5);
        arrowHead.addPoint(5, -5);

        double angle = Math.atan2(y2 - y1, x2 - x1);
        g2d.translate(x2, y2);
        g2d.rotate((angle - Math.PI / 2d));

        g2d.fill(arrowHead);

        g2d.rotate(-(angle - Math.PI / 2d));
        g2d.translate(-x2, -y2);
    }

    private double dist(double x1, double y1, double x2, double y2) {
        double xDiff = x1 - x2;
        double yDiff = y1 - y2;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    void draw(Graphics g) {
        g.setColor(Color.BLACK);

        int sx = (int) getStartPoint()[0];
        int sy = (int) getStartPoint()[1];
        int ex = (int) getEndPoint()[0];
        int ey = (int) getEndPoint()[1];

        int px = (int) getPeakPos()[0];
        int py = (int) getPeakPos()[1];

        drawArc(g, sx, sy, px, py, ex, ey);
        drawArrowHead(g, px, py, ex, ey);
        g.drawOval(px, py, 10, 10);

        drawEnclosingRect(g);
    }

    protected void drawEnclosingRect(Graphics g) {
        if(!mouseOver) return;
    
        Graphics2D g2d = (Graphics2D) g;

        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g.setColor(Color.RED);

        Polygon rect = enclosingRect();
        g2d.draw(rect);

        g2d.setStroke(new BasicStroke());
    }

    @Override
    public String toString() {
        return startNode + "-" + endNode;
    }

    protected Polygon enclosingRect() {
        int sx = (int) getStartPoint()[0];
        int sy = (int) getStartPoint()[1];
        int ex = (int) getEndPoint()[0];
        int ey = (int) getEndPoint()[1];

        double[] m = getMiddlePoint(sx, sy, ex, ey);
        double[] p = getPeakPos();
        int distY = (int) dist(m[0], m[1], p[0], p[1]);

        Polygon rect = new Polygon();

        double angle = Math.atan2(ey - sy, ex - sx);

        rect.addPoint(sx, sy);
        rect.addPoint(ex, ey);

        int diffX = (int) (Math.cos(angle - Math.PI / 2) * distY);
        int diffY = (int) (Math.sin(angle - Math.PI /2) * distY);

        rect.addPoint(ex + diffX, ey + diffY);
        rect.addPoint(sx + diffX, sy + diffY);
        return rect;
    }

    boolean isMouseOver(int mx, int my) {
        Polygon rect = enclosingRect();

        if(rect.contains(mx, my)) {
            mouseOver = true;
            return true;
        }
        mouseOver = false;
        return false;
    }

    public void move(double x, double y) {
        startNode.move(x, y);
        endNode.move(x, y);
    }


}