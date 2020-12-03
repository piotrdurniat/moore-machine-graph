package moore_machine;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.Color;
import java.awt.Cursor;

public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener, MouseWheelListener {

  private static final long serialVersionUID = 1L;
  private int translateX = 0;
  private int translateY = 0;
  private double scale = 1;
  private double minScale = 0.1;
  private int mouseX = 0;
  private int mouseY = 0;
  private boolean mouseButtonLeft = false;
  private boolean mouseButtonRight = false;

  private MooreNode nodeUnderCursor = null;
  private int mouseCursor = Cursor.DEFAULT_CURSOR;

  private EdgeEditor edgeEditor;
  protected MooreGraph graph;

  GraphPanel() {

    edgeEditor = new EdgeEditor(this);

    addMouseListener(this);
    addMouseListener(edgeEditor);
    addMouseMotionListener(this);
    addMouseMotionListener(edgeEditor);
    addMouseWheelListener(this);
    addKeyListener(this);
    setFocusable(true);
    requestFocus();
    setBackground(Color.WHITE);
  }

  protected void showGlobalPopupMenu(MouseEvent event) {
    JPopupMenu popup = new JPopupMenu();
    JMenuItem addNodeMenuItem = new JMenuItem("Create new node");
    JMenuItem addEdgeMenuItem = new JMenuItem("Create new edge");

    addNodeMenuItem.addActionListener(e -> createNewNode(event.getX(), event.getY()));
    addEdgeMenuItem.addActionListener(e -> addNewEdge());

    popup.add(addNodeMenuItem);
    popup.add(addEdgeMenuItem);
    popup.show(event.getComponent(), event.getX(), event.getY());
  }

  public void createNewNode(int x, int y) {
    String state = JOptionPane.showInputDialog("Enter node state value:");
    String output = JOptionPane.showInputDialog("Enter node output value:");
    MooreNode newNode = new MooreNode(x, y, state, output);
    graph.addNode(newNode);
    repaint();
  }

  protected void showNodePopupMenu(MouseEvent event, MooreNode node) {
    JPopupMenu popup = new JPopupMenu();
    JMenuItem editStateMenuItem = new JMenuItem("Edit state value");
    JMenuItem editOutputMenuItem = new JMenuItem("Edit output value");
    JMenuItem deleteNodeMenuItem = new JMenuItem("Delete node");

    editStateMenuItem.addActionListener(e -> editNodeState(node));
    editOutputMenuItem.addActionListener(e -> editNodeOutput(node));
    deleteNodeMenuItem.addActionListener(e -> removeNode(node));

    popup.add(editStateMenuItem);
    popup.add(editOutputMenuItem);
    popup.add(deleteNodeMenuItem);

    popup.show(event.getComponent(), event.getX(), event.getY());
  }

  private void editNodeState(MooreNode node) {
    String newState = JOptionPane.showInputDialog("Enter new state value:");
    node.setState(newState);
    repaint();
  }

  private void editNodeOutput(MooreNode node) {
    String newOutput = JOptionPane.showInputDialog("Enter new output value:");
    node.setOutput(newOutput);
    repaint();
  }

  private void removeNode(MooreNode node) {
    graph.removeNode(node);
    repaint();
  }

  public double getScale() {
    return scale;
  }

  public int getTranslateX() {
    return translateX;
  }

  public int getTranslateY() {
    return translateY;
  }

  public MooreNode getNodeUnderCursor() {
    return nodeUnderCursor;
  }

  public MooreGraph getGraph() {
    return graph;
  }

  public void setGraph(MooreGraph graph) {
    this.graph = graph;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    edgeEditor.draw(g);
    if (graph == null)
      return;

    Graphics2D g2d = (Graphics2D) g;

    g.translate(translateX, translateY);
    g2d.scale(scale, scale);
    graph.draw(g);
  }

  private void translateView(int x, int y) {
    translateX += x;
    translateY += y;
  }

  private void scaleView(double scaleChange) {
    scale += scaleChange;
    if (scale < minScale) {
      scale = minScale;
    }
  }

  private void setMouseCursor() {
    if (nodeUnderCursor != null) {
      mouseCursor = Cursor.HAND_CURSOR;
    } else if (mouseButtonLeft) {
      mouseCursor = Cursor.MOVE_CURSOR;
    } else {
      mouseCursor = Cursor.DEFAULT_CURSOR;
    }
    setCursor(Cursor.getPredefinedCursor(mouseCursor));
  }

  @Override
  public void keyPressed(KeyEvent event) {

    int transSpeed;
    double scaleSpeed = 0.02;

    if (event.isShiftDown()) {
      transSpeed = 10;
    } else {
      transSpeed = 1;
    }
    switch (event.getKeyCode()) {
      case KeyEvent.VK_LEFT:
        translateView(-transSpeed, 0);
        break;
      case KeyEvent.VK_RIGHT:
        translateView(transSpeed, 0);
        break;
      case KeyEvent.VK_UP:
        translateView(0, -transSpeed);
        break;
      case KeyEvent.VK_DOWN:
        translateView(0, transSpeed);
        break;
      case KeyEvent.VK_UNDERSCORE:
      case 45:
        scaleView(-scaleSpeed);
        break;
      case KeyEvent.VK_EQUALS:
        scaleView(scaleSpeed);
        break;
      case KeyEvent.VK_E:
        addNewEdge();
        break;
    }

    repaint();
  }

  private void addNewEdge() {
    edgeEditor.newEdge();
  }

  @Override
  public void keyReleased(KeyEvent arg0) {

  }

  @Override
  public void keyTyped(KeyEvent arg0) {

  }

  @Override
  public void mouseDragged(MouseEvent event) {
    if (mouseButtonLeft) {

      int diffX = event.getX() - mouseX;
      int diffY = event.getY() - mouseY;

      if (nodeUnderCursor != null) {

        double x = (diffX / scale);
        double y = (diffY / scale);

        nodeUnderCursor.move(x, y);
      } else {
        translateView(diffX, diffY);
      }
    }
    mouseX = event.getX();
    mouseY = event.getY();

    repaint();
  }

  private MooreNode findNode(int mouseX, int mouseY) {

    for (MooreNode node : graph.getNodes()) {

      int x = (int) ((mouseX - translateX) / scale);
      int y = (int) ((mouseY - translateY) / scale);

      if (node.isMouseOver(x, y)) {
        return node;
      }
    }
    return null;
  }

  @Override
  public void mouseMoved(MouseEvent event) {
    nodeUnderCursor = findNode(event.getX(), event.getY());
    setMouseCursor();
    repaint();
  }

  @Override
  public void mouseClicked(MouseEvent arg0) {

  }

  @Override
  public void mouseEntered(MouseEvent arg0) {

  }

  @Override
  public void mouseExited(MouseEvent arg0) {

  }

  @Override
  public void mousePressed(MouseEvent event) {
    if (event.getButton() == 1)
      mouseButtonLeft = true;
    if (event.getButton() == 3)
      mouseButtonRight = true;

    mouseX = event.getX();
    mouseY = event.getY();
    setMouseCursor();
  }

  @Override
  public void mouseReleased(MouseEvent event) {
    if (event.getButton() == 1)
      mouseButtonLeft = false;
    if (event.getButton() == 3)
      mouseButtonRight = false;

    mouseX = event.getX();
    mouseY = event.getY();
    setMouseCursor();

    if (event.getButton() == 3) {
      if (nodeUnderCursor != null) {
        showNodePopupMenu(event, nodeUnderCursor);
      } else {
        showGlobalPopupMenu(event);
      }
    }
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent event) {
    double scaleSpeed = 0.1;
    scaleView(scaleSpeed * event.getPreciseWheelRotation());
    repaint();
  }
}
