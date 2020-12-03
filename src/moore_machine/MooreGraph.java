/*
 *  Application for editing and graphical visualization of Moore Machine Graphs
 * 
 *  Author: Piotr Durniat
 *  Date: 03.12.2020
 *  Group: E03-11f
 */

package moore_machine;

import java.util.ArrayList;
import java.util.Iterator;
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

        for (Iterator<MooreEdge> it = edges.iterator(); it.hasNext();) {
            MooreEdge next = it.next();
            if (next.startNode.equals(node) || next.endNode.equals(node)) {
                it.remove();
            }
        }

    }

    public void addEdge(MooreEdge edge) {
        edges.add(edge);
    }

    public void removeEdge(MooreEdge edge) {
        edges.remove(edge);
    }

    public ArrayList<MooreNode> getNodes() {
        return new ArrayList<MooreNode>(nodes);
    }

    public ArrayList<MooreEdge> getEdges() {
        return new ArrayList<MooreEdge>(edges);
    }

    public void draw(Graphics g) {
        for (MooreEdge edge : edges) {
            edge.draw(g);
        }
        for (MooreNode node : nodes) {
            node.draw(g);
        }
    }

    String getEdgeList() {
        String list = "";
        for (MooreEdge edge : edges) {
            list += edge + "\n";
        }
        return list;
    }

    String getNodeList() {
        String list = "";
        for (MooreNode node : nodes) {
            list += node + "\n";
        }
        return list;
    }

}
