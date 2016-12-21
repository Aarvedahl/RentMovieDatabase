package rentAMovie;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Rent {

	public static String showCurrentRent(ResultSet rs) throws SQLException{
		StringBuffer buffer = new StringBuffer();
		
		while(rs.next()){ 
			buffer.append("Users: " + rs.getInt("users") + " Movie: " + rs.getInt("Movie") + " IssuedDate: " + rs.getString("issuedDate") + " ReturnDate: " + rs.getString("returnDate") + "\n"	);
		}
		return buffer.toString();
	}
}
