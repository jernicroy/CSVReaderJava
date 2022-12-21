package com.main.javaPostgreSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;

public class CSVreader {

	public static void main(String[] args) throws IOException {
		
		Connection conn=null;
		String dbURL = "jdbc:postgresql://localhost:5432/userDetails";
		String dataUrl = "D:\\Jernic\\Asir Tech\\Task List\\PostgreSQL\\userDetails.csv";
//		List<String[]> line = new ArrayList<>();
		String[] line=null;
		CSVReader reader= null;
		try {
			String sql= "INSERT INTO user_details(reg_no, first_name, last_name, location) VALUES(?, ?, ?, ?)";
			
			conn = DriverManager.getConnection(dbURL,"postgres","Roy@123");
			System.out.println("Connection Succeed");
			reader = new CSVReader(new FileReader(dataUrl));
			PreparedStatement statement= conn.prepareStatement(sql);
			
			while((line= reader.readNext())!= null) {
				int reg= Integer.parseInt(line[0]);
				statement.setInt(1, reg);
				statement.setString(2, line[1]);
				statement.setString(3, line[2]);
				statement.setString(4, line[3]);
				statement.executeUpdate();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
