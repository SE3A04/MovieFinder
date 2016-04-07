<?php
error_reporting(E_ALL) ;
ini_set('display_errors','On');
	class ExpertControl1 {
		public $servername ;
		public $username ;
		public $password ;
		public $dbname ;
		public $conn ;
		
		public function ExpertControl1() {
			$this->servername = "localhost" ;
			$this->username = "root" ;
			$this->password = "bitnami" ;
			$this->dbname = "experts" ;
			$this->conn = new mysqli($this->servername,$this->username,$this->password,$this->dbname) ;

			if ($this->conn->connect_error) {
				die("Connection Failed: " . $this->conn->connect_error) ;
			}
		}


		public function getResult($nameString) {
			$names = explode(",",$nameString) ;
			//$res = ($conn)->query($sql) ;
			$conn = new mysqli($this->servername,$this->username,$this->password,$this->dbname) ;
			
			$sql = sprintf("SELECT DISTINCT m.movie_name,
			(SELECT count(*) as cnt from character_members m2 WHERE m2.movie_name=m.movie_name AND character_members IN ('%s')) as order_col
			FROM character_members m
			ORDER BY order_col DESC
			LIMIT 1;",implode("', '", $names));

			$stmt = $conn->prepare($sql);
			
			$stmt->execute() ;
			$stmt->bind_result($movieName,$weight);
			
			if ($stmt->fetch()) {
				if ($weight>0) {return $movieName ; }
				else { return 'No Movie Found' ;}
			}
			
			$stmt->close() ;
			$conn->close() ;
		}

	}

?>
