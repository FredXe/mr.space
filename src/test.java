import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;

public class test {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(600, 800);
		File file = new File("src/icon.jpg");
		BufferedImage bufferedImage = null;
		BufferedImage bufferedImage1 = null;

		try {
			bufferedImage = ImageIO.read(file);
			bufferedImage1 = ImageIO.read(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		// bufferedImage.
		BufferedImageMaskTest.mask(bufferedImage);

		ImageIcon imageIcon = new ImageIcon(bufferedImage);
		JLabel label = new JLabel(imageIcon);
		ImageIcon imageIcon1 = new ImageIcon(bufferedImage1);
		JLabel label1 = new JLabel(imageIcon1);
		label1.setHorizontalAlignment(JLabel.LEFT);

		label.setBounds(0, 0, 300, 300);

		// label.setBackground(Color.BLUE);
		label.setOpaque(false);
		label1.setOpaque(false);

		// frame.setBackground(Color.BLACK);
		label.setLocation(0, 0);
		frame.add(label);
		frame.add(label1);

	}

}
