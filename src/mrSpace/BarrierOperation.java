package mrSpace;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class BarrierOperation {
	
	final public static int TOP = 1;
	final public static int BOTTOM = 0;
	private Barrier upperBarrier = null;
	private int[] offset;
	//private Barrier lowerBarrier;
	
	BarrierOperation()
	{
		upperBarrier = new Barrier();
		offset = new int[] {0,-100,0,0,-100,0,0,-100,0,0,0,0};
		upperBarrier.transparency(TOP, offset);
	}
	public Image getter()
	{
		return upperBarrier.getInstantImage();
	}
	
	private class Barrier 
	{	
		final private static BufferedImage ORIGIN_BUFFEREDIMAGE = Tool.toBufferedImage(new ImageIcon("src/background.png").getImage());
		final public static int BARRIER_WIDTH = ORIGIN_BUFFEREDIMAGE.getWidth();
		final public static int BARRIER_HEIGHT = ORIGIN_BUFFEREDIMAGE.getHeight();
		
		final public static int TRAP_AMOUNT = 12;
		final public static int BASE_LINE = BARRIER_HEIGHT/2;
		public static int TRAP_WIDTH = BARRIER_WIDTH/TRAP_AMOUNT;
		private int[] offset;
		private BufferedImage instantBufferedImage = null;
		private Image instantImage = null;
		
		Barrier()
		{	
			instantBufferedImage = ORIGIN_BUFFEREDIMAGE;
		}
		private void refresh()
		{
			instantBufferedImage = ORIGIN_BUFFEREDIMAGE;
		}
		private void transparency (int isTop, int[] offset)
		{	
			
			for(int x = 0  ; x < BARRIER_WIDTH ; x++)
			{	
				for(int y = BASE_LINE + offset[x*TRAP_AMOUNT/BARRIER_WIDTH] ; !(y == isTop*(BARRIER_HEIGHT)); y++)
				{
					//Graphics2D g2D = (Graphics2D)instantBufferedImage.getGraphics();
					int rgb = instantBufferedImage.getRGB(x, y);
					rgb = 0x00fffff & rgb;
					instantBufferedImage.setRGB(x, y, rgb);
				}
			}
			instantImage = instantBufferedImage;
		}
		public Image getInstantImage()
		{
			return instantImage;
		}
	}
}
