package studentApplication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class deleteUser extends JFrame {
	private final static String host = "localhost";
	private final static int PORT = 8000;
	DataOutputStream toServer;
	DataInputStream fromServer;
	DataOutputStream toServerString;
	ObjectInputStream ObjectfromServer;

	public static void main(String[] args) {
		deleteUser frames = new deleteUser();
	}

	JPanel panel = new JPanel();
	JLabel enterDetails = new JLabel("To delete someone from the database you need to be a admin");
	JLabel labeluser = new JLabel("Username: ");
	static JTextField username = new JTextField();
	JLabel labelpass = new JLabel("Password: ");
	static JPasswordField pass = new JPasswordField();
	static JButton verify = new JButton("Verify");
	static JLabel labelFname = new JLabel("First Name: ");
	static JTextField deleteFname = new JTextField();
	static JLabel labelUser = new JLabel("Username: ");
	static JTextField deleteUsername = new JTextField();
	static JButton menu = new JButton ("Main Menu");
	static JButton delete = new JButton("Delete");

	deleteUser() {
		super("::Student Details::");
		setSize(600, 280);
		setLocation(200, 250);
		panel.setLayout(null);

		enterDetails.setBounds(20, 20, 400, 20);
		labeluser.setBounds(20, 50, 70, 20);
		username.setBounds(90, 50, 100, 20);
		labelpass.setBounds(210, 50, 70, 20);
		pass.setBounds(280, 50, 100, 20);
		verify.setBounds(400, 50, 90, 20);
		labelFname.setBounds(20, 100, 70, 20);
		deleteFname.setBounds(90, 100, 80, 20);
		labelUser.setBounds(20, 130, 70, 20);
		deleteUsername.setBounds(90, 130, 100, 20);
		delete.setBounds(200, 200, 100, 40);
		menu.setBounds(10, 200, 100, 40);

		labelFname.setVisible(false);
		labelUser.setVisible(false);
		deleteUsername.setVisible(false);
		deleteFname.setVisible(false);
		delete.setVisible(false);

		panel.add(labelFname);
		panel.add(deleteFname);
		panel.add(enterDetails);
		panel.add(labeluser);
		panel.add(username);
		panel.add(labelpass);
		panel.add(pass);
		panel.add(verify);
		panel.add(menu);	
		panel.add(labelUser);
		panel.add(deleteUsername);
		panel.add(delete);

		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		checkAdmin();
		menu();
		delete();

	}

	public static void checkAdmin() {
		verify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Socket s = new Socket(host, PORT);
					String connetionType = "checkAdmin";
					DataOutputStream toServerString = new DataOutputStream(s.getOutputStream());
					toServerString.writeUTF(connetionType);
					loginAddress AdminLogin = new loginAddress(username.getText(), pass.getText());
					ObjectOutputStream toServerObject = new ObjectOutputStream(s.getOutputStream());
					toServerObject.writeObject(AdminLogin);

					boolean checkAdmin;
					DataInputStream dataFromServer = new DataInputStream(s.getInputStream());
					checkAdmin = dataFromServer.readBoolean();
					if (checkAdmin == true) {

						labelFname.setVisible(true);
						labelUser.setVisible(true);
						deleteUsername.setVisible(true);
						deleteFname.setVisible(true);
						delete.setVisible(true);

					}
					if (checkAdmin == false) {
						JOptionPane.showMessageDialog(null, "the username or password entered is inncorrect ");
					}
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static void delete() {
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Socket s = new Socket(host, PORT);

					String todelete = "toDelete";
					DataOutputStream toServerString = new DataOutputStream(s.getOutputStream());
					toServerString.writeUTF(todelete);

					ObjectOutputStream toServerObject = new ObjectOutputStream(s.getOutputStream());
					toDelete TD = new toDelete(deleteFname.getText(), deleteUsername.getText());
					toServerObject.writeObject(TD);

					DataInputStream fromServer = new DataInputStream(s.getInputStream());
					boolean deleted = fromServer.readBoolean();

					if (deleted == true) {
						JOptionPane.showMessageDialog(null, "the user has been deleted ");
						deleteUsername.setText(null);
						deleteFname.setText(null);
						username.setText(null);
						username.setFocusable(true);
						pass.setText(null);
						labelFname.setVisible(false);
						labelUser.setVisible(false);
						deleteUsername.setVisible(false);
						deleteFname.setVisible(false);
						delete.setVisible(false);
					}
					if (deleted == false) {
						JOptionPane.showMessageDialog(null, "the user could not be found ");
						deleteUsername.setText(null);
						deleteFname.setText(null);
						deleteFname.setFocusable(true);
					}
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void menu() {
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					MainClient frame = new MainClient();
					frame.setVisible(true);
					dispose();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
