<?php
		$servername = "localhost";
		$username1 = "id4224320_android";
		$password1 = "12345";
		$dbname = "id4224320_android";

		//$username = $_REQUEST['username'];

$conn = new mysqli($servername, $username1, $password1, $dbname);
		if ($conn->connect_error) {
    		die("Connection failed: " . $conn->connect_error);
		} 
		
$sql = "select * from losts ORDER BY id DESC";

		$result = $conn->query($sql);
		if ($result->num_rows > 0) {
			 while($row = $result->fetch_assoc()) {	
			 	echo $row["id"].",".$row["whatlost"].",".$row["date"].",".$row["status"].";";
			}
			//$_SESSION['user'] = $username;
		}else{
			echo "0";
		}

	?>