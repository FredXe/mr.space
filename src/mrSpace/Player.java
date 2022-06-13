package mrSpace;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JInternalFrame;

public class Player {
	public static final int STAND = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int SQUAT1 = 3;
	public static final int SQUAT2 = 4;
	public static final int SQUAT3 = 5;
	public static final int LIE = 6;
	public static final int LIE_SWEAT = 7;
	public static final int AIR = 8;
	public static final int AIR2 = 9;
	public static final int AIR_LAND = 10;
	public static final int JUMP_LEFT = 11;
	public static final int JUMP_LEFT_LAND = 12;
	public static final int JUMP_RIGHT = 13;
	public static final int JUMP_RIGHT_LAND = 14;
	public static final int POSE_AMOUNT = 15;

	private Game game;

	private BufferedImage[] poseBufferedImage;

	private Image currentPose;
	private Point coordinate;
	private int position;

	private static final int MOVING_SPEED = 10;
	private static final int MOVING_TIME = 8;

	private MovingKeyListener movingKeyListener = new MovingKeyListener();
	private LeftAnimationListener leftAnimationListener = new LeftAnimationListener();
	private Timer leftAnimationTimer = new Timer(MOVING_TIME, leftAnimationListener);

	private RightAnimationListener rightAnimationListener = new RightAnimationListener();
	private Timer rightAnimationTimer = new Timer(MOVING_TIME, rightAnimationListener);

	Player(Game gameInput) {
		poseBufferedImage = new BufferedImage[Player.POSE_AMOUNT];

		try {
			poseBufferedImage[Player.STAND] = ImageIO.read(new File("src/playerImage/stand.png"));
			poseBufferedImage[Player.LEFT] = ImageIO.read(new File("src/playerImage/left.png"));
			poseBufferedImage[Player.RIGHT] = ImageIO.read(new File("src/playerImage/right.png"));
			poseBufferedImage[Player.SQUAT1] = ImageIO.read(new File("src/playerImage/squat1.png"));
			poseBufferedImage[Player.SQUAT2] = ImageIO.read(new File("src/playerImage/squat2.png"));
			poseBufferedImage[Player.SQUAT3] = ImageIO.read(new File("src/playerImage/squat3.png"));
			poseBufferedImage[Player.LIE] = ImageIO.read(new File("src/playerImage/lie.png"));
			poseBufferedImage[Player.LIE_SWEAT] = ImageIO.read(new File("src/playerImage/lie_sweat.png"));
			poseBufferedImage[Player.AIR] = ImageIO.read(new File("src/playerImage/air.png"));
			poseBufferedImage[Player.AIR2] = ImageIO.read(new File("src/playerImage/air2.png"));
			poseBufferedImage[Player.AIR_LAND] = ImageIO.read(new File("src/playerImage/air_land.png"));
			poseBufferedImage[Player.JUMP_LEFT] = ImageIO.read(new File("src/playerImage/jump_left.png"));
			poseBufferedImage[Player.JUMP_LEFT_LAND] = ImageIO.read(new File("src/playerImage/jump_left_land.png"));
			poseBufferedImage[Player.JUMP_RIGHT] = ImageIO.read(new File("src/playerImage/jump_right.png"));
			poseBufferedImage[Player.JUMP_RIGHT_LAND] = ImageIO.read(new File("src/playerImage/jump_right_land.png"));

		} catch (Exception e) {
			// TODO: handle exception
		}

		game = gameInput;
		game.addKeyListener(movingKeyListener);
		game.setFocusable(true);

		currentPose = poseBufferedImage[Player.STAND];
		coordinate = new Point(500, 300);
		position = coordinate.x / 50;

		// pose = new BufferedImage[1];
		// pose[Player.STAND] = new ImageIcon("src/playerImage/stand.png").getImage();
		// pose[Player.STAND]
	}

	public Image getCurrentPose() {
		return currentPose;
	}

	public Point getCoordinate() {
		return coordinate;
	}

	private class MovingKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			System.out.print(e.getKeyChar());
			// System.out.println(e.getKeyCode());
			if (e.getKeyCode() == KeyEvent.VK_A && position > 0 && !leftAnimationTimer.isRunning()) {
				position--;
				leftAnimationTimer.restart();
				currentPose = poseBufferedImage[Player.LEFT];
			} else if (e.getKeyCode() == KeyEvent.VK_D && position < 11 && !rightAnimationTimer.isRunning()) {
				position++;
				rightAnimationTimer.restart();
				currentPose = poseBufferedImage[Player.RIGHT];
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// System.out.println(":D");

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// System.out.println(":D");

		}
	}

	private class LeftAnimationListener implements ActionListener {
		private int displacement;

		@Override
		public void actionPerformed(ActionEvent e) {

			coordinate.x = coordinate.x - MOVING_SPEED;
			displacement = displacement + MOVING_SPEED;
			if (displacement >= 50) {
				leftAnimationTimer.stop();
				displacement = 0;
				currentPose = poseBufferedImage[Player.STAND];
			}
			game.repaint();
		}
	}

	private class RightAnimationListener implements ActionListener {
		private int displacement;

		@Override
		public void actionPerformed(ActionEvent e) {
			coordinate.x = coordinate.x + MOVING_SPEED;
			displacement = displacement + MOVING_SPEED;
			if (displacement >= 50) {
				rightAnimationTimer.stop();
				displacement = 0;
				currentPose = poseBufferedImage[Player.STAND];
			}
			game.repaint();
		}
	}
}
