package mrSpace;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;

public class Main {

	public static void main(String[] args) {

		JFrame main = new JFrame();

		Game game = new Game();

		main.add(game);
		main.setTitle("Mr.Space");
		main.setSize(600, 800);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);

		// JFrame main = new JFrame();
		// JPanel panel = new JPanel();
		// JFrame frame = new JFrame();
		// main.add(frame);
		// frame.setVisible(true);
		// frame.setSize(600, 800);

		// main.setSize(600, 800);
		// main.setVisible(true);

	}

}
