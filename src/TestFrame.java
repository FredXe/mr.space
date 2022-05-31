import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestFrame extends JFrame implements ActionListener {

	TestFrame() {
		JButton button = new JButton();

		button.setBounds(200, 200, 200, 100);
		button.setText("._.");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(720, 960);
		this.setVisible(true);
		this.add(button);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			// System.out.
		}
	}

}
