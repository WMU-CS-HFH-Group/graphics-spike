import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class Diagram extends Component {
	// Transformation variables
	private double scale;
	private int translationX, translationY;
	private Point panOrigin;
	private Point lastTranslation;
	
	// Transformation limits
	private double minScale = 0.1;
	private double maxScale = 3.0;
	
	// Grid sizes
	private List<Dimension> grids;
	
	// Preset grid sizes
	public static final Dimension YD_GRID = new Dimension(36.0f);
	public static final Dimension FT_GRID = new Dimension(12.0f);
	public static final Dimension SIX_IN_GRID = new Dimension(6.0f);
	public static final Dimension IN_GRID = new Dimension(1.0f);
	
	// Page Specifications
	private Dimension maxWidth, maxHeight;
	
	// Common dimensions
	private Dimension rampWidth = new Dimension(40.0f);
	private Dimension postSize = new Dimension(4.0f);
	
	public Diagram() {
		// Transformation
		this.scale = 1.0;
		this.translationX = 0;
		this.translationY = 0;
		this.panOrigin = new Point(0, 0);
		this.lastTranslation = new Point(0, 0);
		
		// Grids
		this.grids = new ArrayList<Dimension>();
		
		// Default grid
		this.addGrid(Diagram.FT_GRID);
		
		// Page Specifications
		this.maxWidth = new Dimension(50, 0, 0);
		this.maxHeight = new Dimension(50, 0, 0);
		
		// Event for zooming
		this.addMouseWheelListener(new MouseAdapter() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				double delta = 0.05 * e.getPreciseWheelRotation();
				if (scale + delta <= maxScale && scale + delta >= minScale) {
					scale += delta;
					revalidate();
					repaint();
				}
			}
		});
		
		// Events for panning
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				panOrigin.setLocation(e.getX(), e.getY());
				lastTranslation.setLocation(translationX, translationY);
			}
		});
		
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				translationX = (int) lastTranslation.getX() + e.getX() - (int) panOrigin.getX();
				translationY = (int) lastTranslation.getY() + e.getY() - (int) panOrigin.getY();
				revalidate();
				repaint();
			}
		});
	}
	
	public void resetTranslation() {
		this.translationX = 0;
		this.translationY = 0;
		revalidate();
		repaint();
	}
	
	public void resetScale() {
		this.scale = 1.0;
		revalidate();
		repaint();
	}
	
	public void addGrid(Dimension grid) {
		this.grids.add(grid);
	}
	
	public void removeGrid(Dimension grid) {
		this.grids.remove(grid);
	}
	
	public void drawPost(Graphics2D g, Dimension x, Dimension y) {
		g.fillRect(x.toEighths(), y.toEighths(), this.postSize.toEighths(), this.postSize.toEighths());
	}
	
	// Vertical = 0
	// Horizontal = 1
	public void drawRampSection(Graphics2D g, Dimension x, Dimension y, Dimension length, int orientation) {
		// Flip'em if the orientation is horizontal.
		if (orientation == 0) {
			//g.drawRect(x, y, length, this.rampWidth);
			g.drawRect(x.toEighths(), y.toEighths(), this.rampWidth.toEighths(), length.toEighths());
		} else {
			//g.drawRect(x, y, this.rampWidth, length);
			g.drawRect(x.toEighths(), y.toEighths(), length.toEighths(), this.rampWidth.toEighths());
		}
	}
	
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		
		// Make transformations
		g.getTransform().setToIdentity();
		g.translate(this.translationX, this.translationY);
		g.scale(this.scale, this.scale);
		
		// Draw grids
		for (Dimension gridSize : this.grids) {
			int verticals = (int) Math.floor(this.maxWidth.divideBy(gridSize)); 
			int horizontals = (int) Math.floor(this.maxHeight.divideBy(gridSize));
			
			g.setColor(Color.LIGHT_GRAY);
			for (int x = 0; x < verticals; x++) {
				int xPos = x * gridSize.toEighths();
				g.drawLine(xPos, 0, xPos, this.maxHeight.toEighths());
			}
			
			for (int y = 0; y < horizontals; y++) {
				int yPos = y * gridSize.toEighths();
				g.drawLine(0, yPos, this.maxWidth.toEighths(), yPos);
			}
		}
		
		g.setColor(Color.BLACK);
		this.drawPost(g, new Dimension(20), new Dimension(20));
		this.drawRampSection(g, new Dimension(24), new Dimension(20), new Dimension(120), 0); // 10ft
	}
}
