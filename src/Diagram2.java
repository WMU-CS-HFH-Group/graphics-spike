import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

@Deprecated
public class Diagram2 extends Component {
	// The user space will be scaled and translated.
	private AffineTransform transform;
	
	// Dimensions that do not normally change
	private Dimension rampWidth = new Dimension(40);
	private Dimension postSize = new Dimension(4);
	
	// Gridlines
	private boolean gridlines;
	private Dimension gridSize;
	
	private Dimension maxWidth, maxHeight;
	
	private Point2D panOrigin;
	
	// Scale = eights of inch to pixel
	// Each unit used in drawing will be an eighth of an inch. This can be scaled up.
	public Diagram2(float scale) {
		this.transform = new AffineTransform();
		this.transform.setToScale(scale, scale);
		this.panOrigin = new Point2D.Double(0, 0);
		
		// Grid size
		this.gridSize = new Dimension(6); // 6"
		this.maxWidth = new Dimension(30, 0, 0); // 30'
		this.maxHeight = new Dimension(30, 0, 0);
		
		// Add mouse listener
		this.addMouseWheelListener(new MouseAdapter() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				double delta = 0.125d * e.getPreciseWheelRotation();
				setScale(transform.getScaleX() + delta);
				revalidate();
				repaint();
			}
		});
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
//				panOrigin.setLocation(e.getX(), e.getY());
//				System.out.println(panOrigin);
			}
		});
		
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				//AffineTransform inverted = (AffineTransform) transform.clone();
				
				try {
					//inverted.invert();
					//Point2D point = inverted.transform(new Point2D.Double(e.getX(), e.getY()), null);
					//inverted.setToTranslation((double)e.getX(), (double)e.getY());
					Point2D p = transform.inverseTransform(new Point2D.Double(e.getX() - panOrigin.getX(), e.getY() - panOrigin.getY()), null);
					transform.translate(p.getX(), p.getY());
					revalidate();
					repaint();
					panOrigin.setLocation(e.getX(), e.getY());
					System.out.printf("%f, %f\n", p.getX(), p.getY());
				} catch (NoninvertibleTransformException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	public void setGridSize(Dimension gridSize) {
		this.gridSize = gridSize;
		revalidate();
		repaint();
	}
	
	public void setGridlines(boolean gridlines, Dimension gridSize) {
		this.gridlines = gridlines;
		this.setGridSize(gridSize);
	}
	
	public Dimension getGridSize() {
		return this.gridSize;
	}
	
	public boolean hasGridlines() {
		return this.gridlines;
	}
	
	// Sets the scale of the diagram and truncates it to the scale unit.
	public void setScale(double scale) {
		double tx = this.transform.getTranslateX();
		double ty = this.transform.getTranslateY();
		this.transform.setToScale(scale, scale);
		this.transform.translate(tx, ty);
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
	
	public void drawTurnaround() {
		
	}
	
	public void drawLabel() {
		
	}
	
	public void drawGridlines(Graphics2D g, Color color, Dimension gridSize) {
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
	
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setTransform(this.transform);
		
		if (this.gridlines) {
			this.drawGridlines(g, Color.LIGHT_GRAY, this.gridSize);
		}
		
		g.setColor(Color.BLACK);
		this.drawPost(g, new Dimension(20), new Dimension(20));
		this.drawRampSection(g, new Dimension(24), new Dimension(20), new Dimension(120), 0); // 10ft
	}
}
