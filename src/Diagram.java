import java.awt.*;
import javax.swing.*;

public class Diagram extends Component {
	// ALL UNITS IN INCHES
	private float width, height; // inches wide and high
	
	public Diagram() {
		this.width = 200;
		this.height = 200;
	}
	
	/**
	 * Calculate an x coordinate/size in pixels, given an inch measurement.
	 * @param coord
	 * @return
	 */
	private int getXCoord(double coord) {
		// factor = how many pixels an inch is
		double factor = this.getWidth() / (double)this.width;
		return (int) (coord * factor);
	}
	
	private int getYCoord(double coord) {
		double factor = this.getHeight() / (double)this.height;
		return (int) (coord * factor);
	}
	
	/**
	 * Draws a post with its top-left corner at the given point. 
	 * @param g
	 */
	private void drawPost(Graphics2D g, Point p) {
		// 4x4 inches
		g.fillRect(this.getXCoord(p.getX()), this.getYCoord(p.getY()), this.getXCoord(4), this.getYCoord(4));
	}
	
	/**
	 * Draws a ramp section.
	 * @param g 
	 * @param p Top-left corner
 	 * @param length Length in inches
 	 * @param orientation Orientation; 0 for vertical, 1 for horizontal.
	 */
	private void drawRampSection(Graphics2D g, Point p, int length, int orientation) {
		int width = getXCoord(40);
		int height = getYCoord(length); 
		int x = getXCoord(p.getX());
		int y = getYCoord(p.getY());
		
		// Flip'em if the orientation is horizontal.
		if (orientation == 1) {
			g.drawRect(getXCoord(p.getX()), getYCoord(p.getY()), height, width);
		} else {
			g.drawRect(getXCoord(p.getX()), getYCoord(p.getY()), width, height);
		}
		
		this.drawPost(g, new Point((int)p.getX() - 4, (int)p.getY()));
		this.drawPost(g, new Point((int)p.getX() + 40, (int)p.getY()));
	}
	
	/**
	 * Draws a turn-around
	 * @param g
	 * @param p top-left corner
	 * @param size width and height in inches
	 */
	private void drawTurnAround(Graphics2D g, Point p, float size) {
		
	}
	
	/**
	 * Draws text
	 * @param g
	 * @param p location
	 */
	private void drawLabel(Graphics2D g) {
		
	}
	
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		
		g.setColor(Color.black);
		this.drawRampSection(g, new Point(24, 20), 12 * 8, 0);
		
	}
}
