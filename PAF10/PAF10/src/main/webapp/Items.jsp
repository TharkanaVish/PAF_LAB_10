<%@ page import="com.paf10.Item"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Items Management</title>
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Items.js"></script>
<link rel="stylesheet" href="views/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">


				<h1>Items Management</h1>
				<form method='post' action='Items.jsp' id='formItem' name='formItem'>
					Item code: <input id='itemCode' name='itemCode' type='text' class='form-control col-md-3'><br> 
					Item name: <input id='itemName' name='itemName' type='text' class='form-control col-md-3'><br> 
					Item price: <input id='itemPrice' name='itemPrice' type='text' class='form-control col-md-3'><br> 
					Item description: <input id='itemDesc' name='itemDesc' type='text' class='form-control col-md-3'><br> 
					<input id='btnSave' name='btnSave' type='button' value='Save' class='btn btn-primary'> 
					<input type='hidden' id='hidItemIDSave' name='hidItemIDSave' value=''>
				</form>

				<br>

				<div id='alertSuccess' name='alertSuccess' class='alert alert-success'></div>
				<div id='alertError' name='alertError' class='alert alert-danger'></div>

				<br>
				<div id="divItemsGrid">
				<%
				Item itemObjRead = new Item();
				out.print(itemObjRead.readItems());
				%>
				</div>

			</div>
		</div>
	</div>
</body>
</html>