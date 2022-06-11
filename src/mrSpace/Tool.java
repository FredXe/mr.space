package mrSpace;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
public class Tool {
	
	public static BufferedImage toBufferedImage(Image input)
	{
		if(input instanceof BufferedImage)
		{
			return (BufferedImage)input;
		}
		BufferedImage transformed = new BufferedImage(input.getWidth(null),input.getHeight(null),BufferedImage.TYPE_4BYTE_ABGR);
		
		Graphics2D g2D = transformed.createGraphics();
		g2D.drawImage(input, 0, 0, null);
		g2D.dispose();
		
		return transformed;
	}
}
