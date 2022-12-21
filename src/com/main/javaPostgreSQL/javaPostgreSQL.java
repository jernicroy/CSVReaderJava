package com.main.javaPostgreSQL;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class javaPostgreSQL {

	public static void main(String[] args) throws IOException {
		
		Connection conn=null;
		String dbURL = "jdbc:postgresql://localhost:5432/userDetails";
		String dataUrl = "D:\\Jernic\\Asir Tech\\Task List\\PostgreSQL\\userDetails.csv";
		int batchsize = 5;
		
		try {
			conn=DriverManager.getConnection(dbURL,"postgres","Roy@123");
			conn.setAutoCommit(false);
			
			int count = 0;
			
			System.out.println("Connection Successful");

			String sql= "INSERT INTO user_details(reg_no,first_name,last_name,location) VALUES(?, ?, ?, ?)";
			PreparedStatement pstate = conn.prepareStatement(sql);
			
//			BufferedReader br = new BufferedReader(new FileReader(dataUrl));
			
//			FileInputStream fis = new FileInputStream(dataUrl); 
//			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
//			CSVReader reader = new CSVReader(new FileReader(dataUrl));
			
			
			String lineText = null;
			BufferedReader br= new BufferedReader(new FileReader(dataUrl));
			while((lineText= br.readLine()) != null) {
				
				String[] data= lineText.split(",");
				int reg_no= Integer.parseInt(data[0]);
				String first_name= data[1];
				String last_name=data[2];
				String location= data[3];
			
				pstate.setInt(1, reg_no);
				pstate.setString(2, first_name);
				pstate.setString(3, last_name);
				pstate.setString(4, location);
				
				pstate.addBatch();			
			
				if(count % batchsize == 0) {
					pstate.executeBatch();
				}
				
			}
			
			br.close();
			
			
//			String sql= "DELETE FROM user_details";
			
//			Statement state=conn.createStatement();
			
//			int rows = state.executeUpdate(sql);
//			
//			if(rows>0) {
//				System.out.println("inserted one row ");
//			}
			
			pstate.executeBatch();
			
			conn.commit();
			conn.close();
		}
		catch(SQLException e) {
			System.out.println("Error in Connection");
			e.printStackTrace();
		}

	}

}
