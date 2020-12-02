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

    void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine(startNode.getX(), startNode.getY(), endNode.getX(), endNode.getY());
    }
    
    @Override
	public String toString() {
		return startNode + "-" + endNode;
	}

}