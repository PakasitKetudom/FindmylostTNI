<?php
$servername = "localhost";
$username = "id4224320_android";
$password = "12345";
$dbname = "id4224320_android";



$getid = $_REQUEST['id'];

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 


$sql = "DELETE FROM founds WHERE id = '".$getid."' ";


if (mysqli_query($conn,$sql)) {
      echo "Values have been delete successfully";
   }else{
      echo "0";

   }

$conn->close();
?>