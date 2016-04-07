<?php

	class ExpertControl2 {
		public $servername ;
		public $username ;
		public $password ;
		public $dbname ;
		public $conn ;
		
		public function ExpertControl2() {
			$this->servername = "localhost" ;
			$this->username = "root" ;
			$this->password = "bitnami" ;
			$this->dbname = "experts" ;
			$this->conn = new mysqli($this->servername,$this->username,$this->password,$this->dbname) ;

			if ($this->conn->connect_error) {
				die("Connection Failed: " . $this->conn->connect_error) ;
			}
		}

		function getResult($keywordString) {
			$keywords = explode(",",$keywordString) ;
			$conn = new mysqli($this->servername,$this->username,$this->password,$this->dbname) ;
			
			$sql = sprintf("SELECT DISTINCT m.movie_name,
			(SELECT count(*) as cnt from genre m2 WHERE m2.movie_name=m.movie_name AND genre IN ('%s')) as order_col
			FROM genre m
			ORDER BY order_col DESC
			LIMIT 1;",implode("', '", $keywords));

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
