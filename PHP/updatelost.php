<?php
$servername = "localhost";
$username = "id4224320_android";
$password = "12345";
$dbname = "id4224320_android";


$getid= $_REQUEST["id"];
$getwhat = $_REQUEST["whatlost"];
$getwhere = $_REQUEST["wherelost"];
$getdate = $_REQUEST["date"];
$getdes= $_REQUEST["description"];
$getname = $_REQUEST["name"];
$gettel = $_REQUEST["tel"];
$getstatus= $_REQUEST["status"];


// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "UPDATE losts SET whatlost='".$getwhat."',wherelost='".$getwhere."',date='".$getdate."',description='".$getdes."',name='".$getname."',tel='".$gettel."',status='".$getstatus."' WHERE id='".$getid."' ";



if (mysqli_query($conn,$sql)) {
      echo "Values have been update successfully";
   }else{
      echo "0";

   }

$conn->close();
?>