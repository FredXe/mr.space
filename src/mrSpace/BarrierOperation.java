package mrSpace;

import java.util.Random;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class BarrierOperation {

	private Game game = null;
	private Player player = null;

	final public static int TOP = 1;
	final public static int BOTTOM = 0;
	private static Random random = new Random();
	private Barrier topBarrier = null;
	private Barrier bottomBarrier;

	private int[] offset;
	private boolean[] livingSpace;
	private int[] livingSpaceHeight;

	final private float ONE_LIVING_SPACE_PROBABILITY = 2.0f;
	final private float TWO_LIVING_SPACE_PROBABILITY = 3.0f;
	final private float THREE_LIVING_SPACE_PROBABILITY = 1.0f;
	final private float LIVING_SPACE_PROBABILITY_SUM = ONE_LIVING_SPACE_PROBABILITY +
			TWO_LIVING_SPACE_PROBABILITY +
			THREE_LIVING_SPACE_PROBABILITY;

	private FallingAnimationListener fallingAnimationListener = new FallingAnimationListener();
	private Timer fallingAnimationTimer = new Timer(5, fallingAnimationListener);
	private int fallingVeloctiy = 50;

	private RisingAnimationListener risingAnimationListener = new RisingAnimationListener();
	private Timer risingAnimationTimer = new Timer(5, risingAnimationListener);
	private int risingVelocity = 50;

	private PreparationAnimationListener preparationAnimationListener = new PreparationAnimationListener();
	private Timer preparationAnimationTimer = new Timer(5, preparationAnimationListener);
	private int preparationVelocity = 50;

	private HoldDurationListener holdDurationListener = new HoldDurationListener();
	private int holdDuration = 2500;
	private Timer holdDurationTimer = new Timer(holdDuration, holdDurationListener);

	BarrierOperation(Game gameinput) {
		topBarrier = new Barrier();
		bottomBarrier = new Barrier();
		offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		livingSpace = new boolean[] { false, false, false, false, false,
				false, false, false, false, false, false, false };
		livingSpaceHeight = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		game = gameinput;
	}

	public void randomBarrier() {
		topBarrier.refresh();
		bottomBarrier.refresh();
		refresh();

		for (int i = 0; i < offset.length; i++) {
			offset[i] = random.nextInt(-50, 50);
		}

		float livingSpaceRandomNum = random.nextFloat(LIVING_SPACE_PROBABILITY_SUM);
		int livingSpaceAmount = 0;
		if (livingSpaceRandomNum < ONE_LIVING_SPACE_PROBABILITY) {
			livingSpaceAmount = 1;
		} else if (livingSpaceRandomNum < TWO_LIVING_SPACE_PROBABILITY + ONE_LIVING_SPACE_PROBABILITY) {
			livingSpaceAmount = 2;
		} else {
			livingSpaceAmount = 3;
		}

		for (int i = 0; i < livingSpaceAmount; i++) {
			int livingSpacePosition = random.nextInt(0, 12);
			// ----------continue if it has been summonned----------
			if (livingSpace[livingSpacePosition]) {
				i--;
				continue;
			}
			livingSpace[livingSpacePosition] = true;
			livingSpaceHeight[livingSpacePosition] = random.nextInt(30, 50);
		}

		topBarrier.transparency(offset, livingSpaceHeight);
		topBarrier.drawOutLine(offset, livingSpaceHeight);
		bottomBarrier.transparency(offset);
		bottomBarrier.drawOutLine(offset);

		topBarrier.setY(-800);
		// bottomBarrier.setY(-250);
	}

	public Image getTopBarrierImage() {
		return topBarrier.getInstantImage();
	}

	public Image getBottomBarrierImage() {
		return bottomBarrier.getInstantImage();
	}

	public int getTopBarrierY() {
		return topBarrier.getY();
	}

	public int getBottomBarrierY() {
		return bottomBarrier.getY();
	}

	public int[] getOffset() {
		return offset;
	}

	public int getBaseLine() {
		return Barrier.BASE_LINE;
	}

	public void setPlayer(Player input) {
		player = input;
	}

	private void refresh() {
		for (int i = 0; i < Barrier.TRAP_AMOUNT; i++) {
			offset[i] = 0;
			livingSpace[i] = false;
			livingSpaceHeight[i] = 0;
		}
	}

	public boolean[] getLivingSpace() {
		return livingSpace;
	}

	public void barrierAnimation() {
		randomBarrier();
		preparationAnimationTimer.start();
		player.getInitialAnimationTimer().restart();
		player.setRisingAnimaListener(true);
	}

	// --------------------Barrier class--------------------

	private class Barrier {
		final private static Image ORIGIN_IMAGE = new ImageIcon("src/background.png").getImage();
		private BufferedImage instantBufferedImage = Tool.toBufferedImage(ORIGIN_IMAGE);
		private Image instantImage = null;

		final public static int BARRIER_WIDTH = ORIGIN_IMAGE.getWidth(null);
		final public static int BARRIER_HEIGHT = ORIGIN_IMAGE.getHeight(null);

		final public static int TRAP_AMOUNT = 12;
		final public static int BASE_LINE = 3 * BARRIER_HEIGHT / 4;
		public static int TRAP_WIDTH = BARRIER_WIDTH / TRAP_AMOUNT;

		private int y = 0;

		Barrier() {
			instantBufferedImage = Tool.toBufferedImage(ORIGIN_IMAGE);
		}

		private void refresh() {
			instantBufferedImage = Tool.toBufferedImage(ORIGIN_IMAGE);
		}

		private int searchYOffset(int x) {
			return x * TRAP_AMOUNT / BARRIER_WIDTH;
		}

		private void transparency(int[] offset, int[] livingSpaceHeight) {
			for (int x = 0; x < BARRIER_WIDTH; x++) {
				for (int y = BASE_LINE + offset[searchYOffset(x)]
						- livingSpaceHeight[searchYOffset(x)] + 1; y < BARRIER_HEIGHT; y++) {
					int rgb = instantBufferedImage.getRGB(x, y);
					rgb = 0x00fffff & rgb;
					instantBufferedImage.setRGB(x, y, rgb);
				}
			}

			instantImage = instantBufferedImage;
		}

		private void transparency(int[] offset) {
			for (int x = 0; x < BARRIER_WIDTH; x++) {
				for (int y = BASE_LINE
						+ offset[searchYOffset(x)]; y >= 0; y--) {
					int rgb = instantBufferedImage.getRGB(x, y);
					rgb = 0x00fffff & rgb;
					instantBufferedImage.setRGB(x, y, rgb);
				}
			}

			instantImage = instantBufferedImage;
		}

		private void drawOutLine(int[] offset, int[] livingSpaceHeight) {
			int OUTER_STROKE = 5;
			// int INNER_STROKE = 4;
			int X_OFFSET = OUTER_STROKE / 2;
			int Y_OFFSET = OUTER_STROKE / 2 * -1;

			Graphics2D g2D = (Graphics2D) instantBufferedImage.getGraphics();
			g2D.setColor(new Color(0xff403416));
			g2D.setStroke(new BasicStroke(OUTER_STROKE));

			// ----------draw horizontal outerline----------
			for (int i = 0; i < TRAP_AMOUNT; i++) {
				int x1 = i * TRAP_WIDTH + X_OFFSET;
				int y1 = BASE_LINE + Y_OFFSET + offset[i] - livingSpaceHeight[i] + 1;
				int x2 = (i + 1) * TRAP_WIDTH - X_OFFSET;
				int y2 = y1;

				g2D.drawLine(x1, y1, x2, y2);
			}

			// ----------draw vertical outerline----------
			for (int i = 1; i < TRAP_AMOUNT; i++) {
				int tmpXOffset = X_OFFSET;
				int tmpYOffset = Y_OFFSET;
				boolean isYGoingUp = offset[i - 1] - livingSpaceHeight[i - 1] > offset[i] - livingSpaceHeight[i];
				if (isYGoingUp) {
					tmpXOffset = X_OFFSET * -1;
				}

				int x1 = i * TRAP_WIDTH + tmpXOffset;
				int y1 = BASE_LINE + tmpYOffset - livingSpaceHeight[i - 1] + offset[i - 1] + 1;
				int x2 = x1;
				int y2 = BASE_LINE + tmpYOffset - livingSpaceHeight[i] + offset[i] + 1;

				g2D.drawLine(x1, y1, x2, y2);
			}
		}

		private void drawOutLine(int[] offset) {
			int OUTER_STROKE = 5;
			// int INNER_STROKE = 4;
			int X_OFFSET = OUTER_STROKE / 2;
			int Y_OFFSET = OUTER_STROKE / 2;

			Graphics2D g2D = (Graphics2D) instantBufferedImage.getGraphics();
			g2D.setColor(new Color(0xff403416));
			g2D.setStroke(new BasicStroke(OUTER_STROKE));
			// ----------draw horizontal outerline----------
			for (int i = 0; i < TRAP_AMOUNT; i++) {
				int x1 = i * TRAP_WIDTH + X_OFFSET;
				int y1 = BASE_LINE + Y_OFFSET + offset[i];
				int x2 = (i + 1) * TRAP_WIDTH - X_OFFSET;
				int y2 = y1;

				g2D.drawLine(x1, y1, x2, y2);
			}

			// ----------draw vertical outerline----------
			for (int i = 1; i < TRAP_AMOUNT; i++) {
				int tmpXOffset = X_OFFSET;
				boolean isYGoingUp = offset[i - 1] > offset[i];
				if (!isYGoingUp) {
					tmpXOffset = X_OFFSET * -1;
				}

				int x1 = i * TRAP_WIDTH + tmpXOffset;
				int y1 = BASE_LINE + Y_OFFSET + offset[i - 1];
				int x2 = x1;
				int y2 = BASE_LINE + Y_OFFSET + offset[i];

				g2D.drawLine(x1, y1, x2, y2);
			}
		}

		public void setY(int inputY) {
			y = inputY;
		}

		public int getY() {
			return y;
		}

		public Image getInstantImage() {
			return instantImage;
		}

	}

	private class FallingAnimationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			topBarrier.setY(topBarrier.getY() + fallingVeloctiy);
			game.repaint();
			// if(topBarrier.getY() == 0 && !livingSpace[player.getPosition()])
			// {
			// System.out.println("Dead");
			// fallingAnimationTimer.stop();
			// }
			// else
			if (topBarrier.getY() == 0) {
				fallingAnimationTimer.stop();
				risingAnimationTimer.restart();
				player.getRisingAnimationTimer().restart();
				player.setRisingAnimaListener(true);
			}
		}
	}

	private class RisingAnimationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			topBarrier.setY(topBarrier.getY() - risingVelocity);
			game.repaint();
			if (topBarrier.getY() == -800) {
				risingAnimationTimer.stop();
				randomBarrier();
				preparationAnimationTimer.restart();
				player.getFallingAnimationTimer().restart();
				player.setFallingAnimationListener(true);
			}
		}
	}

	private class PreparationAnimationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			topBarrier.setY(topBarrier.getY() + preparationVelocity);

			game.repaint();
			if (topBarrier.getY() == -300) {
				preparationAnimationTimer.stop();
				holdDurationTimer.restart();
			}
		}
	}

	private class HoldDurationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			holdDurationTimer.stop();
			fallingAnimationTimer.restart();
		}
	}

}
