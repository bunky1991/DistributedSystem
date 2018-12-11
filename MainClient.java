package studentApplication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainClient extends JFrame {	
	public static void main(String[] args) throws IOException {
		MainClient frames = new MainClient();
	}

	JPanel panel = new JPanel();
	JButton viewDetails = new JButton("View details");
	JButton delete = new JButton("Delete");
	JButton logout = new JButton("Log out");

	MainClient() throws IOException {
		super("::Main Menu::");
		setSize(500, 400);
		setLocation(200, 300);
		panel.setLayout(null);

		logout.setBounds(350, 300, 110, 40);
		viewDetails.setBounds(20, 40, 110, 40);
		delete.setBounds(20,300, 110, 40);

		panel.add(viewDetails);
		panel.add(delete);
		panel.add(logout);

		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		deleteDetails();
		viewDetails();
		logout();
	}		
	public void viewDetails() {
		viewDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				viewDetails frame = new viewDetails();
				frame.setVisible(true);
				dispose();
			}
		});
	}
	
	public void deleteDetails() {
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				deleteUser frame = new deleteUser();
				frame.setVisible(true);
				dispose();
			}
		});
	}
	public void logout() {
		logout.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				Login frame = new Login();
				frame.setVisible(true);
				dispose();
			}
		});
	}
}
