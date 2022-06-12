package mrSpace;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame main = new JFrame();

		Game game = new Game();

		main.add(game);
		main.setTitle("¶ô°k!!");
		main.setSize(600, 800);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
	}
}
