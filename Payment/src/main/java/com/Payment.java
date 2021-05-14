package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {
	
	private Connection connect() {
		Connection con = null;
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rest_api?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
			
		}catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
		}
		
		return con;
	}
	
	public String readPayment() {
		
		String output = ""; 
		 
		try   {    
			  
			  Connection con = connect(); 
		 
			   if (con == null)    {
				   return "Error while connecting to the database.."; 
				   
			   } 
			    
		   // Prepare the html table to be displayed    
		  output = "<table border='1'><tr>"
		  		+ "<th>Name on Card</th>"
		  		+ "<th>Card No</th>"
		  		+ "<th>cvv</th>"
		  		+ "<th>Ex date</th>"
		  		+ "<th>Amount</th>"
		  		+ "<th>Update</th>"
		  		+ "<th>Remove</th></tr>"; 
		 
		   String query = "select * from payment_management";    
		   Statement stmt = con.createStatement();    
		   ResultSet rs = stmt.executeQuery(query); 
		 
		   // iterate through the rows in the result set    
		   while (rs.next()) {     
			   String pId = Integer.toString(rs.getInt("payment_id"));     
			   String cname = rs.getString("name_on_card"); 
			   String cardNo = rs.getString("card_number"); 
			   String cvv = rs.getString("cvv"); 
			   String expday= rs.getString("exp_day");
			   String amout = rs.getString("amout");     
		 
		    // Add into the html table
			   output += "<tr><td><input id='hiddenIDUpdate' name='hiddenIDUpdate' type='hidden' value='" + pId + "'>"+ cname + "</td>"; 
			   output += "<td>" + cardNo + "</td>"; 
			   output += "<td>" + cvv + "</td>"; 
			   output += "<td>" + expday + "</td>"; 
			   output += "<td>" + amout + "</td>";     
		 
		    // buttons     
			   output += "<td><input name='btnUpdate' type='button'"
			   		+ "value='Update' class='btnUpdate btn btn-secondary'></td>" 
			   		+ "<td><input name='btnRemove' type='button' value='Remove'"
			   		+ "class='btnRemove btn btn-danger' data-pid='"      
			   		+ pId +"'></td></tr>";    
		   } 
		 
		   con.close(); 
		 
		   // Complete the html table    
		   output += "</table>";   
		   
		}catch (Exception e)   {    
			output = "Error while reading the Users.";    
			System.err.println(e.getMessage());   
		} 
		 
		  return output; 
		  
	}
	
	public String insertPayment(String name_on_card, String card_number, String cvv, String exp_day, String amout) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			if(con == null) {
				return "Error while conncting to the database for inserting..";
				
			}
			
			String query = "INSERT INTO payment_management(`name_on_card`, `card_number`, `cvv`, `exp_day`, `amout`) VALUES (?,?,?,?,?)";
			
			
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1, name_on_card);
			ps.setString(2, card_number);
			ps.setString(3, cvv);
			ps.setString(4, exp_day);
			ps.setString(5, amout);
			
			ps.executeUpdate();
			ps.close();
			
			String newPayment = readPayment();
			output = "{\"status\":\"success\", \"data\": \""+newPayment+"\"}";
		//	output = "Insert Successfully";
			
		}catch (Exception e) {
			// TODO: handle exception
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the users.\"}";
			System.out.println(e);
		}
		
		return output;
		
	}
	
	public String updatePayment(String ID, String name, String card_number, String cvv, String exp_day, String amout)  {   
		
		String output = ""; 
	 
		try   {    
			Connection con = connect(); 
	 
	   if (con == null)    {
		   return "Error while connecting to the database for updating."; 
	   } 
	 
	   // create a prepared statement    
	   String query = "UPDATE payment_management SET name_on_card=?, card_number=?, cvv=?, exp_day=?, amout=?"
	   		+ "WHERE payment_id=?"; 
	 
	   PreparedStatement ps = con.prepareStatement(query); 
	 
	   // binding values    
	    ps.setString(1, name);
		ps.setString(2, card_number);
		ps.setString(3, cvv);
		ps.setString(4, exp_day	);
		ps.setString(5, amout);       
	   ps.setInt(6, Integer.parseInt(ID)); 
	 
	   // execute the statement    
	   ps.execute();    
	   con.close(); 
	 
	   String newPayment = readPayment();
	   output = "{\"status\":\"success\", \"data\": \""+newPayment+"\"}";
	//   output = "Updated successfully";   
	   
		}catch (Exception e)   {    
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the user.\"}";    
			System.err.println(e.getMessage());   
		} 
	 
	  return output;  
	  
	}
	
	public String deletePayment(String pId)  {   
		
		String output = ""; 
	 
	  try   {    
		  Connection con = connect(); 
	 
	   if (con == null)    {
		   return "Error while connecting to the database for deleting."; 
	   } 
	 
	   // create a prepared statement    
	   String query = "delete from payment_management where payment_id=?"; 
	 
	   PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
	   // binding values    
	   preparedStmt.setInt(1, Integer.parseInt(pId)); 
	 
	   // execute the statement    
	   preparedStmt.execute();    
	   con.close(); 
	 
	   String newPayment = readPayment();
	   output = "{\"status\":\"success\", \"data\": \""+newPayment+"\"}";  
	   
	  }catch (Exception e)   {    
		  output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";    
		  System.err.println(e.getMessage());   
	  } 
	 
	  return output;  
	  
	}

}
