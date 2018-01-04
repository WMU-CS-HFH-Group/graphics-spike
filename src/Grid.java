import java.awt.Color;

public class Grid {
	private Dimension size;
	private int thickness;
	private Color color;
	private double disappearAtScale;
	
	// Labels on each measurement
	private boolean displayLabels;
	private int labelInterval;
	
	public Grid(Dimension size, int thickness, Color color, double disappearAtScale) {
		this.size = size;
		this.thickness = thickness;
		this.color = color;
		this.disappearAtScale = disappearAtScale;
		
		this.displayLabels = false;
		this.labelInterval = 1;
	}
	
	public Dimension getSize() {
		return size;
	}
	public void setSize(Dimension size) {
		this.size = size;
	}
	public int getThickness() {
		return thickness;
	}
	public void setThickness(int thickness) {
		this.thickness = thickness;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	public double getDisappearAtScale() {
		return disappearAtScale;
	}

	public void setDisappearAtScale(double disappearAtScale) {
		this.disappearAtScale = disappearAtScale;
	}

	public boolean isDisplayLabels() {
		return displayLabels;
	}

	public void setDisplayLabels(boolean displayLabels) {
		this.displayLabels = displayLabels;
	}

	public int getLabelInterval() {
		return labelInterval;
	}

	public void setLabelInterval(int labelInterval) {
		this.labelInterval = labelInterval;
	}
	
	public static Grid createFeetGrid() {
		return new Grid(new Dimension(12.0f), 3, Color.LIGHT_GRAY, 0.25);
	}
	
	public static Grid createInchesGrid() {
		return new Grid(new Dimension(1.0f), 1, Color.LIGHT_GRAY, 1.0);
	}
}