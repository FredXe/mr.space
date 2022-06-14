package mrSpace;

import java.util.Random;

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

public class Player {
	public static final int STAND = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int SQUAT1 = 3;
	public static final int SQUAT2 = 4;
	public static final int SQUAT3 = 5;
	public static final int JUMP_LEFT = 6;
	public static final int JUMP_LEFT_LAND = 7;
	public static final int JUMP_RIGHT = 8;
	public static final int JUMP_RIGHT_LAND = 9;
	public static final int LIE = 10;
	public static final int LIE_SWEAT = 11;
	public static final int AIR = 12;
	public static final int AIR2 = 13;
	public static final int AIR_LAND = 14;
	public static final int POSE_AMOUNT = 15;

	public static final int PLAYER_IMAGE_HEIGHT = 60;

	private Game game;
	private BarrierOperation br;

	private BufferedImage[] poseBufferedImage;

	private Image currentPose;
	private Point coordinate;
	private Point targetCoordinate;
	private int position;
	private boolean movable = false;

	private static final int MOVING_SPEED = 25;
	private static final int MOVING_TIME = 3;

	private MovingKeyListener movingKeyListener = new MovingKeyListener();

	private ChangePoseListener changePoseListener = new ChangePoseListener();
	private Timer changePoseTimer = new Timer(80, changePoseListener);

	private AirPoseListener airPoseListener = new AirPoseListener();
	private Timer airPoseTimer = new Timer(60, airPoseListener);

	private PlayerVerticalAnimationListener playerVerticalAnimationListener = new PlayerVerticalAnimationListener();
	private Timer playerVerticalAnimationTimer = new Timer(1, playerVerticalAnimationListener);

	private LeftAnimationListener leftAnimationListener = new LeftAnimationListener();
	private Timer leftAnimationTimer = new Timer(MOVING_TIME, leftAnimationListener);

	private RightAnimationListener rightAnimationListener = new RightAnimationListener();
	private Timer rightAnimationTimer = new Timer(MOVING_TIME, rightAnimationListener);

	private RisingAnimationListener risingAnimationListener = new RisingAnimationListener();
	private Timer risingAnimationTimer = new Timer(30, risingAnimationListener);

	private InitialAnimationListener initialAnimationListener = new InitialAnimationListener();
	private Timer initialAnimationTimer = new Timer(30, initialAnimationListener);

	private FallingAnimationListener fallingAnimationListener = new FallingAnimationListener();
	private Timer fallingAnimationTimer = new Timer(30, fallingAnimationListener);

	Player(Game gameInput, BarrierOperation brInput) {
		poseBufferedImage = new BufferedImage[Player.POSE_AMOUNT];

		try {
			// poseBufferedImage[Player.STAND] = ImageIO.read(new
			// File("src/playerImage/stickman.png"));
			poseBufferedImage[Player.STAND] = ImageIO.read(new File("src/playerImage/stand.png"));
			poseBufferedImage[Player.LEFT] = ImageIO.read(new File("src/playerImage/left.png"));
			poseBufferedImage[Player.RIGHT] = ImageIO.read(new File("src/playerImage/right.png"));
			poseBufferedImage[Player.SQUAT1] = ImageIO.read(new File("src/playerImage/squat1.png"));
			poseBufferedImage[Player.SQUAT2] = ImageIO.read(new File("src/playerImage/squat2.png"));
			poseBufferedImage[Player.SQUAT3] = ImageIO.read(new File("src/playerImage/squat3.png"));
			poseBufferedImage[Player.JUMP_LEFT] = ImageIO.read(new File("src/playerImage/jump_left.png"));
			poseBufferedImage[Player.JUMP_LEFT_LAND] = ImageIO.read(new File("src/playerImage/jump_left_land.png"));
			poseBufferedImage[Player.JUMP_RIGHT] = ImageIO.read(new File("src/playerImage/jump_right.png"));
			poseBufferedImage[Player.JUMP_RIGHT_LAND] = ImageIO.read(new File("src/playerImage/jump_right_land.png"));
			poseBufferedImage[Player.LIE] = ImageIO.read(new File("src/playerImage/lie.png"));
			poseBufferedImage[Player.LIE_SWEAT] = ImageIO.read(new File("src/playerImage/lie_sweat.png"));
			poseBufferedImage[Player.AIR] = ImageIO.read(new File("src/playerImage/air.png"));
			poseBufferedImage[Player.AIR2] = ImageIO.read(new File("src/playerImage/air2.png"));
			poseBufferedImage[Player.AIR_LAND] = ImageIO.read(new File("src/playerImage/air_land.png"));

		} catch (Exception e) {
		}

		// poseBufferedImage[Player.STAND] = Tool.scale(poseBufferedImage[Player.STAND],
		// 120, 120, 2, 2);

		game = gameInput;
		game.addKeyListener(movingKeyListener);
		game.setFocusable(true);

		br = brInput;
		br.setPlayer(this);

		airPoseTimer.restart();
		coordinate = new Point(300, 450);
		targetCoordinate = new Point();
		position = coordinate.x / 50;
		targetCoordinate.setLocation(coordinate);

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

	public Timer getAirPoseTimer() {
		return airPoseTimer;
	}

	public Timer getRisingAnimationTimer() {
		return risingAnimationTimer;
	}

	public Timer getFallingAnimationTimer() {
		return fallingAnimationTimer;
	}

	public Timer getInitialAnimationTimer() {
		return initialAnimationTimer;
	}

	public void dead() {
		movable = false;
		changePoseTimer.stop();
		game.gameEnd();
	}

	public void gameStart() {
		airPoseTimer.restart();
		coordinate.setLocation(300, 450);
		position = 6;
	}

	public int getPosition() {
		return position;
	}

	public void setMovable(boolean input) {
		movable = input;
		if (movable) {
			changePoseTimer.restart();
			airPoseTimer.stop();
		} else {
			changePoseTimer.stop();
		}
	}

	public void setPose(int input) {
		currentPose = poseBufferedImage[input];
	}

	public void setPosition(int input) {
		position = input;
	}

	public void setRisingAnimaListener(boolean input) {
		risingAnimationListener.setFirstTime(input);
	}

	public void setInitialAnimationListener(boolean input) {
		initialAnimationListener.setFirstTime(input);
	}

	public void setFallingAnimationListener(boolean input) {
		fallingAnimationListener.setFirstTime(input);
	}

	private class MovingKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.print(" " + position + " " + movable);
			// System.out.println(e.getKeyCode());
			if (movable) {
				// movable = false;
				if ((e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) && position > 0) {
					position--;
					boolean isYGoingUp = br.getOffset()[position + 1] > br.getOffset()[position];
					leftAnimationListener.count += 2;
					if (isYGoingUp) {
						System.out.println(":(");
						playerVerticalAnimationListener.setFirstTime(true);
						playerVerticalAnimationListener.setIsRight(PlayerVerticalAnimationListener.LEFT);
						playerVerticalAnimationTimer.restart();
					} else {
						leftAnimationListener.setFirstTime(true);
						leftAnimationTimer.restart();
					}
				} else if ((e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) && position < 11) {
					position++;
					boolean isYGoingUp = br.getOffset()[position - 1] > br.getOffset()[position];
					rightAnimationListener.count += 2;
					if (isYGoingUp) {
						playerVerticalAnimationListener.setFirstTime(true);
						playerVerticalAnimationListener.setIsRight(PlayerVerticalAnimationListener.RIGHT);
						playerVerticalAnimationTimer.restart();
					} else {
						rightAnimationListener.setFirstTime(true);
						rightAnimationTimer.restart();
					}
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// System.out.println(":D");
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				System.out.println(" right released and now coordinate : " + coordinate.x);
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				System.out.println(" left released and now coordinate : " + coordinate.x);
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// System.out.println(":D");

		}
	}

	private class ChangePoseListener implements ActionListener {
		private Random random = new Random();

		@Override
		public void actionPerformed(ActionEvent e) {
			setPose(random.nextInt(0, 6));
			game.repaint();
		}
	}

	private class AirPoseListener implements ActionListener {
		private int airPose = 12;

		@Override
		public void actionPerformed(ActionEvent e) {
			airPose = (airPose == 12) ? 13 : 12;
			setPose(airPose);
			game.repaint();
		}
	}

	private class PlayerVerticalAnimationListener implements ActionListener {
		public static final int RIGHT = 1;
		public static final int LEFT = -1;
		private boolean firstTime = false;
		private int movingSpeed = 0;
		private int isRight = 1;
		private int count = 0;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (firstTime) {
				movingSpeed = (br.getOffset()[position - isRight] - br.getOffset()[position]) / 2;
				coordinate.y = br.getBaseLine() + br.getOffset()[position - isRight];
				coordinate.y -= (br.getOffset()[position - isRight] - br.getOffset()[position]) % 2;
				count += 2;
				targetCoordinate.y = br.getBaseLine() + br.getOffset()[position];
				firstTime = false;
			}
			// System.out.println("playerVerticalAnimation " + coordinate.y + " " +
			// targetCoordinate.y);
			coordinate.y = coordinate.y - movingSpeed;
			game.repaint();
			count--;
			if (coordinate.y == targetCoordinate.y) {
				movingSpeed = 0;
				playerVerticalAnimationTimer.stop();
				if (isRight == 1) {
					if (br.getOffset()[position - 1] > br.getOffset()[position]) {
						rightAnimationListener.setFirstTime(true);
						rightAnimationTimer.restart();
					}
				} else {
					if (br.getOffset()[position + 1] > br.getOffset()[position]) {
						leftAnimationListener.setFirstTime(true);
						leftAnimationTimer.restart();
					}
				}
			}
		}

		public void setIsRight(int input) {
			isRight = input;
		}

		public void setFirstTime(boolean input) {
			firstTime = input;
		}
	}

	private class LeftAnimationListener implements ActionListener {
		private boolean firstTime = false;
		public int count = 0;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (firstTime) {
				targetCoordinate.x = position * 50;
				// count += 5;
				firstTime = false;
			}
			System.out.println(" Left " + count);
			coordinate.x = coordinate.x - MOVING_SPEED;
			// displacement = displacement + MOVING_SPEED;
			count--;
			if (count == 0) {
				// coordinate.x = position * 50;
				// count = 0;

				leftAnimationTimer.stop();
				// currentPose = poseBufferedImage[Player.STAND];
				if (br.getOffset()[position + 1] <= br.getOffset()[position]) {
					playerVerticalAnimationListener.setFirstTime(true);
					playerVerticalAnimationListener.setIsRight(PlayerVerticalAnimationListener.LEFT);
					playerVerticalAnimationTimer.restart();
				}
			}
			game.repaint();
		}

		public void setFirstTime(boolean input) {
			firstTime = input;
		}
	}

	private class RightAnimationListener implements ActionListener {
		private boolean firstTime = false;
		public int count = 0;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (firstTime) {
				// coordinate.x = (position - 1) * 50;
				targetCoordinate.x = position * 50;

				// count += 5;

				firstTime = false;
			}

			System.out.println(" Right " + count);
			coordinate.x = coordinate.x + MOVING_SPEED;
			// displacement = displacement + MOVING_SPEED;
			count--;
			if (count == 0) {
				// coordinate.x = position * 50;
				// count = 0;

				rightAnimationTimer.stop();
				// currentPose = poseBufferedImage[Player.STAND];
				if (br.getOffset()[position - 1] <= br.getOffset()[position]) {
					playerVerticalAnimationListener.setFirstTime(true);
					playerVerticalAnimationListener.setIsRight(PlayerVerticalAnimationListener.RIGHT);
					playerVerticalAnimationTimer.restart();
				}
			}
			game.repaint();
		}

		public void setFirstTime(boolean input) {
			firstTime = input;
		}
	}

	private class RisingAnimationListener implements ActionListener {
		private boolean firstTime = false;
		private static final int DEFAULT_RISING_SPEED = 81;
		private int risingSpeed = DEFAULT_RISING_SPEED;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (firstTime) {
				// System.out.println("risingAnimation " + risingSpeed);
				airPoseTimer.restart();
				risingSpeed = 450 - coordinate.y;
				risingSpeed -= risingSpeed / 3;
				firstTime = false;
			}
			coordinate.y = coordinate.y + risingSpeed;
			risingSpeed -= risingSpeed / 3;
			game.repaint();
			if (coordinate.y <= 450) {
				risingAnimationTimer.stop();
				initialAnimationListener.setFirstTime(true);
				initialAnimationTimer.restart();
			}
		}

		public void setFirstTime(boolean input) {
			firstTime = input;
		}
	}

	private class InitialAnimationListener implements ActionListener {
		private boolean firstTime = false;
		private int initialSpeed = 0;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (firstTime) {
				// System.out.println("InitialAnimation");
				coordinate.x = position * 50;
				initialSpeed = (300 - coordinate.x) / 10;
				firstTime = false;
			}
			coordinate.x = coordinate.x + initialSpeed;
			game.repaint();
			// System.out.println(coordinate.x);
			if (coordinate.x == 300) {
				position = 6;
				targetCoordinate.x = position * 50;
				System.out.println(":D");
				initialAnimationTimer.stop();
				fallingAnimationListener.setFirstTime(true);
				fallingAnimationTimer.restart();
			}
		}

		public void setFirstTime(boolean input) {
			firstTime = input;
		}
	}

	private class FallingAnimationListener implements ActionListener {
		private boolean firstTime = false;
		private int fallingSpeed = 0;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (firstTime) {
				fallingSpeed = (br.getOffset()[6] + br.getBaseLine() - coordinate.y) / 6;
				firstTime = false;
				coordinate.y += (br.getOffset()[6] + br.getBaseLine() - coordinate.y) % 6;
				airPoseTimer.restart();
			}
			coordinate.y = coordinate.y + fallingSpeed;
			game.repaint();
			// System.out.println(coordinate.y);
			if (coordinate.y >= br.getOffset()[6] + br.getBaseLine()) {
				fallingAnimationTimer.stop();
				// System.out.println("fallingAnimation stopped " + coordinate.y);
				movable = true;
				airPoseTimer.stop();
				changePoseTimer.restart();
			}
		}

		public void setFirstTime(boolean input) {
			firstTime = input;
		}
	}

}
