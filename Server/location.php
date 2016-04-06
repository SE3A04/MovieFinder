<?php
$servername = "localhost";
$username = "root";
$password = "bitnami";
$dbname = "experts";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("");
}

$sql = "SELECT Location FROM locations WHERE ID=" . $_REQUEST["ID"];
$res = $conn->query($sql);

$prefix = "";
if ($res->num_rows > 0) {
	while($row = $res->fetch_assoc()) {
		echo $prefix . $row["Location"];
		$prefix = "@";
	}
}

$conn->close();
?> 
