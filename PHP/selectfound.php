<?php
		$servername = "localhost";
		$username1 = "id4224320_android";
		$password1 = "12345";
		$dbname = "id4224320_android";

		$getid= $_REQUEST['id'];

$conn = new mysqli($servername, $username1, $password1, $dbname);
		if ($conn->connect_error) {
    		die("Connection failed: " . $conn->connect_error);
		} 
		
$sql = "select * from founds where id ='".$getid."' ";

		$result = $conn->query($sql);
		if ($result->num_rows > 0) {
			 while($row = $result->fetch_assoc()) {	
			 	echo $row["id"].",".$row["whatfound"].",".$row["wherefound"].",".$row["date"].",".$row["description"].",".$row["name"].",".$row["tel"].",".$row["status"].",".$row["secret"];
			}
		}else{
			echo "0";
		}

	?>