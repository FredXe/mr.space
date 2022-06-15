package mrSpace;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu implements MouseListener {
	public JFrame menuf = new JFrame();
	public StartAnimation anime;
	private static JLabel background;
	private Clip background_music;
	private Clip click_button;
	private Clip over_button;

	private JPanel button_panel;
	private JButton start_button;
	private JButton help_button;
	private JButton record_button;

	private ImageIcon start_img;
	private ImageIcon start_img2;
	private ImageIcon start_img3;
	private ImageIcon help_img;
	private ImageIcon help_img2;
	private ImageIcon help_img3;
	private ImageIcon record_img;
	private ImageIcon record_img2;
	private ImageIcon record_img3;

	private boolean check = true;

	Menu() {
		set_frame();
		set_background();
		set_button();
		try {
			set_music();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		background_music.start();
		menuf.setVisible(true);
	}

	public void visible() {
		menuf.setVisible(true);
	}

	public void set_music() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		reset_music();

		File background_file = new File("src/music/menu.wav");
		AudioInputStream background_audioStream = AudioSystem.getAudioInputStream(background_file);
		background_music = AudioSystem.getClip();
		background_music.open(background_audioStream);
		background_music.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void reset_music() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		File click_file = new File("src/music/mouse_click.wav");
		AudioInputStream click_audioStream = AudioSystem.getAudioInputStream(click_file);
		click_button = AudioSystem.getClip();
		click_button.open(click_audioStream);

		File over_file = new File("src/music/mouse_over.wav");
		AudioInputStream over_audioStream = AudioSystem.getAudioInputStream(over_file);
		over_button = AudioSystem.getClip();
		over_button.open(over_audioStream);
	}

	public void set_frame() {
		menuf.setTitle("¶ô°k¡I¡I");
		menuf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuf.setSize(609, 835);
		ImageIcon logo = new ImageIcon("src/background_image/logo.jpg");
		menuf.setIconImage(logo.getImage());
		menuf.setResizable(false);
		menuf.setLocationRelativeTo(null); //
		menuf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FileWriter record_fr = null;
				try {
					record_fr = new FileWriter("src/record.txt");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				BufferedWriter bw = new BufferedWriter(record_fr);
				String str = "";
				for (int i = 0; i < 4; i++) {
					str += arr.score[i];
					str += '\n';
				}
				System.out.println(str);
				try {
					bw.write(str);
					bw.flush();
					bw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public void set_background() {
		background = new JLabel();
		ImageIcon image = new ImageIcon("src/background_image/menu.png");
		background.setIcon(image);

		button_panel = new JPanel();
		button_panel.setOpaque(false);
		button_panel.setLayout(null);
		button_panel.setBounds(0, 0, 600, 800);
		button_panel.setFocusable(false);
		background.add(button_panel);

		menuf.add(background);
	}

	public Clip getBackgroundMusic() {
		return background_music;
	}

	public static JLabel getBackground() {
		return background;
	}

	/* Button */
	public void set_button() {

		/* start_button */
		start_button = new JButton();
		start_button.setFocusable(false);
		start_button.addMouseListener(this);

		start_img = new ImageIcon("src/button_image/button_start.png");
		start_button.setIcon(start_img);
		start_img2 = new ImageIcon("src/button_image/button_start_over.png");
		start_img3 = new ImageIcon("src/button_image/button_start_pressed.png");

		start_button.setBounds(165, 472, 270, 70);
		start_button.setContentAreaFilled(false); // ?????????
		start_button.setBorderPainted(false); // ????????????
		button_panel.add(start_button);

		/* help_button */
		help_button = new JButton();
		help_button.addMouseListener(this);

		help_img = new ImageIcon("src/button_image/button_help.png");
		help_button.setIcon(help_img);
		help_img2 = new ImageIcon("src/button_image/button_help_over.png");
		help_img3 = new ImageIcon("src/button_image/button_help_pressed.png");

		help_button.setBounds(165, 580, 270, 70);
		help_button.setContentAreaFilled(false);
		help_button.setBorderPainted(false);
		button_panel.add(help_button);

		/* record_button */
		record_button = new JButton();
		record_button.addMouseListener(this);

		record_img = new ImageIcon("src/button_image/button_record.png");
		record_button.setIcon(record_img);
		record_img2 = new ImageIcon("src/button_image/button_record_over.png");
		record_img3 = new ImageIcon("src/button_image/button_record_pressed.png");

		record_button.setBounds(165, 688, 270, 70);
		record_button.setContentAreaFilled(false);
		record_button.setBorderPainted(false);
		button_panel.add(record_button);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("mouseClicked");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("mousePressed");
		if (e.getSource() == start_button) {
			System.out.println("start");
			start_button.setIcon(start_img3);
			check = false;
		}

		else if (e.getSource() == help_button) {
			System.out.println("help");
			help_button.setIcon(help_img3);
			check = false;
		}

		else if (e.getSource() == record_button) {
			System.out.println("record");
			record_button.setIcon(record_img3);
			check = false;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("mouseReleased");
		if (e.getSource() == start_button) {
			start_button.setIcon(start_img);
			click_button.start();
			background_music.stop();
			background_music.setMicrosecondPosition(0);
			System.out.println("start");

			start_button.setEnabled(false);
			help_button.setEnabled(false);
			record_button.setEnabled(false);

			start_button.removeMouseListener(this);
			help_button.removeMouseListener(this);
			record_button.removeMouseListener(this);

			anime = new StartAnimation(this);
			menuf.add(anime);
		}

		else if (e.getSource() == help_button) {
			System.out.println("help");
			help_button.setIcon(help_img);
			click_button.start();
			menuf.setVisible(false);
			Help help = new Help(this);
			check = true;
		}

		else if (e.getSource() == record_button) {
			System.out.println("record");
			click_button.start();
			record_button.setIcon(record_img);
			menuf.setVisible(false);
			Record record = new Record(this);
			check = true;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		try {
			reset_music();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		}

		System.out.println("mouseEntered");
		if (e.getSource() == start_button && check) {
			over_button.start();
			start_button.setIcon(start_img2);
		}

		else if (e.getSource() == help_button && check) {
			over_button.start();
			help_button.setIcon(help_img2);
		}

		else if (e.getSource() == record_button && check) {
			over_button.start();
			record_button.setIcon(record_img2);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("mouseExited");
		if (e.getSource() == start_button && check) {
			start_button.setIcon(start_img);
		}

		else if (e.getSource() == help_button && check) {
			help_button.setIcon(help_img);
		}

		else if (e.getSource() == record_button && check) {
			record_button.setIcon(record_img);
		}
	}
}