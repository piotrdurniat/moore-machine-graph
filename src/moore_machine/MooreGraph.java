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

import javax.swing.JOptionPane;

import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class MooreGraph implements Serializable {

    private static final long serialVersionUID = 1L;
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

    public static void serialize(String fileName, MooreGraph graph) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(graph);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static MooreGraph deserialize(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            return (MooreGraph) inputStream.readObject();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

}
