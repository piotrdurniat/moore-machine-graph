package moore_machine;

import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;

public class MooreGraph {

    private List<MooreNode> nodes;
    private List<MooreEdge> edges;

    MooreGraph() {
        nodes = new ArrayList<MooreNode>();
        edges = new ArrayList<MooreEdge>();
    }

    public void addNode(MooreNode node) {
        nodes.add(node);
    }

    public void removeNode(MooreNode node) {
        nodes.remove(node);
    }

    public ArrayList<MooreNode> getNodes() {
        return new ArrayList<MooreNode>(nodes);
    }

    public void draw(Graphics g) {
        for (MooreEdge edge : edges) {
            edge.draw(g);
        }
        for (MooreNode node : nodes) {
            node.draw(g);
        }
    }

}
