import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class Panel extends DrawingPanel {
	
	protected int mouseX, mouseY;
	protected boolean mouseDown, shiftDown;
	protected Graphics g;
	protected Set<Integer> keysDown;
	
	/**
	 * Makes the graphical board
	 * @param width - Width of grid
	 * @param height - height of grid
	 */
	public Panel(int width, int height) {
		super(width, height);
		g = this.getGraphics();
		keysDown = new HashSet<Integer>();
	}
	


	public void mouseMoved(MouseEvent arg0) {
		mouseX = arg0.getX();
		mouseY = arg0.getY();	
	}
	
	public void keyPressed(KeyEvent arg0) {
		keysDown.add(arg0.getKeyCode());
		if(keysDown.contains(KeyEvent.VK_SPACE)) {
			action();
		}
	}
	
	public void keyReleased(KeyEvent arg0) {
		keysDown.remove(arg0.getKeyCode());
	}
	
	public void mousePressed(MouseEvent arg0) {
		mouseDown = true;
		if(mouseDown) {
			action();
		}
	}
	
	public void mouseReleased(MouseEvent arg0) {
		mouseDown = false;
	}
	
	
	public boolean mouseDown() {return mouseDown;}
	public int getMouseX() {return mouseX;}
	public int getMouseY() {return mouseY;}
	public boolean isKeyDown(int key) {return keysDown.contains(key);}
	protected void action(){};
	
}
