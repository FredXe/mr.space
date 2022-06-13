package mrSpace;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

public class Tool {

	public static BufferedImage toBufferedImage(Image input) {
		if (input instanceof BufferedImage) {
			return (BufferedImage) input;
		}
		BufferedImage transformed = new BufferedImage(input.getWidth(null), input.getHeight(null),
				BufferedImage.TYPE_4BYTE_ABGR);

		Graphics2D g2D = transformed.createGraphics();
		g2D.drawImage(input, 0, 0, null);
		g2D.dispose();

		return transformed;
	}

	public static BufferedImage imageScaling(BufferedImage inputBufferedImage, int targetWidth, int targetHeight) {
		BufferedImage outputBufferedImage = new BufferedImage(targetWidth, targetHeight, inputBufferedImage.getType());
		Graphics2D g2D = outputBufferedImage.createGraphics();
		g2D.drawImage(inputBufferedImage, 0, 0, targetWidth, targetHeight, null);
		g2D.dispose();

		return outputBufferedImage;
	}

	/**
	 * scale image
	 * 
	 * @param sbi       image to scale
	 * @param imageType type of image
	 * @param dWidth    width of destination image
	 * @param dHeight   height of destination image
	 * @param fWidth    x-factor for transformation / scaling
	 * @param fHeight   y-factor for transformation / scaling
	 * @return scaled image
	 */

	public static BufferedImage scale(BufferedImage sbi, int dWidth, int dHeight, double fWidth,
			double fHeight) {
		BufferedImage dbi = null;
		if (sbi != null) {
			dbi = new BufferedImage(dWidth, dHeight, sbi.getType());
			Graphics2D g = dbi.createGraphics();
			AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
			g.drawRenderedImage(sbi, at);
		}
		return dbi;
	}

}
