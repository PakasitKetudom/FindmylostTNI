<?php
		$servername = "localhost";
		$username1 = "id4224320_android";
		$password1 = "12345";
		$dbname = "id4224320_android";

		//$username = $_REQUEST['username'];
		//$action = $_REQUEST['action']

$conn = new mysqli($servername, $username1, $password1, $dbname);
		if ($conn->connect_error) {
    		die("Connection failed: " . $conn->connect_error);
		} 

		switch ($_REQUEST['action']) {
			case 'no':
				$sql = "select * from losts where status = 'ยังไม่ได้คืน' ORDER BY id DESC";

				break;
			
			case 'yes':
				$sql = "select * from losts where status = 'ได้คืนแล้ว' ORDER BY id DESC";

				break;
		}



		$result = $conn->query($sql);
		if ($result->num_rows > 0) {
			 while($row = $result->fetch_assoc()) {	
			 	echo $row["id"].",".$row["whatlost"].",".$row["date"].",".$row["status"].";";
			}

		}else{
			echo "0";
		}

	?>