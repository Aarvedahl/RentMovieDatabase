package rentAMovie;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.JTextArea;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
public class Main extends JFrame {
	private JButton editAUserButton;
	private JButton addAUserButton;
	private JPanel contentPane;
	private JButton showUsersButton;
	private JButton deleteUserButton;
	private JButton showMovieButton;
	private JButton showRentedButton;
	private GroupLayout gl_contentPane;
	private JOptionPane optionPane;
	private JTextArea textArea;
	private JComboBox comboBox;
	private String name;
	private ResultSet res;
	private String address;
	private String newName;
	private static final String USERNAME = "root";
	private static final String PASSWORD = "****";
	private static final String CONN_STRING = "jdbc:mysql://localhost:3306/rentmovie?verifyServerCertificate=false&useSSL=true";
	private ResultSet rs;
	private Statement stm;
	private Connection conn;
	private Statement stm2;
	private JTextField textField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("Netflix 2.0");
		try{
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
			stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stm2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stm.executeQuery("SELECT * FROM users");
			res = stm2.executeQuery("SELECT * FROM movie");	
			
		}catch (SQLException e){
			System.out.println(e.getSQLState());
			System.out.println(e.getErrorCode());
			System.out.println(e.getMessage());
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 721, 492);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		user();
		showRent();		
		
		
		showMovieButton = new JButton("Show All movies");
		showMovieButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String query ="Select * from movie";
				try{	
					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet rset = pst.executeQuery();
					textArea.setText(Movie.showMovies(rset));
					pst.close();
					rset.close();
					
				}
				catch(SQLException e){
					e.getSQLState();
				textArea.setText(e.getMessage());
				}
				
			}
		});
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String query = "Select * from movie where genre=? ";

				try{
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1, (String)comboBox.getSelectedItem());
					ResultSet rs = pst.executeQuery();
					textArea.setText(Movie.showMovies(rs));
					pst.close();
					rs.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		textArea.setEditable(false);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String query = "Select * from movie where name LIKE?";

				try{
				PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
				pst.setString(1, textField.getText() + "%");
				
				ResultSet res = pst.executeQuery();
				while(res.next()){
				textArea.setText("MovieCode: " + res.getInt("Code") + " Name: " + res.getString("name") + " Price: " + res.getInt("price") + " Genre: " + res.getString("Genre") + "\n");
				}
				pst.close();
				res.close();
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
		});
		textField.setColumns(10);
		
		JLabel lblSearchForMovie = new JLabel("Search for Movie titles");
		gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(83))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addComponent(editAUserButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(deleteUserButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(showUsersButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
							.addComponent(addAUserButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(showMovieButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(showRentedButton)
								.addContainerGap()))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSearchForMovie)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
							.addGap(45))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(28)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(addAUserButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(editAUserButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(showUsersButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(deleteUserButton)
							.addGap(18)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(showMovieButton)
							.addGap(50)
							.addComponent(showRentedButton))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblSearchForMovie)))
					.addContainerGap(13, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		fillComboBox();
	}
	
	private void user(){
		addUser();
		editUser();
		showUser();		
		deleteUser();
	}
	
	private void addUser(){	
		addAUserButton = new JButton("Add a user");
		addAUserButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				 name = JOptionPane.showInputDialog("Enter your name: ");
				 address = JOptionPane.showInputDialog("Enter your address: ");
				 String query = User.addUser(name, address);
				 try{
					 	stm = conn.createStatement();
					 	stm.executeUpdate(query);
					
				}catch(SQLException e){
						System.out.println(e.getMessage() );
				}
			}
		});
			
	}
	
	private void editUser(){
		editAUserButton = new JButton("Edit user");
		editAUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userID = JOptionPane.showInputDialog("Input the ID of user you want to Edit: ");
				int id = Integer.parseInt(userID);
				newName = JOptionPane.showInputDialog("Enter the new name of user " + id);
				String newAddress = JOptionPane.showInputDialog("Enter the new address of user " + id);
				String query = User.editUser(newName, newAddress, id);
				try{
					stm = conn.createStatement();
					stm.executeUpdate(query);
			
				}catch(SQLException e){
					System.out.println(e.getMessage());
				}
			}
		});
	}
	
	private void showUser(){
		
		showUsersButton = new JButton("Show users");
		showUsersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query1 = "Select * from Users";	

				try {
				stm = conn.createStatement();
				rs = stm.executeQuery(query1);
				
				textArea.setText(User.displayUsers(rs));
				
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
	}
	
	private void deleteUser(){
		deleteUserButton = new JButton("Delete user");
		deleteUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userID = JOptionPane.showInputDialog("Input the ID of user you want to delete: ");
				int id = Integer.parseInt(userID);
				String query = User.deleteUser(id);
				try{
					stm = conn.createStatement();
					stm.executeUpdate(query);	
					
				}catch(SQLException e2){
					System.out.println(e2.getMessage());
				}
			}		
		});

	}
	private void showRent(){
		showRentedButton = new JButton("Show rented movies");
		showRentedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{	
					String query ="Select * from rent";
					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet rset = pst.executeQuery();
					textArea.setText(Rent.showCurrentRent(rset));
					pst.close();
					rset.close();				
				}
				catch(SQLException e2){
					e2.getSQLState();
				textArea.setText(e2.getMessage());
				}
			}
		});	
	}
	private void fillComboBox(){
		try{

			String query ="Select * from movie";
			stm = conn.createStatement();
			stm.executeQuery(query);
			comboBox.addItem(" ");
			comboBox.addItem("Comedy");
			comboBox.addItem("Biography");
			comboBox.addItem("Action");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
