package rentAMovie;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Movie {

	public static String showMovies(ResultSet rs) throws SQLException{
		StringBuffer buffer = new StringBuffer();
		
		while(rs.next()){ 
			buffer.append("MovieCode: " + rs.getInt("Code") + " Name: " + rs.getString("name") + " Price: " + rs.getInt("price") + " Genre: " + rs.getString("Genre") + "\n"	);
		}
		return buffer.toString();
	}
}
