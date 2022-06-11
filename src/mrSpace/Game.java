package mrSpace;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class Game extends JFrame{
	
	BarrierOperation br = new BarrierOperation();
	Image background = new ImageIcon("src/backcolor.png").getImage();
	Game()
	{
		this.setTitle("Mr.Space");
		this.setSize(600,800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(background,0,0,null);
		g2D.drawImage(br.getter(),0,0,null);
	}
}
