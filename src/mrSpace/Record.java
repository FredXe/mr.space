package mrSpace;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//implements MouseListener
public class Record implements MouseListener {
	private JFrame recordf = new JFrame();
	private JButton back_button;
	private JLabel background;

	private ImageIcon back_img;
	private ImageIcon back_img2;
	private ImageIcon back_img3;

	private Clip click_button;
	private Clip over_button;

	Menu menu;

	Record(Menu input) {
		menu = input;
		set_frame();
		set_background();
		set_button();
		try {
			set_music();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		recordf.setVisible(true); // make this visible
	}

	public void set_frame() {
		recordf.setTitle("分數排行");
		recordf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		recordf.setLayout(null);
		recordf.setSize(609, 835);
		ImageIcon logo = new ImageIcon("src/background_image/logo.jpg");
		recordf.setIconImage(logo.getImage()); // change icon of this
		recordf.setResizable(false);// 禁止放大
		recordf.setLocationRelativeTo(null); // 使畫面在螢幕正中間
		recordf.addWindowListener(new WindowAdapter() {
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

	public void set_background() {
		background = new JLabel();
		ImageIcon image = new ImageIcon("src/background_image/record_background.png");
		background.setSize(600, 800);
		background.setIcon(image);
		recordf.add(background);

		// arr.score[0]=get_score()

		Arrays.sort(arr.score);

		JLabel one = new JLabel();
		String str = Integer.toString(arr.score[3]);
		one.setText(str);
		one.setForeground(new Color(0xFFFFFF));
		one.setFont(new Font("特太行書", Font.BOLD, 45));
		one.setBounds(413 - (str.length() * 25), 310, 150, 150);
		background.add(one);
		JLabel one_shadow = new JLabel();
		one_shadow.setText(str);
		one_shadow.setFont(new Font("特太行書", Font.BOLD, 45));
		one_shadow.setBounds(415 - (str.length() * 25), 312, 150, 150);
		background.add(one_shadow);

		JLabel two = new JLabel();
		str = Integer.toString(arr.score[2]);
		two.setText(str);
		two.setForeground(new Color(0xFFFFFF));
		two.setFont(new Font("特太行書", Font.BOLD, 45));
		two.setBounds(413 - (str.length() * 25), 387, 150, 150);
		background.add(two);
		JLabel two_shadow = new JLabel();
		two_shadow.setText(str);
		two_shadow.setFont(new Font("特太行書", Font.BOLD, 45));
		two_shadow.setBounds(415 - (str.length() * 25), 389, 150, 150);
		background.add(two_shadow);

		JLabel three = new JLabel();
		str = Integer.toString(arr.score[1]);
		three.setText(str);
		three.setForeground(new Color(0xFFFFFF));
		three.setFont(new Font("特太行書", Font.BOLD, 45));
		three.setBounds(413 - (str.length() * 25), 465, 150, 150);
		background.add(three);
		JLabel three_shadow = new JLabel();
		three_shadow.setText(str);
		three_shadow.setFont(new Font("特太行書", Font.BOLD, 45));
		three_shadow.setBounds(415 - (str.length() * 25), 467, 150, 150);
		background.add(three_shadow);

	}

	/* Button */
	public void set_button() {
		/* back_button */
		back_button = new JButton();
		back_button.addMouseListener(this);
		// back_button.addMouseListener(this);

		back_img = new ImageIcon("src/button_image/button_back_black.png");
		back_button.setIcon(back_img);
		back_img2 = new ImageIcon("src/button_image/button_back_over.png");
		back_img3 = new ImageIcon("src/button_image/button_back_pressed.png");

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
			back_button.setIcon(back_img3);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource() == back_button) {
			click_button.start();
			recordf.dispose();
			menu.visible();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		if (e.getSource() == back_button) {
			back_button.setIcon(back_img);
		}
	}
}
