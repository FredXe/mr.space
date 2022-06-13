package mrSpace;

import javax.swing.Timer;
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
	Timer kickStartTimer = new Timer(2000, barrierActionListener);

	Player player = new Player(this, br);

	KeyListenerTest keyListenerTest = new KeyListenerTest();

	Game() {
		kickStartTimer.start();
		this.addKeyListener(keyListenerTest);
		this.setSize(600, 800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;

		g2D.drawImage(background, 0, 0, null);
		g2D.drawImage(player.getCurrentPose(), player.getCoordinate().x,
				player.getCoordinate().y - Player.PLAYER_IMAGE_HEIGHT, null);
		g2D.drawImage(br.getTopBarrierImage(), 0, br.getTopBarrierY(), null);
		g2D.drawImage(br.getBottomBarrierImage(), 0, br.getBottomBarrierY(), null);
		update(getGraphics());
	}

	public void update(Graphics g) {

	}

	private class BarrierActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			br.barrierAnimation();
			kickStartTimer.stop();
			// repaint();
		}
	}

	private class KeyListenerTest implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			// System.out.println(":D");
			// if (e.getKeyChar() == ' ') {

			// br.randomBarrier();
			// br.barrierAnimation();
			// repaint();
			// }
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
