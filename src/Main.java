import java.awt.*;
import javax.swing.*;

public class Main {
	private Frame frame;
	private Diagram panel;
	
	public Main() {
		frame = new Frame("Graphics Spike");
		panel = new Diagram(1.125f);
		panel.setGridlines(true, new Dimension(6));
	}
	
	public void launch() {
		frame.setSize(800, 800);
		frame.setBackground(Color.white);
		frame.setLayout(new BorderLayout());
		frame.add("Center", panel);
		
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.launch();
	}
}
