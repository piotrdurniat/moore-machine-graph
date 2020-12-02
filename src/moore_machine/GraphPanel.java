package moore_machine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener, MouseWheelListener {

  private static final long serialVersionUID = 1L;
  private int translateX = 0;
  private int translateY = 0;
  private double scale = 1;
  private int mouseX = 0;
  private int mouseY = 0;
  private boolean mouseButtonLeft = false;
  private boolean mouseButtonRight = false;

  GraphPanel() {
    addMouseListener(this);
    addMouseMotionListener(this);
    addMouseWheelListener(this);
    addKeyListener(this);
    setFocusable(true);
    requestFocus();
  }

  protected MooreGraph graph;

  public MooreGraph getGraph() {
    return graph;
  }

  public void setGraph(MooreGraph graph) {
    this.graph = graph;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (graph == null)
      return;

    Graphics2D g2d = (Graphics2D) g;

    // Translate to the center in order to scale wiew from the middle
    g.translate(this.getWidth() / 2, this.getHeight() / 2);
    g2d.scale(scale, scale);
    g.translate(-this.getWidth() / 2, -this.getHeight() / 2);

    g.translate(translateX, translateY);
    graph.draw(g);


  }

  private void translateView(int x, int y) {
    translateX += x;
    translateY += y;
  }

  private void scaleView(double scaleChange) {
    scale += scaleChange;
  }

  @Override
  public void keyPressed(KeyEvent event) {

    int transSpeed;
    double scaleSpeed = 0.05;

    if (event.isShiftDown()) {
      transSpeed = 10;
    } else {
      transSpeed = 1;
    }
    System.out.println(event.getKeyCode());
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

    }

    repaint();
  }

  @Override
  public void keyReleased(KeyEvent arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void keyTyped(KeyEvent arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseDragged(MouseEvent event) {
    if (mouseButtonLeft) {
      int diffX = event.getX() - mouseX;
      int diffY = event.getY() - mouseY;
      translateView(diffX, diffY);
    }
    mouseX = event.getX();
    mouseY = event.getY();

    repaint();
  }

  @Override
  public void mouseMoved(MouseEvent arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseClicked(MouseEvent arg0) {
    // TODO Auto-generated method stub

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
  public void mousePressed(MouseEvent event) {
    if (event.getButton() == 1)
      mouseButtonLeft = true;
    if (event.getButton() == 3)
      mouseButtonRight = true;

      mouseX = event.getX();
      mouseY = event.getY();
  }

  @Override
  public void mouseReleased(MouseEvent event) {
    if (event.getButton() == 1)
      mouseButtonLeft = false;
    if (event.getButton() == 3)
      mouseButtonRight = false;

      mouseX = event.getX();
      mouseY = event.getY();
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent event) {
    double scaleSpeed = 0.1;
    scaleView(scaleSpeed * event.getPreciseWheelRotation());
    repaint();
  }
}
