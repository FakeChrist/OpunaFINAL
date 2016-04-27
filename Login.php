<?php
$con=mysqli_connect("", "", "", "");

$Username = $_POST["Username"];
$Password = $_POST["Password"];

$statement = mysqli_prepare($con, "SELECT * FROM User_info WHERE Username = ? AND Password = ?");
mysqli_stmt_bind_param($statement, "ss", $Username, $Password);
mysqli_stmt_execute($statement);

mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $User_id, $Firstname, $Lastname, $Age, $School, $Stype, $Email, $Username, $Password, $Join_Date, $Last_Login);

$response = array();
$response["success"] = false;


while(mysqli_stmt_fetch($statement)){
	
$response["success"] = true;
$response["Firstname"] = $Firstname;
$response["Lastname"] = $Lastname;
$response["Age"] = $Age;
$response["School"] = $School;
$response["Stype"] = $Stype;
$response["Email"] = $Email;
$response["Username"] = $Username;
$response["Password"] = $Password;
}

echo json_encode($response);




?>