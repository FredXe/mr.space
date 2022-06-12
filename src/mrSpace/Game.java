package mrSpace;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Game extends JFrame {

	BarrierOperation br = new BarrierOperation();
	Image background = new ImageIcon("src/background_color.png").getImage();

	BarrierActionListener barrierActionListener = new BarrierActionListener();
	Timer summonBarrier = new Timer(1000, barrierActionListener);

	KeyListenerTest keyListenerTest = new KeyListenerTest();

	Game() {
		// summonBarrier.start();
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
		g2D.drawImage(br.getTopBarrierImage(), 0, 0, null);
		g2D.drawImage(br.getButtomBarrierImage(), 0, 0, null);
	}

	private class BarrierActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			br.randomBarrier();
			repaint();
		}
	}

	private class KeyListenerTest implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getKeyChar() == ' ') {
				br.randomBarrier();
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
