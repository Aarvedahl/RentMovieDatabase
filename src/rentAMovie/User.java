package rentAMovie;

import java.sql.ResultSet;
import java.sql.SQLException;


public class User {

	public static String displayUsers(ResultSet rs) throws SQLException{
		StringBuffer buffer = new StringBuffer();
		
		while(rs.next()){ 
			buffer.append("ID: " + rs.getInt("ID") + " Name: " + rs.getString("name") + " Adress: " + rs.getString("address") + "\n");
		}
		return buffer.toString();
	}
	
	public static String editUser(String newName, String address, int id){
		String query = "Update users " + "Set name = '" + newName + "'," + "address = '" + address + "' where id = '" + id + "'";
		return query;		
	}
	
	public static String addUser (String name, String address) {
		String query = "Insert into Users (name, address) values ('" + name + "','" + address + "')";
		return query;
	}
	
	public static String deleteUser(int id){
		String query ="Delete from users where id = '" + id + "'";
		return query;
	}

	
	
}

