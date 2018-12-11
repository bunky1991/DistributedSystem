package studentApplication;

import javax.swing.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Login extends JFrame {

	static ObjectInputStream inputFromServer;
	private DataInputStream dataFromServer;
	
	public boolean mainClient = false;
	private static final long serialVersionUID = 01L;
	private static final int PORT = 8000;
	private final String host = "localhost";
	
	public static void main(String[] args) {
		Login frames = new Login();
	}

	JButton login = new JButton("Login");
	JButton reg = new JButton("Register");
	JPanel panel = new JPanel();
	JTextField user = new JTextField();
	JPasswordField pass = new JPasswordField();

	Login() {
		super("::Login::");
		setSize(300, 200);
		setLocation(200, 300);
		panel.setLayout(null);

		user.setBounds(100, 40, 110, 20);
		pass.setBounds(100, 70, 110, 20);
		login.setBounds(115, 100, 80, 20);
		reg.setBounds(110, 130, 90, 20);

		panel.add(user);
		panel.add(pass);
		panel.add(login);
		panel.add(reg);

		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		onLogin();
		actionreg();
		actionLogin();

	}

	public void actionreg() {
		reg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Register newframe = new Register();
				newframe.setVisible(true);
				dispose();
			}
		});
	}
	public void actionLogin() throws NullPointerException {
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					Socket s = new Socket(host, PORT);
					// create string connection type
					String connectionType = "login";
					System.out.println("connectionType 'login'");
					// send this string to server
					DataOutputStream toServer = new DataOutputStream(s.getOutputStream());
					toServer.writeUTF(connectionType);
					// get information from text fields
					loginAddress lg = new loginAddress(user.getText(), pass.getText());
					// send object to server
					ObjectOutputStream toServerObject = new ObjectOutputStream(s.getOutputStream());
					toServerObject.writeObject(lg);
					// receive boolean from server
					boolean loginV;
					dataFromServer = new DataInputStream(s.getInputStream());
					loginV = dataFromServer.readBoolean();
				
					// if true open a new window, false - show error
					if (loginV == true) {
						MainClient frame = new MainClient();
						frame.setVisible(true);
						dispose();
						System.out.println("Login == true");
					}
					if (loginV == false) {
						JOptionPane.showMessageDialog(null, "Username/Password not recognised:");
						System.out.println("loginV == false ");
					}
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				}

			}
		});
	}

	public void onLogin() {
		System.out.println("onLogin");
	}
}