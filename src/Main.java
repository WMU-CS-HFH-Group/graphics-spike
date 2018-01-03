import java.awt.*;
import javax.swing.*;

public class Main {
	private JFrame frame;
	private Diagram panel;
	
	public Main() {
		frame = new JFrame("Graphics Spike");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new Diagram();
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
