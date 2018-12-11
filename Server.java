package studentApplication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Server {
	// output to file
	static ObjectOutputStream outputToFile;
	// Object Streams
	static ObjectInputStream inputFromClient;
	static ObjectOutputStream outputToClient;
	// data Streams
	static DataInputStream inputStringFromClient;
	static DataOutputStream outputStringToClient;
	// ServerSocket and Socket for server Connection
	private static ServerSocket serverSocket;
	private static Socket s;

	public static void main(String[] args) throws SQLException {

		try {
			serverSocket = new ServerSocket(8000);
			System.out.println("Server Running");
			while (true) {
				// accept connection
				s = serverSocket.accept();
				// prepare for input String
				inputStringFromClient = new DataInputStream(s.getInputStream());
				// set the input String in to connection (varialbe)
				String connection = inputStringFromClient.readUTF();
				// if connection is one of the following steps ten will begin that method in the
				// if statement
				if (connection.equals("login")) {
					System.out.println("Server Running: Login");
					// if boolean is true or false it was send that data
					boolean result = checkUsername();
					outputStringToClient = new DataOutputStream(s.getOutputStream());
					outputStringToClient.writeBoolean(result);

				}
				if (connection.equals("uniqueUser")) {
					System.out.println("Server Running: Random Username generator");
					RandomUser();
					// the random username is sent back to register
					inputStringFromClient = new DataInputStream(s.getInputStream());
					// the full object of register including the unique username
					String connectionReg = inputStringFromClient.readUTF();
					if (connectionReg.equals("register")) {
						System.out.println("Server Running: Register");
						addStudent();
					}
				}
				if (connection.equals("viewDetails")) {
					System.out.println("Server Running: View Details");
					viewDetails();
				}
				if (connection.equals("update")) {
					System.out.println("Server Running: Update");
					Update();
				}
				if (connection.equals("checkAdmin")) {
					System.out.println("Server Running: checkAdmin");
					boolean result = CheckAdmin();
					outputStringToClient = new DataOutputStream(s.getOutputStream());
					outputStringToClient.writeBoolean(result);
				}
				if (connection.equals("toDelete")) {
					System.out.println("Server Running: Delete");
					toDelete();
				}
			}
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			try {
				outputToFile = new ObjectOutputStream(new FileOutputStream("StudentInfo.dat", true));
				outputToFile.close();
				serverSocket.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static boolean checkUsername() throws SQLException, ClassNotFoundException, IOException {

		// receive login object from the client
		inputFromClient = new ObjectInputStream(s.getInputStream());
		loginAddress login = (loginAddress) inputFromClient.readObject();
		// get username and pass from this object
		String username = login.getUsername();
		String password = login.getPassword();
		// check if these records are in database 
		System.out.println("Server Running: Check Username: Starting MYSql DataBase");
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/students", "root", "CMD password");
		// The query that will be sent to database
		String query = "select * from studentInformation where username = ? and password  = ?";
		PreparedStatement statement = conn.prepareStatement(query);

		statement.setString(1, username);
		statement.setString(2, password);

		boolean result = false;

		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			result = true;
		}

		return result;

	}

	public static void addStudent() throws SQLException, ClassNotFoundException, IOException {
		// prepare for input Object
		inputFromClient = new ObjectInputStream(s.getInputStream());
		// state what the object is and assign it to a variable in this case NS
		student NS = (student) inputFromClient.readObject();

		String Sfirstname = NS.getFirstname();
		String Slastname = NS.getLastname();
		String Sage = NS.getAge();
		String ScourseID = NS.getCourseID();
		String Spassword = NS.getPassword();
		String Susername = NS.getUsername();

		System.out.println("Server Running: addStudent: Starting MYSql DataBase");
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/students", "root", "CMD password");
		String query = "insert into studentInformation(firstname, lastname, age, courseID, password, username)"
				+ "values(?,?,?,?,?,?)";
		PreparedStatement statement = conn.prepareStatement(query);

		statement.setString(1, Sfirstname);
		statement.setString(2, Slastname);
		statement.setString(3, Sage);
		statement.setString(4, ScourseID);
		statement.setString(5, Spassword);
		statement.setString(6, Susername);
		statement.executeUpdate();

		String connectionType = "InfoAdded";
		DataOutputStream toClient = new DataOutputStream(s.getOutputStream());
		toClient.writeUTF(connectionType);

	}

	public static void RandomUser() throws IOException, ClassNotFoundException {
		// this is to retrieve the object input
		inputFromClient = new ObjectInputStream(s.getInputStream());
		uniqueUser UNS = (uniqueUser) inputFromClient.readObject();
		// the username is made from the students fristname, last name and age
		// the first 3 letters are used from firstname
		String SFirstname = UNS.getFirstname();

		char firstname1 = SFirstname.charAt(0);
		char firstname2 = SFirstname.charAt(2);
		char firstname3 = SFirstname.charAt(1);

		String f1 = String.valueOf(firstname1);
		String f2 = String.valueOf(firstname2);
		String f3 = String.valueOf(firstname3);

		// the lastname letters 2,3,4 are used.
		String SLastname = UNS.getLastname();

		char lastname1 = SLastname.charAt(3);
		char lastname2 = SLastname.charAt(2);
		char lastname3 = SLastname.charAt(1);

		String l1 = String.valueOf(lastname1);
		String l2 = String.valueOf(lastname2);
		String l3 = String.valueOf(lastname3);

		// the age is used to retrieve 2 letters
		String SAge = UNS.getAge();

		char charAge1 = SAge.charAt(1);
		char charAge2 = SAge.charAt(0);

		String a1 = String.valueOf(charAge1);
		String a2 = String.valueOf(charAge2);
		// put all the letters and number back together and put the in to a variable
		String Username = f2 + l1 + f1 + a1 + f3 + l3 + l2 + a2;
		// send the Username back to register page.
		DataOutputStream dataToClient = new DataOutputStream(s.getOutputStream());
		dataToClient.writeUTF(Username);

	}

	public static void viewDetails() throws ClassNotFoundException, SQLException, IOException {
		System.out.println("Server Running: View Details: Starting MYSql DataBase");
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/students", "root", "CMD password");

		ObjectInputStream inputFromClient = new ObjectInputStream(s.getInputStream());
		loginAddress lg = (loginAddress) inputFromClient.readObject();

		String username = lg.getUsername();
		String password = lg.getPassword();

		String query = "select * from studentInformation where username = ? and password  = ?";

		PreparedStatement statement = conn.prepareStatement(query);

		statement.setString(1, username);
		statement.setString(2, password);

		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			String firstname = rs.getString(1);
			String lastname = rs.getString(2);
			String age = rs.getString(3);
			String courseID = rs.getString(4);
			String pass = rs.getString(5);
			String userName = rs.getString(6);

			student NS = new student(firstname, lastname, age, courseID, pass, userName);
			ObjectOutputStream toClient = new ObjectOutputStream(s.getOutputStream());
			toClient.writeObject(NS);

		}
		return;

	}

	public static void Update() throws ClassNotFoundException, SQLException, IOException {
		System.out.println("Server Running: Update: Starting MYSql DataBase");
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/students", "root", "CMD password");

		ObjectInputStream inputFromClient = new ObjectInputStream(s.getInputStream());
		changeDetails CD = (changeDetails) inputFromClient.readObject();

		String Username = CD.getFixedusername();
		String Password = CD.getFixedPassword();
		String Firstname = CD.getFirstname();
		String LastName = CD.getLastname();
		String age = CD.getAge();
		String course = CD.getCourseID();
		String username = CD.getUsername();
		String password = CD.getPassword();

		String queryUpdate = "update studentInformation"
				+ " set firstname = ?,  lastname = ?, age = ?, courseID = ?, password = ?, username = ? "
				+ " where Password = ? and Username = ? ";

		PreparedStatement statement = conn.prepareStatement(queryUpdate);

		statement.setString(1, Firstname);
		statement.setString(2, LastName);
		statement.setString(3, age);
		statement.setString(4, course);
		statement.setString(5, password);
		statement.setString(6, username);
		statement.setString(7, Password);
		statement.setString(8, Username);

		statement.executeUpdate();

		String connection = "Updated";

		DataOutputStream toServerString = new DataOutputStream(s.getOutputStream());
		toServerString.writeUTF(connection);

	}

	public static boolean CheckAdmin() throws ClassNotFoundException, SQLException, IOException {
		System.out.println("Server Running: Update: Starting MYSql DataBase");
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/students", "root", "CMD password");

		ObjectInputStream inputFromClient = new ObjectInputStream(s.getInputStream());
		loginAddress AdminLogin = (loginAddress) inputFromClient.readObject();

		String username = AdminLogin.getUsername();
		String password = AdminLogin.getPassword();

		String query = "select * from Admin where username = ? and password  = ?";
		PreparedStatement statement = conn.prepareStatement(query);

		statement.setString(1, username);
		statement.setString(2, password);

		boolean result = false;

		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			result = true;
			if (result == true) {

			}

		}

		return result;

	}

	public static void toDelete() throws ClassNotFoundException, SQLException, IOException {
		System.out.println("Server Running: Delete: Starting MYSql DataBase");
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/students", "root", "CMD password");


		ObjectInputStream inputFromClient = new ObjectInputStream(s.getInputStream());
		toDelete TD = (toDelete) inputFromClient.readObject();
	
		String Fname = TD.getFname();
		String username = TD.getUsername();

		String Query = "select * from studentInformation where firstname = ? and username = ?";
		String deleteQuery = "delete from studentInformation where firstname = ? and username = ?";
	
		PreparedStatement statement = conn.prepareStatement(Query);

		statement.setString(1, Fname);
		statement.setString(2, username);

		boolean result = false;

		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			result = true;
		}

		if (result == true) {
			PreparedStatement state = conn.prepareStatement(deleteQuery);

			state.setString(1, Fname);
			state.setString(2, username);
			state.executeUpdate();

			boolean sendresult = result;
			DataOutputStream toDeleteClient = new DataOutputStream(s.getOutputStream());
			toDeleteClient.writeBoolean(result);
		}
		if (result == false) {
			boolean sendresult = result;
			DataOutputStream toDeleteClient = new DataOutputStream(s.getOutputStream());
			toDeleteClient.writeBoolean(result);
		}

	}
}
