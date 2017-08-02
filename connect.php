<?php
	$host = '192.168.11.9';
	$user = 'root';
	$password = 'server';
	$database = 'db_coba';
	
	$connect = mysqli_connect($host,$user,$password,$database);
	
	if(!$connect){
		echo 'Koneksi database gagal!';
	}
?>