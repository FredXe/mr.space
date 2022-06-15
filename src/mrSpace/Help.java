package mrSpace;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;

public class Help implements MouseListener, KeyListener {
	private JFrame helpf = new JFrame();
	private JButton back_button;
	private JLabel background;
	private JLabel man;
	private JLabel press_A;
	private JLabel press_D;
	private ImageIcon back_img;
	private ImageIcon back_img2;
	private ImageIcon back_img3;

	private Clip click_button;
	private Clip over_button;

	int man_x = 482;
	Menu menu;

	Help(Menu input) {
		menu = input;
		set_frame();
		set_img();
		set_button();
		try {
			set_music();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		helpf.setVisible(true); // make this visible
	}

	public void set_frame() {
		helpf.setTitle("遊戲說明");
		helpf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		helpf.setLayout(null);
		helpf.setSize(609, 835);
		ImageIcon logo = new ImageIcon("src/background_image/logo.jpg");
		helpf.setIconImage(logo.getImage()); // change icon of this
		helpf.setResizable(false);// 禁止放大
		helpf.setLocationRelativeTo(null); // 使畫面在螢幕正中間
		helpf.addKeyListener(this);
		helpf.setFocusable(true);
		helpf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FileWriter record_fr = null;
				try {
					record_fr = new FileWriter("src/record.txt");
				} catch (IOException e1) {
					e1.printStackTrace();
				} // C:/Users/alo20/source/eclipse-workspace/project/src/record.txt
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

	public void set_music() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		File click_file = new File("src/music/back_click.wav");
		AudioInputStream click_audioStream = AudioSystem.getAudioInputStream(click_file);
		click_button = AudioSystem.getClip();
		click_button.open(click_audioStream);

		File over_file = new File("src/music/mouse_over.wav");
		AudioInputStream over_audioStream = AudioSystem.getAudioInputStream(over_file);
		over_button = AudioSystem.getClip();
		over_button.open(over_audioStream);
	}

	public void set_img() {
		background = new JLabel();
		ImageIcon image = new ImageIcon("src/background_image/help_background.png");
		background.setSize(600, 800);
		background.setIcon(image);
		helpf.add(background);

		back_img = new ImageIcon("src/button_image/button_back_black.png");
		back_img2 = new ImageIcon("src/button_image/button_back_over.png");
		back_img3 = new ImageIcon("src/button_image/button_back_pressed.png");
		man = new JLabel();
		ImageIcon man_img = new ImageIcon("src/background_image/stand.png");
		man.setBounds(482, 87, 60, 60);
		man.setIcon(man_img);
		background.add(man);

		press_A = new JLabel();
		ImageIcon A_img = new ImageIcon("src/button_image/button_A_pressed.png");
		press_A.setBounds(126, 498, 46, 51);
		press_A.setIcon(A_img);
		background.add(press_A);
		press_A.setVisible(false);

		press_D = new JLabel();
		ImageIcon D_img = new ImageIcon("src/button_image/button_D_pressed.png");
		press_D.setBounds(335, 498, 46, 51);
		press_D.setIcon(D_img);
		background.add(press_D);
		press_D.setVisible(false);
	}

	/* Button */
	public void set_button() {
		/* back_button */
		back_button = new JButton();
		back_button.addMouseListener(this);
		back_button.setIcon(back_img);
		back_button.setBounds(5, 5, 76, 73);
		back_button.setContentAreaFilled(false);
		back_button.setBorderPainted(false);
		background.add(back_button);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == back_button) {
			System.out.println("back");
			back_button.setIcon(back_img3);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource() == back_button) {
			click_button.start();
			helpf.dispose();
			menu.visible();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		try {
			set_music();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (e.getSource() == back_button) {
			over_button.start();
			back_button.setIcon(back_img2);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == back_button) {
			back_button.setIcon(back_img);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		System.out.println("press");
		if (e.getKeyCode() == KeyEvent.VK_A) {
			press_A.setVisible(true);
			if (man_x - 32 >= 450) {
				man.setBounds(man_x - 32, 87, 60, 60);
				man_x -= 32;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			press_D.setVisible(true);
			if (man_x + 32 <= 514) {
				man.setBounds(man_x + 32, 87, 60, 60);
				man_x += 32;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("release");
		press_A.setVisible(false);
		press_D.setVisible(false);
	}
}
