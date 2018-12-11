package studentApplication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JFrame {
	private final String host = "localhost";
	private final int PORT = 8000;
	private static final long serialVersionUID = 1L;
	DataOutputStream toServer;
	DataOutputStream toServerString;
	DataInputStream FromServer;

	public static void main(String[] args) {
		Register frames = new Register();
	}

	JPanel panel = new JPanel();
	JLabel labelFname = new JLabel("First Name: ");
	JTextField Fname = new JTextField();
	JLabel labelLname = new JLabel("Lastname: ");
	JTextField Lname = new JTextField();
	JLabel labelAge = new JLabel("Age: ");
	JTextField age = new JTextField();
	JLabel lablecourse = new JLabel("Course ID: ");
	JTextField course = new JTextField();
	JLabel labelpass = new JLabel("Password: ");
	JPasswordField password = new JPasswordField(15);
	static JButton donebutton = new JButton("Done");
	JButton register = new JButton("Register");
	JTextField tempuser = new JTextField();
	JTextField User = new JTextField();

	Register() {
		super("::Register::");
		setSize(400, 200);
		setLocation(200, 250);
		panel.setLayout(null);

		labelFname.setBounds(20, 20, 70, 20);
		Fname.setBounds(90, 20, 100, 20);
		labelLname.setBounds(200, 20, 70, 20);
		Lname.setBounds(265, 20, 100, 20);
		labelAge.setBounds(60, 50, 40, 20);
		age.setBounds(90, 50, 50, 20);
		lablecourse.setBounds(25, 80, 80, 20);
		course.setBounds(90, 80, 100, 20);
		labelpass.setBounds(200, 50, 70, 20);
		password.setBounds(265, 50, 100, 20);
		register.setBounds(150, 120, 100, 20);
		User.setBounds(265, 80 ,100, 20);
		donebutton.setBounds(150, 120, 70, 20);
		User.setEditable(false);
		donebutton.setVisible(false);

		panel.add(labelFname);
		panel.add(Fname);
		panel.add(labelLname);
		panel.add(Lname);
		panel.add(labelAge);
		panel.add(age);
		panel.add(lablecourse);
		panel.add(course);
		panel.add(labelpass);
		panel.add(password);
		panel.add(register);
		panel.add(User);
		panel.add(donebutton);

		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		actionReg();
		done();
	}

	public void actionReg() {
		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					// open socket
					Socket s = new Socket(host, PORT);
					// create String connection type
					String connectionUser = "uniqueUser";
					// prepare to send string
					DataOutputStream toServerStringUser = new DataOutputStream(s.getOutputStream());
					// send string
					toServerStringUser.writeUTF(connectionUser);
					// prepare for object to be sent
					ObjectOutputStream toServerObjectUser = new ObjectOutputStream(s.getOutputStream());
					// prepare object
					uniqueUser UNS = new uniqueUser(Fname.getText(), Lname.getText(), age.getText());
					// send object
					toServerObjectUser.writeObject(UNS);
					//recieve data stream
					DataInputStream FromServer = new DataInputStream(s.getInputStream());
					//assign the conents of the data stream to a variable
					String tempUser = FromServer.readUTF();
					tempuser.setText(tempUser);
					String connectionType = "register";
					// send this String to server
					DataOutputStream toServerStringReg = new DataOutputStream(s.getOutputStream());
					// send this to server
					toServerStringReg.writeUTF(connectionType);
					// prepare object
					User.setText(tempUser);
					ObjectOutputStream toServer = new ObjectOutputStream(s.getOutputStream());
					// make Object
					student NS = new student(Fname.getText(), Lname.getText(), age.getText(), course.getText(),
							password.getText(), tempuser.getText());
					// send object
					toServer.writeObject(NS);
					
					DataInputStream inputFromServer = new DataInputStream(s.getInputStream());
					String connection = inputFromServer.readUTF();

					if (connection.equals("InfoAdded")) {
						JOptionPane.showMessageDialog(null,Fname.getText() + " your account has been made \nusername is " 
								+ tempuser.getText() + " \n please copy your Username from register page, \nthen press done");
						register.setVisible(false);
						donebutton.setVisible(true);
						
					}
				} catch (UnknownHostException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				}
			}
		});
	}
	
	public void done() {
		donebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Login frame = new Login();
				frame.setVisible(true);
				dispose();
				
			}
			
		});
	}

}
