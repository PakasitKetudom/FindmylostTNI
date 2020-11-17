<?php
$servername = "localhost";
$username = "id4224320_android";
$password = "12345";
$dbname = "id4224320_android";


    $getwhat = $_REQUEST["whatlost"];
    $getwhere = $_REQUEST["wherelost"];
    $getdate = $_REQUEST["date"];
    $getdes= $_REQUEST["description"];
    $getname = $_REQUEST["name"];
    $gettel = $_REQUEST["tel"];
    $getsecret = $_REQUEST["secret"];


    

$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

	$sql = "INSERT INTO losts (whatlost,wherelost,date,description,name,tel,secret,status)
	VALUES ('".$getwhat."', '".$getwhere."', '".$getdate."', '".$getdes."', '".$getname."', '".$gettel."', '".$getsecret."','ยังไม่ได้คืน')";

if (mysqli_query($conn,$sql)) {


    	echo "New record created successfully";
	} else {
    	echo "Error";
	}

    $conn->close();
?>