package mrSpace;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class StartAnimation extends JPanel implements ActionListener {
	public Game game;
	public Menu menu;
	public boolean animationCheck = false;
	Image up_background;
	Image low_background;
	Image low_no_background;
	Image man;
	Image shock_man;
	Image background_img;
	Timer timer;

	int x = 0;
	int upy = -202;
	int lowy = 0;
	int xVelocity = 8;
	int yVelocity = 8;
	int time = 0;

	boolean uplow = true;
	boolean check_man = false;
	boolean check_shock_man = false;
	private Clip close;
	private Clip open_wall;

	StartAnimation(Menu input) {
		menu = input;
		setpanel();
	}

	public void setpanel() {
		background_img = new ImageIcon("src/background_image/background_color.png").getImage();
		this.setBounds(0, 0, 600, 800);
		up_background = new ImageIcon("src/background_image/rockwall_upper_y_202.png").getImage();
		low_background = new ImageIcon("src/background_image/rockwall_lower_withStickman.png").getImage();
		low_no_background = new ImageIcon("src/background_image/rockwall_lower_noStickman.png").getImage();
		man = new ImageIcon("src/background_image/stand.png").getImage();
		shock_man = new ImageIcon("src/background_image/shock.png").getImage();
		timer = new Timer(1, this);
		timer.start();
	}

	public void set_music() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		File close_file = new File("src/music/close.wav");
		AudioInputStream close_audioStream = AudioSystem.getAudioInputStream(close_file);
		close = AudioSystem.getClip();
		close.open(close_audioStream);

		File open_file = new File("src/music/open.wav");
		AudioInputStream open_audioStream = AudioSystem.getAudioInputStream(open_file);
		open_wall = AudioSystem.getClip();
		open_wall.open(open_audioStream);
	}

	public void paint(Graphics g) {

		// super.paint(g);

		Graphics2D g2D_5 = (Graphics2D) g;
		g2D_5.drawImage(background_img, 0, 0, null);

		Graphics2D g2D_2 = (Graphics2D) g;
		g2D_2.drawImage(low_background, x, lowy, null);

		Graphics2D g2D_1 = (Graphics2D) g;
		g2D_1.drawImage(up_background, x, upy, null);

		if (check_man) {
			Graphics2D g2D_3 = (Graphics2D) g;
			g2D_3.drawImage(man, 245, 379, null);
		}

		else if (check_shock_man) {
			Graphics2D g2D_4 = (Graphics2D) g;
			g2D_4.drawImage(shock_man, 245, 379, null);
		}
		update(getGraphics());
	}

	public void update(Graphics g) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (upy <= -74 && lowy == 0) {
			upy = upy + yVelocity;
		} else if (time <= 3 && uplow == true) {
			if (time == 0) {
				try {
					set_music();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e2) {
					e2.printStackTrace();
				}
				close.start();
			}
			low_background = low_no_background;
			check_shock_man = true;
			timer.stop();
			timer = new Timer(100, this);
			timer.start();
			upy = upy - yVelocity;
			lowy = lowy - yVelocity;
			time++;
			uplow = false;
		} else if (time <= 3 && uplow == false) {
			upy = upy + yVelocity;
			lowy = lowy + yVelocity;
			time++;
			uplow = true;
		} else if (time == 4) {
			try {
				TimeUnit.MILLISECONDS.sleep(400);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			open_wall.start();
			check_man = true;
			time++;
			timer.stop();
			timer = new Timer(1, this);
			timer.start();
		} else if (lowy <= 480) {
			upy = upy - yVelocity;
			lowy = lowy + yVelocity;
			repaint();
			if (lowy > 480) {
				menu.menuf.remove(Menu.getBackground());
				open_wall.stop();
				timer.stop();
				game = new Game(menu);
				System.out.print("ajsfhshaf");
				menu.menuf.add(game);
			}
		}
		if (lowy <= 480)
			repaint();

	}
}