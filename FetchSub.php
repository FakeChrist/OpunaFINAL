<?php 

	$sql = "SELECT * FROM Subjects";
	
	require_once('DB.php');
	
	$r = mysqli_query($con,$sql);
	
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		array_push($result,array(
			'Sub_id'=>$row['Sub_id'],
			'Subject_Name'=>$row['Subject_Name'],
			'File_Path'=>$row['File_Path']
			
		));
	}
	
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);