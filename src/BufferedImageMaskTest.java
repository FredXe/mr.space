import java.awt.image.BufferedImage;

public class BufferedImageMaskTest {
	public static BufferedImage mask(BufferedImage inputBf) {
		int width = inputBf.getWidth();
		int height = inputBf.getHeight();
		// inputBf.
		int[] imagePixels = inputBf.getRGB(0, 0, width, height, null, 0, width);

		for (int i = 0; i < imagePixels.length; i++) {
			imagePixels[i] = 0x00555555;
		}

		// for (int w = 0; w < width; w++) {
		// for (int h = 0; h < height; h++) {
		// inputBf.
		// }
		// }
		inputBf.setRGB(0, 0, width, height, imagePixels, 0, width);

		return inputBf;
	}
}
