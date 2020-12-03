package moore_machine;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
			+ "   arrow keys -> translate view\n" + "   +/= -> zoom in\n" + "   -/_ -> zoom out\n"
			+ "   SHIFT + arrow keys --> translate view faster\n\n" + "When mouse cursor is over a node:\n"
			+ "   DEL   --> delete node\n" + "   +/=   --> increse node size\n" + "   -/_   --> decrese node size\n\n"
			+ "Mouse actions:\n" + "   Left button drag --> translate view\n" + "   Scroll wheel --> scale view\n"
			+ "   Right click --> create new node\n" + "When curson is over a node:\n"
			+ "   Left button drag --> move node\n" + "   Right click --> delete node\n";

	private GraphPanel panel = new GraphPanel();

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
		setSize(800, 800);
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
		// TODO - edge liset
		System.out.println("Show list of nodes");
	}

	private void showEdgeList() {
		// TODO  - node list
		System.out.println("Edge list");
	}

	private void showAuthor() {
		JOptionPane.showMessageDialog(this, APP_AUTHOR, APP_TITLE, JOptionPane.INFORMATION_MESSAGE);
	}

	private void showInstructions() {
		JOptionPane.showMessageDialog(this, APP_INSTRUCTION, APP_TITLE, JOptionPane.PLAIN_MESSAGE);
	}


	private void showBuiltInExample() {
		MooreGraph graph = new MooreGraph();

		MooreNode n1 = new MooreNode(100, 100, "q1", "y1");
		MooreNode n2 = new MooreNode(100, 400, "q2", "y2");
		MooreNode n3 = new MooreNode(400, 200, "q3", "y1");
		MooreNode n4 = new MooreNode(400, 500, "q4", "y2");

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
