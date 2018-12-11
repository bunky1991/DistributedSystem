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

public class viewDetails extends JFrame {
	private final String host = "localhost";
	private final int PORT = 8000;
	DataOutputStream toServer;
	DataInputStream fromServer;
	DataOutputStream toServerString;
	ObjectInputStream ObjectfromServer;

	public static void main(String[] args) {
		viewDetails frames = new viewDetails();
	}

	JPanel panel = new JPanel();
	JLabel enterDetails = new JLabel("To view your details please enter your username and password.");
	JLabel labeluser = new JLabel("Username: ");
	JTextField username = new JTextField();
	JLabel labelpass = new JLabel("Password: ");
	JPasswordField pass = new JPasswordField();
	JButton verify = new JButton("Verify");

	JLabel labelFname = new JLabel("First Name: ");
	JTextField Fname = new JTextField();
	JLabel labelLname = new JLabel("Lastname: ");
	JTextField Lname = new JTextField();
	JLabel labelage = new JLabel("Age: ");
	JTextField age = new JTextField();
	JLabel labelcourse = new JLabel("Course name: ");
	JTextField courseid = new JTextField();
	JLabel labelUser = new JLabel("Username: ");
	JTextField Username = new JTextField();
	JLabel labelPassword = new JLabel("Password: ");
	JPasswordField password = new JPasswordField();
	JButton update = new JButton("Update");

	viewDetails() {
		super("::Student Details::");
		setSize(600, 280);
		setLocation(200, 250);
		panel.setLayout(null);
		
		// relogin
		enterDetails.setBounds(20, 20, 400, 20);
		labeluser.setBounds(20, 50, 70, 20);
		username.setBounds(90, 50, 100, 20);
		labelpass.setBounds(210, 50, 70, 20);
		pass.setBounds(280, 50, 100, 20);
		verify.setBounds(400, 50, 90, 20);

		// details
		labelFname.setBounds(20, 100, 70, 20);
		Fname.setBounds(90, 100, 80, 20);
		labelLname.setBounds(180, 100, 70, 20);
		Lname.setBounds(250, 100, 80, 20);
		labelage.setBounds(350, 100, 70, 20);
		age.setBounds(380, 100, 70, 20);
		labelcourse.setBounds(20, 140, 90, 20);
		courseid.setBounds(110, 140, 100, 20);
		labelUser.setBounds(20, 170, 70, 20);
		Username.setBounds(90, 170, 100, 20);
		labelPassword.setBounds(220, 170, 70, 20);
		password.setBounds(290, 170, 100, 20);
		update.setBounds(200, 200, 100, 40);

		labelFname.setVisible(false);
		Fname.setVisible(false);
		labelLname.setVisible(false);
		Lname.setVisible(false);
		labelage.setVisible(false);
		age.setVisible(false);
		labelcourse.setVisible(false);
		courseid.setVisible(false);
		labelUser.setVisible(false);
		Username.setVisible(false);
		labelPassword.setVisible(false);
		password.setVisible(false);
		update.setVisible(false);
		
		panel.add(labelFname);
		panel.add(Fname);
		panel.add(Lname);
		panel.add(labelLname);
		panel.add(enterDetails);
		panel.add(labeluser);
		panel.add(username);
		panel.add(labelpass);
		panel.add(pass);
		panel.add(verify);
		panel.add(labelage);
		panel.add(age);
		panel.add(courseid);
		panel.add(labelcourse);
		panel.add(labelUser);
		panel.add(Username);
		panel.add(labelPassword);
		panel.add(password);
		panel.add(update);

		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		viewDetails();
		Update();

	}

	public void viewDetails() {

		verify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Socket s = new Socket(host, PORT);

					String connectionType = "viewDetails";
					DataOutputStream toServerString = new DataOutputStream(s.getOutputStream());
					toServerString.writeUTF(connectionType);
					System.out.println("write viewDetails to server");
					loginAddress lg = new loginAddress(username.getText(), pass.getText());
					ObjectOutputStream toServerObject = new ObjectOutputStream(s.getOutputStream());
					toServerObject.writeObject(lg);

					ObjectfromServer = new ObjectInputStream(s.getInputStream());
					System.out.println("third");
					student NS = (student) ObjectfromServer.readObject();
					System.out.println("fourth");

					String first = NS.getFirstname();
					String last = NS.getLastname();
					String ageset = NS.getAge();
					String course = NS.getCourseID();
					String userName = NS.getUsername();
					String Pass = NS.getPassword();

					Fname.setText(first);
					Lname.setText(last);
					age.setText(ageset);
					courseid.setText(course);
					Username.setText(userName);
					password.setText(Pass);
					labelFname.setVisible(true);
					Fname.setVisible(true);
					labelLname.setVisible(true);
					Lname.setVisible(true);
					labelage.setVisible(true);
					age.setVisible(true);
					labelcourse.setVisible(true);
					courseid.setVisible(true);
					labelUser.setVisible(true);
					Username.setVisible(true);
					labelPassword.setVisible(true);
					password.setVisible(true);
					update.setVisible(true);
					username.setEditable(false);
					pass.setEditable(false);

				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void Update() {
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//Open socket
					Socket s = new Socket(host, PORT);
					//prepare string for server
					String connetionType = "update";
					//prepare data output stream
					DataOutputStream toServerString = new DataOutputStream(s.getOutputStream());
					//send the string to server
					toServerString.writeUTF(connetionType);
					//prepare object to send to server
					changeDetails CD = new changeDetails(username.getText(), pass.getText(), Fname.getText(),
							Lname.getText(), age.getText(), courseid.getText(), password.getText(), Username.getText());
					//prepare object output stream
					ObjectOutputStream toServerObject = new ObjectOutputStream(s.getOutputStream());
					//send object to server
					toServerObject.writeObject(CD);
					//prepare input stream
					DataInputStream fromServerString = new DataInputStream(s.getInputStream());
					//set the new inputed string to a variable
					String connection = fromServerString.readUTF();
					//If the variable contains "Updated" 
					if (connection.equals("Updated")) {
						//states that your information has been updated
						
						JOptionPane.showMessageDialog(null, "the user details have been updated ");
						//last step will put you back to main menu
						MainClient frame = new MainClient();
						frame.setVisible(true);
						dispose();
					}
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
