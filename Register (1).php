<?php
$con=mysqli_connect("", "", "", "");

	
	$Firstname = $_POST['Firstname'];
	$Lastname = $_POST['Lastname'];
	$Age = $_POST['Age'];
	$School = $_POST['School'];
	$Stype = $_POST["Stype"];
	$Email = $_POST["Email"];
	$Username = $_POST["Username"];
	$Password = $_POST["Password"];
	
	$statement = mysqli_prepare($con, "INSERT INTO User_info (Firstname, Lastname, Age, School, Stype, Email, Username, Password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
	mysqli_stmt_bind_param($statement, "ssisssss", $Firstname, $Lastname, $Age, $School, $Stype, $Email, $Username, $Password);
	mysqli_stmt_execute($statement);
	
	$response = array();
	$response["success"] = true;
	
	echo json_encode($response);
?>