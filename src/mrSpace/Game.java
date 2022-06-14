package mrSpace;

import java.util.Arrays;
import java.io.File;
import javax.swing.Timer;
import javax.swing.JInternalFrame;
import javax.swing.ImageIcon;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Game extends JInternalFrame {

	BarrierOperation br = new BarrierOperation(this);
	Image background = new ImageIcon("src/background_color.png").getImage();
	Image endgameBackground = null;
	private int endgameBackgroundY = -800;

	private Clip gameMusic;

	BarrierActionListener barrierActionListener = new BarrierActionListener();
	Timer kickStartTimer = new Timer(500, barrierActionListener);

	private Player player = new Player(this, br);

	private int score = 0;
	private int[] fileScore = { 0, 0, 0, 0 };

	KeyListenerTest keyListenerTest = new KeyListenerTest();

	private DeadHoldListener deadHoldListener = new DeadHoldListener();
	private Timer deadHoldTimer = new Timer(500, deadHoldListener);

	private EndgameAnimationListener endgameAnimationListener = new EndgameAnimationListener();
	private Timer endgameAnimationTimer = new Timer(10, endgameAnimationListener);
	private static final int BACKGROUND_FALLING_SPEED = 20;

	private EndgameRisingListener endgameRisingListener = new EndgameRisingListener();
	private Timer endgameRisingTimer = new Timer(10, endgameRisingListener);

	private int X_RENDER_OFFSET = -5;
	private int Y_RENDER_OFFSET = 10;

	Game() {
		setScore(0);
		setSound();

		kickStartTimer.restart();
		this.addKeyListener(keyListenerTest);
		this.setSize(600, 800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void setSound() {
		try {
			File gameMusicFile = new File("src/sounds/game.wav");
			AudioInputStream gameAudioStream = AudioSystem.getAudioInputStream(gameMusicFile);
			gameMusic = AudioSystem.getClip();
			gameMusic.open(gameAudioStream);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;

		g2D.drawImage(background, 0, 0, null);
		g2D.drawImage(player.getCurrentPose(), player.getCoordinate().x + X_RENDER_OFFSET,
				player.getCoordinate().y - Player.PLAYER_IMAGE_HEIGHT + Y_RENDER_OFFSET, null);
		g2D.drawImage(br.getTopBarrierImage(), 0, br.getTopBarrierY(), null);
		g2D.drawImage(br.getBottomBarrierImage(), 0, br.getBottomBarrierY(), null);
		g2D.drawImage(endgameBackground, 0, endgameBackgroundY, null);
		update(getGraphics());
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public void update(Graphics g) {

	}

	private class BarrierActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameMusic.start();
			score = 0;
			endgameBackground = null;
			br.barrierAnimation();
			kickStartTimer.stop();
			// repaint();
		}
	}

	public void gameEnd() {
		gameMusic.stop();
		gameMusic.setFramePosition(0);
		deadHoldTimer.restart();
	}

	public void endgameScore() {
		BufferedImage bf = Tool.toBufferedImage(endgameBackground);
		Graphics2D g2D = (Graphics2D) bf.getGraphics();
		g2D.setColor(new Color(0xCCA854));
		g2D.setFont(new Font("特太行書", Font.BOLD, 50));
		g2D.drawString(String.valueOf(score), 380 - (String.valueOf(score).length() * 25), 170);
		g2D.setColor(Color.BLACK);
		g2D.setFont(new Font("特太行書", Font.BOLD, 45));

		fileScore[3] = score;
		Arrays.sort(fileScore);
		g2D.drawString(String.valueOf(fileScore[3]), 410 - (String.valueOf(fileScore[3]).length() * 25), 488);
		g2D.drawString(String.valueOf(fileScore[2]), 410 - (String.valueOf(fileScore[2]).length() * 25), 563);
		g2D.drawString(String.valueOf(fileScore[1]), 410 - (String.valueOf(fileScore[1]).length() * 25), 643);
		int offset = -2;
		g2D.setColor(Color.WHITE);
		g2D.drawString(String.valueOf(fileScore[3]), 410 - (String.valueOf(fileScore[3]).length() * 25) + offset,
				488 + offset);
		g2D.drawString(String.valueOf(fileScore[2]), 410 - (String.valueOf(fileScore[2]).length() * 25) + offset,
				563 + offset);
		g2D.drawString(String.valueOf(fileScore[1]), 410 - (String.valueOf(fileScore[1]).length() * 25) + offset,
				643 + offset);
		endgameBackground = bf;
	}

	private class KeyListenerTest implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				endgameRisingTimer.restart();
				player.gameStart();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}

	private class DeadHoldListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			deadHoldTimer.stop();
			endgameBackgroundY = -800;
			endgameBackground = new ImageIcon("src/endgame_background.png").getImage();
			endgameScore();
			endgameAnimationTimer.restart();
		}
	}

	private class EndgameAnimationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			endgameBackgroundY += BACKGROUND_FALLING_SPEED;
			repaint();
			if (endgameBackgroundY == 0) {
				endgameAnimationTimer.stop();
				br.setTopBarrierY(-800);
				br.setBottomBarrierY(250);
			}
		}
	}

	private class EndgameRisingListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			endgameBackgroundY -= BACKGROUND_FALLING_SPEED;
			repaint();
			if (endgameBackgroundY == -800) {
				endgameRisingTimer.stop();
				kickStartTimer.restart();
			}
		}
	}
}
