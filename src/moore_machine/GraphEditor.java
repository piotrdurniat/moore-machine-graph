package moore_machine;

import javax.swing.JFrame;

public class GraphEditor extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final String APP_TITLE = "Moore machine graph editor";

    private GraphPanel panel = new GraphPanel();

    public static void main(String[] args) {
        new GraphEditor();
    }

    GraphEditor() {
        super(APP_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 800);
		setLocationRelativeTo(null);
		setContentPane(panel);
		showBuiltInExample();
		setVisible(true);
    }

    private void showBuiltInExample() {
		MooreGraph graph = new MooreGraph();

		MooreNode n1 = new MooreNode(100, 100, "oong state name", "y1");
		MooreNode n2 = new MooreNode(100, 200, "q2", "y2");
		MooreNode n3 = new MooreNode(200, 100, "q3", "y1");
		MooreNode n4 = new MooreNode(200, 250, "q4", "y2");

		MooreEdge e1 = new MooreEdge(n1, n2, "z1");
		MooreEdge e2 = new MooreEdge(n2, n3, "z2");

		graph.addNode(n1);
		graph.addNode(n2);
		graph.addNode(n3);
		graph.addNode(n4);

		graph.addEdge(e1);
		graph.addEdge(e2);

		panel.setGraph(graph);
	}
}
