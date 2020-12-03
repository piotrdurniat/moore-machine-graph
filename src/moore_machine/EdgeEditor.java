package moore_machine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

public class EdgeEditor implements MouseMotionListener, MouseListener {
    private boolean active = false;
    private boolean addMode = false;

    private MooreEdge edge;
    private int mouseX = 0;
    private int mouseY = 0;
    private GraphPanel panel;

    EdgeEditor(GraphPanel panel) {
        this.panel = panel;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public MooreEdge getEdge() {
        return edge;
    }

    public void setEdge(MooreEdge edge) {
        this.edge = edge;
    }

    void draw(Graphics g) {
        g.setColor(Color.BLACK);

        if (!active)
            return;

        Node startNode = edge.getStartNode();

        if (startNode == null) {
            g.drawString("Select start node...", panel.getWidth() / 2, 40);
        } else {
            g.drawString("Select end node...", panel.getWidth() / 2, 40);

            double scale = panel.getScale();
            int translateX = panel.getTranslateX();
            int translateY = panel.getTranslateY();

            int x = (int) (startNode.getX() * scale + translateX);
            int y = (int) (startNode.getY() * scale + translateY);

            g.drawLine(x, y, mouseX, mouseY);
        }
    }

    public void newEdge(MooreNode startNode) {
        active = true;
        addMode = true;
        edge = new MooreEdge();
        if (startNode != null) {
            edge.setStartNode(startNode);
        }
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
    }

    @Override
    public void mouseMoved(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        if (!active)
            return;
        Node nodeUnderCursor = panel.getNodeUnderCursor();
        Node startNode = edge.getStartNode();
        Node endNode = edge.getEndNode();

        if (nodeUnderCursor == null)
            return;

        if (startNode == null) {
            edge.setStartNode(nodeUnderCursor);
        } else if (endNode == null) {
            edge.setEndNode(nodeUnderCursor);
            String input = JOptionPane.showInputDialog("Enter edge input value:");
            edge.setInput(input);

            MooreGraph graph = panel.getGraph();

            if (addMode)
                graph.addEdge(edge);
            panel.repaint();
            clearFields();
        }
    }

    private void clearFields() {
        active = false;
        addMode = false;
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

}
