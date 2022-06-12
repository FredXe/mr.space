package mrSpace;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Game extends JInternalFrame {

	BarrierOperation br = new BarrierOperation(this);
	Image background = new ImageIcon("src/background_color.png").getImage();

	BarrierActionListener barrierActionListener = new BarrierActionListener();
	Timer summonBarrier = new Timer(2000, barrierActionListener);
	
	KeyListenerTest keyListenerTest = new KeyListenerTest();

	Game() {
<<<<<<< HEAD

=======
>>>>>>> 6281876c140219974902a689fbb44713d06b9eca
		summonBarrier.start();
		this.addKeyListener(keyListenerTest);
		this.setTitle("Mr.Space");
		this.setSize(600, 800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(background, 0, 0, null);
<<<<<<< HEAD
		// g2D.setColor(Color.yellow);
		// g2D.fillRect(0, 0, 600, 800);
=======
>>>>>>> 6281876c140219974902a689fbb44713d06b9eca
		g2D.drawImage(br.getTopBarrierImage(), 0, br.getTopBarrierY(), null);
		g2D.drawImage(br.getButtomBarrierImage(), 0, br.getBottomBarrierY(), null);
		update(getGraphics());
	}

	public void update(Graphics g) {

	}

	private class BarrierActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			br.randomBarrier();
			br.barrierAnimation();
			repaint();
		}
	}

	private class KeyListenerTest implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getKeyChar() == ' ') {

				br.randomBarrier();
				br.barrierAnimation();
				repaint();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	}

}
