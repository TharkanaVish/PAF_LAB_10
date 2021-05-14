package com.paf10;
import java.sql.*;

public class Item {

	//Connection
	public Connection connect()
	{
		Connection con = null;

		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/PAF2021",	"root", "");
			//For testing
			System.out.print("Successfully connected");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return con;
	}

	//Insert
	public String insertItem(String code, String name, String price, String desc)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database";
			}

			// create a prepared statement
			String query = " insert into item(`itemID`,`itemCode`,`itemName`,`itemPrice`,`itemDesc`) values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, code);
			preparedStmt.setString(3, name);
			preparedStmt.setDouble(4, Double.parseDouble(price));
			preparedStmt.setString(5, desc); 

			//execute the statement
			preparedStmt.execute();
			con.close();

			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
			
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	//Read
	public String readItems()
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			// Prepare the HTML table to be displayed
			output = "<table  class='table table-dark table-striped'><tr><th>Item Code</th>"
					+"<th>Item Name</th><th>Item Price</th>"
					+ "<th>Item Description</th>"
					+ "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from item";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next())
			{
				String itemID = Integer.toString(rs.getInt("itemID"));
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = Double.toString(rs.getDouble("itemPrice"));
				String itemDesc = rs.getString("itemDesc");

				// Add a row into the HTML table
				output += "<tr><td>" + itemCode + "</td>";
				output += "<td>" + itemName + "</td>";
				output += "<td>" + itemPrice + "</td>"; 
				output += "<td>" + itemDesc + "</td>";
				

				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-itemid='" + itemID + "'></td>"
						+"<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='" + itemID + "'>" + "</td></tr>";
			}

			con.close();

			// Complete the HTML table
			output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	//Update
	public String updateItem(int id, String code, String name, String price, String desc)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database";
			}

			// create a prepared statement
			String query = "update item set `itemCode`=?,`itemName`=?,`itemPrice`=?,`itemDesc`=? where `itemID`=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, code);
			preparedStmt.setString(2, name);
			preparedStmt.setDouble(3, Double.parseDouble(price));
			preparedStmt.setString(4, desc);
			preparedStmt.setInt(5, id);

			//execute the statement
			preparedStmt.executeUpdate();
			con.close();

			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
			
			
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while updating the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	//Delete
	public String removeItem(int id)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database";
			}

			// create a prepared statement
			String query = "delete from item where `itemID`=?;";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, id);

			//execute the statement
			preparedStmt.executeUpdate();
			con.close();

			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	/*
	//View Item
	public String viewItem(int id)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}

			String query = "select * from item where `itemID`=?;";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, id);
			ResultSet rs = preparedStmt.executeQuery();

			// iterate through the rows in the result set
			if (rs.next())
			{
				String itemID = Integer.toString(rs.getInt("itemID"));
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = Double.toString(rs.getDouble("itemPrice"));
				String itemDesc = rs.getString("itemDesc");

				output += "<form id='formItem' name='formItem' method='post' action='Items.jsp'> "
						+ "Item code: <input id='itemCode' name='itemCode' type='text' value='"+ itemCode +"' class='form-control col-md-3'><br>" 
						+ "Item name: <input id='itemName' name='itemName' type='text' value='"+ itemName +"' class='form-control col-md-3'><br> "
						+ "Item price: <input id=itemPrice' name='itemPrice' type='text' value='"+ itemPrice +"' class='form-control col-md-3'><br> "
						+ "Item description: <input id=itemDesc' name='itemDesc' type='text' value='"+ itemDesc +"' class='form-control col-md-3'><br> "
						+ "<input id='action' name='action' value='update' type='hidden'> "
						+ "<input id='itemID' name='itemID' value='"+ itemID +"' type='hidden'> "
						+ "<input id='btnSave' name='btnSave' type='button' value='Update Item "+ id +"' class='btn btn-warning'> "
						+ "<input type='hidden' id='hidItemIDSave' name='hidItemIDSave' value=''>"
						+ "</form> <br>"
						+ "<a href='Items.jsp' id='cancelUpdate' name='cancelUpdate'>Cancel Updating</a>";
			}

			con.close();
		}
		catch (Exception e)
		{
			output = "Error while reading the selected item.\nGo back to <a href='items.jsp'>Items.jsp</a>";
			System.err.println(e.getMessage());
		}
		return output;
	}*/
}
