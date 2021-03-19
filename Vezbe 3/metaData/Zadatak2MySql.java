/*
 * Napisati metod koji je u stanju da izvr�i bilo koji SELECT upit i procita njegov ResultSet
 * Rezultate mo�e da �vraca� na proizvoljan nacin.
 * Potrebno je obezbediti citanje celih brojeva, stringova i datuma.
 */

package metaData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Zadatak2MySql {

	public static void main(String[] args) {

		Connection conn = null;
		ResultSet rSet = null;
		Statement stmt = null;
		BufferedReader br = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/nastavnikpredmet", "root", "root");
			
			System.out.println("Unesi neki upit:");
			br = new BufferedReader(new InputStreamReader(System.in));
			String query = br.readLine();
			
			stmt = conn.createStatement();
			rSet = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rSet.getMetaData();
			
			int brKolona = rsmd.getColumnCount();
			for (int i = 1; i <= brKolona; i++) {
				System.out.println(rsmd.getColumnName(i) + "[" + rsmd.getColumnTypeName(i) + "]");
			}
			System.out.println();
			
			while (rSet.next()) {
				for (int i = 1; i <= brKolona; i++) {
					if (rsmd.getColumnTypeName(i).equals("INT"))
						System.out.print(rSet.getInt(i) + " ");
					else if (rsmd.getColumnTypeName(i).equals("VARCHAR"))
						System.out.print(rSet.getString(i) + " ");
					else if (rsmd.getColumnTypeName(i).equals("DATE"))
						System.out.print(rSet.getDate(i) + " ");
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (rSet != null) {
					rSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
