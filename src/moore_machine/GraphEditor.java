package moore_machine;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GraphEditor extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String APP_TITLE = "Moore machine graph editor";
	private static final String APP_AUTHOR = "Autor: Piotr Durniat\n  Data: grudzień 2020";
	private static final String APP_INSTRUCTION = "                  APP INSTRUCTIONS \n\n" + "Active keys:\n"
			+ "   arrow keys   --> translate view\n" + "   +/=   --> zoom in\n" + "   -/_   --> zoom out\n"
			+ "   SHIFT + arrow keys   --> translate view faster\n" + "   E   -> create new Edge\n\n"
			+ "When mouse cursor is over a node:\n" + "   DEL   --> delete node\n\n" + "Mouse actions:\n"
			+ "   Left button drag --> translate view\n" + "   Scroll wheel --> scale view\n"
			+ "   Right click --> show options popup menu\n" + "When curson is over a node:\n"
			+ "   Left button drag --> move node\n" + "   Right click --> show ndde options\n";

	private static final int viewWidth = 800;
	private static final int viewHeight = 800;
	private GraphPanel panel = new GraphPanel(viewWidth / 2, viewHeight / 2);

	public static void main(String[] args) {
		new GraphEditor();
	}

	private JMenuBar menuBar = new JMenuBar();
	private JMenu menuGraph = new JMenu("Graph");
	private JMenu menuHelp = new JMenu("Help");
	private JMenuItem menuNew = new JMenuItem("New", KeyEvent.VK_N);
	private JMenuItem menuShowExample = new JMenuItem("Example", KeyEvent.VK_X);
	private JMenuItem menuExit = new JMenuItem("Exit", KeyEvent.VK_E);
	private JMenuItem menuNodeList = new JMenuItem("List of Nodes", KeyEvent.VK_N);
	private JMenuItem menuEdgeList = new JMenuItem("List of Edges");
	private JMenuItem menuAuthor = new JMenuItem("Author", KeyEvent.VK_A);
	private JMenuItem menuInstruction = new JMenuItem("Instruction", KeyEvent.VK_I);

	GraphEditor() {
		super(APP_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(viewWidth, viewHeight);
		setLocationRelativeTo(null);
		setContentPane(panel);
		createMenu();
		showBuiltInExample();
		setVisible(true);
	}

	private void createMenu() {
		menuNew.addActionListener(e -> setNewGraph());
		menuShowExample.addActionListener(e -> showBuiltInExample());
		menuExit.addActionListener(e -> System.exit(0));
		menuNodeList.addActionListener(e -> showNodeList());
		menuEdgeList.addActionListener(e -> showEdgeList());
		menuAuthor.addActionListener(e -> showAuthor());
		menuInstruction.addActionListener(e -> showInstructions());

		menuGraph.setMnemonic(KeyEvent.VK_G);
		menuGraph.add(menuNew);
		menuGraph.add(menuShowExample);
		menuGraph.addSeparator();
		menuGraph.add(menuNodeList);
		menuGraph.add(menuEdgeList);
		menuGraph.addSeparator();
		menuGraph.add(menuExit);

		menuHelp.setMnemonic(KeyEvent.VK_H);
		menuHelp.add(menuInstruction);
		menuHelp.add(menuAuthor);

		menuBar.add(menuGraph);
		menuBar.add(menuHelp);
		setJMenuBar(menuBar);
	}

	private void setNewGraph() {
		panel.setGraph(new MooreGraph());
	}

	private void showNodeList() {
		String nodeList = panel.getNodeList();
		JOptionPane.showMessageDialog(this, nodeList);
	}

	private void showEdgeList() {
		String edgeList = panel.getEdgeList();
		JOptionPane.showMessageDialog(this, edgeList);
	}

	private void showAuthor() {
		JOptionPane.showMessageDialog(this, APP_AUTHOR, APP_TITLE, JOptionPane.INFORMATION_MESSAGE);
	}

	private void showInstructions() {
		JOptionPane.showMessageDialog(this, APP_INSTRUCTION, APP_TITLE, JOptionPane.PLAIN_MESSAGE);
	}

	private void showBuiltInExample() {
		MooreGraph graph = new MooreGraph();

		MooreNode q0 = new MooreNode(-260, 0, "q0", "y0");
		MooreNode q1 = new MooreNode(-20, -140, "q1", "y1");
		MooreNode q2 = new MooreNode(-20, 140, "q2", "y1");
		MooreNode q3 = new MooreNode(260, -140, "q3", "y1");
		MooreNode q4 = new MooreNode(260, 140, "q4", "y1");

		MooreEdge e1 = new MooreEdge(q0, q1, "z1", 0);
		MooreEdge e2 = new MooreEdge(q0, q2, "z0", 0);
		MooreEdge e3 = new MooreEdge(q1, q3, "z1", 50);
		MooreEdge e4 = new MooreEdge(q1, q3, "z0", -50);
		MooreEdge e5 = new MooreEdge(q3, q4, "z1", 0);
		MooreEdge e6 = new MooreEdge(q4, q2, "z0", 0);
		MooreEdge e7 = new MooreEdge(q2, q3, "z0", 50);
		MooreEdge e8 = new MooreEdge(q3, q2, "z0", 50);
		MooreEdge e9 = new MooreEdge(q2, q1, "z1", 0);
		MooreEdge e10 = new MooreEdge(q4, q1, "z1", 0);

		graph.addNode(q0);
		graph.addNode(q1);
		graph.addNode(q2);
		graph.addNode(q3);
		graph.addNode(q4);

		graph.addEdge(e1);
		graph.addEdge(e2);
		graph.addEdge(e3);
		graph.addEdge(e4);
		graph.addEdge(e5);
		graph.addEdge(e6);
		graph.addEdge(e7);
		graph.addEdge(e8);
		graph.addEdge(e9);
		graph.addEdge(e10);

		panel.setGraph(graph);
	}

}
