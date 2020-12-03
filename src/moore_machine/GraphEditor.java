/*
 *  Application for editing and graphical visualization of Moore Machine Graphs
 * 
 *  Author: Piotr Durniat
 *  Date: 03.12.2020
 *  Group: E03-11f
 */

package moore_machine;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GraphEditor extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String APP_TITLE = "Moore machine graph editor";
	private static final String APP_AUTHOR = "Autor: Piotr Durniat\n  Data: grudzień 2020";

	// @formatter:off
	private static final String APP_INSTRUCTION = "                  APP INSTRUCTIONS \n\n" 
		+ "Active keys:\n"
		+ "        Arrow keys           -   translate view\n" 
		+ "        + / =                -   zoom in\n" 
		+ "        - / _                -   zoom out\n"
		+ "        SHIFT + Arrow keys   -   translate view faster\n" 
		+ "        E                    -   create new Edge\n\n"
		+ "Mouse actions:\n"
		+ "    When cursor is over the canvas:\n"
		+ "        Left button drag     -   translate view\n" 
		+ "        Scroll wheel         -   zoom in / zoom out\n"
		+ "        Right click          -   show graph popup menu\n\n" 
		+ "    When cursor is over a node:\n"
		+ "        Left button drag     -   move node\n" 
		+ "        Right click          -   show node popup menu\n\n"
		+ "    When cursor is over an edge:\n" 
		+ "        Left button drag     -   move edge\n"
		+ "        Right click          -   show edge popup menu";

	//@formatter:on

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
	private JMenuItem menuSave = new JMenuItem("Save to file", KeyEvent.VK_S);
	private JMenuItem menuLoad = new JMenuItem("Load from file", KeyEvent.VK_L);

	GraphEditor() {
		super(APP_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(viewWidth, viewHeight);
		setLocationRelativeTo(null);
		setContentPane(panel);
		createMenu();
		showBuiltInExample();
		setVisible(true);

		Font font = new Font("MonoSpaced", Font.BOLD, 12);
		UIManager.put("OptionPane.messageFont", font);
	}

	private void createMenu() {
		menuNew.addActionListener(e -> setNewGraph());
		menuShowExample.addActionListener(e -> showBuiltInExample());
		menuExit.addActionListener(e -> System.exit(0));
		menuNodeList.addActionListener(e -> showNodeList());
		menuEdgeList.addActionListener(e -> showEdgeList());
		menuAuthor.addActionListener(e -> showAuthor());
		menuInstruction.addActionListener(e -> showInstructions());

		menuSave.addActionListener(e -> saveGraphToFile());
		menuLoad.addActionListener(e -> loadGraphFromFile());

		menuGraph.setMnemonic(KeyEvent.VK_G);
		menuGraph.add(menuNew);
		menuGraph.add(menuShowExample);
		menuGraph.addSeparator();
		menuGraph.add(menuNodeList);
		menuGraph.add(menuEdgeList);
		menuGraph.addSeparator();
		menuGraph.add(menuSave);
		menuGraph.add(menuLoad);
		menuGraph.addSeparator();

		menuGraph.add(menuExit);

		menuHelp.setMnemonic(KeyEvent.VK_H);
		menuHelp.add(menuInstruction);
		menuHelp.add(menuAuthor);

		menuBar.add(menuGraph);
		menuBar.add(menuHelp);
		setJMenuBar(menuBar);
	}

	private void saveGraphToFile() {
		MooreGraph graph = panel.getGraph();

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Binary files", "bin", "BIN");
		chooser.setFileFilter(filter);
		chooser.setCurrentDirectory(new File("."));
		int returnValue = chooser.showSaveDialog(this);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			String fileName = chooser.getSelectedFile().getName();
			MooreGraph.serialize(fileName, graph);
		}
	}

	private void loadGraphFromFile() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Binary files", "bin", "BIN");
		chooser.setFileFilter(filter);
		chooser.setCurrentDirectory(new File("."));
		int returnValue = chooser.showOpenDialog(this);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			String fileName = chooser.getSelectedFile().getName();
			MooreGraph graph = MooreGraph.deserialize(fileName);
			panel.setGraph(graph);
			panel.repaint();
		}
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
		MooreGraph graph = MooreGraph.deserialize("built-in-example.bin");
			panel.setGraph(graph);
			panel.repaint();
	}

}
