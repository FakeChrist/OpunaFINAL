<?php
 
if($_SERVER['REQUEST_METHOD']=='POST'){
				$image = $_POST['image'];
                $Title = $_POST['Title'];
				$Description = $_POST['Description'];
				

require_once('DatabaseConnect.php');
$sql ="SELECT id FROM Photos ORDER BY id ASC";
$res = mysqli_query($con,$sql);
$id = 0;
while($row = mysqli_fetch_array($res)){
$id = $row['id'];
}
$path = "uploads/$id.png"; //Possibly change to match file path of Subject ID


$actualpath = "http://opuna.co.uk/$path";

$sql = "INSERT INTO Photos (image,Title,Description) VALUES ('$actualpath','$Title', '$Description')";
if(mysqli_query($con,$sql)){
file_put_contents($path,base64_decode($image));
echo "Successfully Uploaded";
}
mysqli_close($con);
}else{
echo "Error";
}
?>