package mrSpace;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;

import javax.swing.ImageIcon;

public class BarrierOperation {

	final public static int TOP = 1;
	final public static int BOTTOM = 0;
	private Barrier upperBarrier = null;
	private int[] offset;
	// private Barrier lowerBarrier;

	BarrierOperation() {
		upperBarrier = new Barrier();
		offset = new int[] { 0, -100, 0, 0, -100, 0, 0, -100, 0, 0, 0, 0 };
		upperBarrier.transparency(TOP, offset);
	}

	public Image getter() {
		return upperBarrier.getInstantImage();
	}

	// --------------------Barrier class--------------------

	private class Barrier {
		final private static BufferedImage ORIGIN_BUFFEREDIMAGE = Tool
				.toBufferedImage(new ImageIcon("src/background.png").getImage());
		private BufferedImage instantBufferedImage = null;
		private Image instantImage = null;

		final public static int BARRIER_WIDTH = ORIGIN_BUFFEREDIMAGE.getWidth();
		final public static int BARRIER_HEIGHT = ORIGIN_BUFFEREDIMAGE.getHeight();

		final public static int TRAP_AMOUNT = 12;
		final public static int BASE_LINE = BARRIER_HEIGHT / 2;
		public static int TRAP_WIDTH = BARRIER_WIDTH / TRAP_AMOUNT;

		private int[] offset;

		Barrier() {
			instantBufferedImage = ORIGIN_BUFFEREDIMAGE;
		}

		private void refresh() {
			instantBufferedImage = ORIGIN_BUFFEREDIMAGE;
		}

		private int searchYOffset(int x) {
			return x * TRAP_AMOUNT / BARRIER_WIDTH;
		}

		private void transparency(int isTop, int[] offset) {

			for (int x = 0; x < BARRIER_WIDTH; x++) {
				for (int y = BASE_LINE
						+ offset[searchYOffset(x)]; !(y == isTop * (BARRIER_HEIGHT)); y++) {
					int rgb = instantBufferedImage.getRGB(x, y);
					rgb = 0x00fffff & rgb;
					instantBufferedImage.setRGB(x, y, rgb);
				}
			}
			drawOutLine(isTop, offset);
			instantImage = instantBufferedImage;
		}

		private void drawOutLine(int isTop, int[] offset) {
			int OUTER_STROKE = 20;
			// int INNER_STROKE = 4;
			int X_OFFSET = OUTER_STROKE / 2;
			int Y_OFFSET = OUTER_STROKE / 2;
			if (isTop == 1) {
				Y_OFFSET = Y_OFFSET * -1;
			}

			Graphics2D g2D = (Graphics2D) instantBufferedImage.getGraphics();
			g2D.setColor(new Color(0xff403416));
			g2D.setStroke(new BasicStroke(OUTER_STROKE));
			// ----------draw horizontal outerline----------
			for (int i = 0; i < TRAP_AMOUNT; i++) {
				int x1 = i * TRAP_WIDTH + X_OFFSET;
				int y1 = BASE_LINE + offset[i] + Y_OFFSET;
				int x2 = (i + 1) * TRAP_WIDTH - X_OFFSET;
				int y2 = y1;

				g2D.drawLine(x1, y1, x2, y2);
			}

			// ----------draw vertical outerline----------
			for (int i = 1; i < TRAP_AMOUNT; i++) {
				int tmpXOffset = X_OFFSET;
				boolean isYGoingUp = offset[i - 1] > offset[i];
				if (isYGoingUp == (isTop == 1)) {
					tmpXOffset = X_OFFSET * -1;
				}

				int x1 = i * TRAP_WIDTH + tmpXOffset;
				int y1 = BASE_LINE + Y_OFFSET + offset[i - 1];
				int x2 = x1;
				int y2 = BASE_LINE + Y_OFFSET + offset[i];

				System.out.println(x1 + ", " + y1 + ", " + x2 + ", " + y2);

				g2D.drawLine(x1, y1, x2, y2);
			}
		}

		public Image getInstantImage() {
			return instantImage;
		}
	}
}
