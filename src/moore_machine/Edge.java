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
import java.awt.Polygon;
import java.awt.geom.QuadCurve2D;
import java.io.Serializable;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.geom.Point2D;

class Edge implements Serializable {

    private static final long serialVersionUID = 1L;
    protected Node startNode;
    protected Node endNode;

    protected int curveHeight = 0;

    boolean mouseOver = false;

    Edge(Node startNode, Node endNode) {
        setStartNode(startNode);
        setEndNode(endNode);
    }

    Edge() {

    }

    public int getCurveHeight() {
        return curveHeight;
    }

    public void setCurveHeight(int height) {
        curveHeight = height;
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

    protected Point2D.Double getStartPoint() {
        Point2D.Double s = getPeakPos();
        Point2D.Double e = startNode.getPos();
        return getIntersectWithCircle(s, e);
    }

    protected Point2D.Double getEndPoint() {
        Point2D.Double s = getPeakPos();
        Point2D.Double e = endNode.getPos();

        return getIntersectWithCircle(s, e);
    }

    private Point2D.Double getIntersectWithCircle(Point2D.Double s, Point2D.Double e) {
        double xDiff = e.x - s.x;
        double yDiff = e.y - s.y;
        int r = endNode.getR();

        double dist = s.distance(e);
        double x = (e.x - (r * xDiff) / dist);
        double y = (e.y - (r * yDiff) / dist);

        return new Point2D.Double(x, y);
    }

    boolean linePoint(Point2D.Double x, Point2D.Double y,Point2D.Double p) {
        double d1 = p.distance(x);
        double d2 = p.distance(y);
        double lineLen = x.distance(y);

        double buffer = 0.2;
        if (d1 + d2 >= lineLen - buffer && d1 + d2 <= lineLen + buffer) {
            return true;
        }
        return false;
    }

    private QuadCurve2D getCurve() {
        Point2D.Double s = getStartPoint();
        Point2D.Double e = getEndPoint();
        Point2D.Double p = getPeakPos();
        QuadCurve2D curve = new QuadCurve2D.Float();

        curve.setCurve(s.x, s.y, p.x, p.y, e.x, e.y);

        return curve;
    }

    protected Point2D.Double getPeakPos() {
        Point2D.Double s = startNode.getPos();
        Point2D.Double e = endNode.getPos();

        double angle = Math.atan2(e.y - s.y, e.x - s.x);

        Point2D.Double middle = getMiddlePoint(s, e);

        double peakX = middle.x + curveHeight * Math.cos(angle - Math.PI / 2);
        double peakY = middle.y + curveHeight * Math.sin(angle - Math.PI / 2);

        return new Point2D.Double(peakX, peakY);
    }

    protected Point2D.Double getMiddlePoint(Point2D.Double s, Point2D.Double e) {
        double middleX = (s.x + e.x) / 2;
        double middleY = (s.y + e.y) / 2;
        return new Point2D.Double(middleX, middleY);
    }

    private void drawArrowHead(Graphics g, Point2D.Double s, Point2D.Double e) {
        Graphics2D g2d = (Graphics2D) g;

        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(0, 5);
        arrowHead.addPoint(-5, -5);
        arrowHead.addPoint(5, -5);

        double angle = Math.atan2(e.y - s.y, e.x - s.x);
        g2d.translate(e.x, e.y);
        g2d.rotate((angle - Math.PI / 2d));

        g2d.fill(arrowHead);

        g2d.rotate(-(angle - Math.PI / 2d));
        g2d.translate(-e.x, -e.y);
    }

    void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.BLACK);

        Point2D.Double e = getEndPoint();
        Point2D.Double p = getPeakPos();
        QuadCurve2D curve = getCurve();
        g2d.draw(curve);

        drawArrowHead(g, p, e);

        drawEnclosingRect(g);
    }

    protected void drawEnclosingRect(Graphics g) {
        if (!mouseOver)
            return;

        Graphics2D g2d = (Graphics2D) g;

        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
        g2d.setStroke(dashed);
        g.setColor(Color.RED);

        Polygon rect = enclosingRect();
        g2d.draw(rect);

        g2d.setStroke(new BasicStroke());
    }

    @Override
    public String toString() {
        return startNode + "  ->  " + endNode;
    }

    protected Polygon enclosingRect() {
        Point2D.Double s = getStartPoint();
        Point2D.Double e = getEndPoint();

        int distY = curveHeight;

        Polygon rect = new Polygon();

        double angle = Math.atan2(e.y - s.y, e.x - s.x);

        rect.addPoint((int)s.x,(int) s.y);
        rect.addPoint((int)e.x,(int) e.y);

        int diffX = (int) (Math.cos(angle - Math.PI / 2) * distY);
        int diffY = (int) (Math.sin(angle - Math.PI / 2) * distY);

        rect.addPoint((int)(e.x + diffX),(int) (e.y + diffY));
        rect.addPoint((int)(s.x + diffX),(int) (s.y + diffY));
        return rect;
    }

    boolean isMouseOver(int mx, int my) {
        Polygon rect = enclosingRect();

        if (Math.abs(curveHeight) <= 3) {

            Point2D.Double s = startNode.getPos();
            Point2D.Double e = endNode.getPos();

            mouseOver = linePoint(s, e, new Point2D.Double(mx, my));
        } else {
            mouseOver = rect.contains(mx, my);
        }
        return mouseOver;
    }

    public void move(double x, double y) {
        startNode.move(x, y);
        endNode.move(x, y);
    }

}