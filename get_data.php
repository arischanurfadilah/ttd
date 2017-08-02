<?php
	$response = array();
	require_once "./connect.php";
	
		
			$query = 'SELECT * FROM tb_coba';
			$result = mysqli_query($connect, $query);
			
			if($result){
				if(mysqli_num_rows($result)){
					$response["tb_coba"] = array();

					while($row = mysqli_fetch_assoc($result)){
						$product = array();
						$product["id"] = $row["id"];
						$product["nama"] = $row["nama"];

						array_push($response["tb_coba"], $product);
					}
					$response["success"] = 1;
					echo json_encode($response);
				} else {
					$response["success"] = 0;
					$response["message"] = "No products found";
					echo json_encode($response);
}
			}
			
		?>	