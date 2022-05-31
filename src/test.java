import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class test {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("Mr.space");
		frame.setVisible(true);
		// frame.setSize(720, 960);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// URL url = frame.getClass().getResource("icon.jpg");
		ImageIcon smileIcon = new ImageIcon("bin/icon.jpg");
		JLabel label = new JLabel();
		label.setText("Hello, young lady");
		label.setIcon(smileIcon);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.TOP);
		label.setForeground(Color.cyan);
		label.setBackground(Color.black);
		label.setOpaque(true);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);

		frame.add(label);
		frame.pack();
		// frame.setIconImage(smileIcon.getImage());

	}
}
