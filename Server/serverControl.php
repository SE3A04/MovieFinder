<?php
error_reporting(E_ALL);
ini_set('display_errors', 'On');
// require expert php files
require 'ExpertControl1.php';
require 'ExpertControl2.php';
require 'ExpertControl3.php';
// expert decision weight
$expertWeight1=.7;
$expertWeight2=.1;
$expertWeight3=.2;
// POST request from client is segmented
$queryE1=$_POST['E1'];
$queryE2=$_POST['E2'];
$queryE3=$_POST['E3'];
// instantiate expert classes
$expertControl1=new expertControl1;
$expertControl2=new expertControl2;
$expertControl3=new expertControl3;
// query experts
$resultE1 = $expertControl1->getResult($queryE1);
$resultE2 = $expertControl2->getResult($queryE2);
$resultE3 = $expertControl3->getResult($queryE3);
// deicde final result from expert weight and value of the returned query
if ($expertWeight1>($expertWeight2+$expertWeight3)){
	$decision=$resultE1;
	$otherResults=$resultE2."&".$resultE3;
}
elseif ($expertWeight2>($expertWeight1+$expertWeight3)){
	$decision=$resultE2;
	$otherResults=$resultE1."&".$resultE3;
}
elseif ($expertWeight3>($expertWeight1+$expertWeight2)){
	$decision=$resultE3;
	$otherResults=$resultE1."&".$resultE2;
}
else {
	if ($resultE1==$resultE2) {
		$resultWeight=$expertWeight1+$expertWeight2;
		if ($resultWeight>$expertWeight3) {
			$decision=$resultE1;
			$otherResults=$resultE2."&".$resultE3;
		}
		else {
			$decision=$resultE3;
			$otherResults=$resultE1."&".$resultE2;
		}
	}
	elseif ($resultE1==$resultE3) {
		$resultWeight=$expertWeight1+$expertWeight3;
		if ($resultWeight>$expertWeight2) {
			$decision=$resultE1;
			$otherResults=$resultE2."&".$resultE3;
		}
		else {
			$decision=$resultE2;
			$otherResults=$resultE1."&".$resultE3;
		}
	}
	elseif ($resultE2==$resultE3) {
		$resultWeight=$expertWeight2+$expertWeight3;
		if ($resultWeight>$expertWeight1) {
			$decision=$resultE2;
			$otherResults=$resultE1."&".$resultE3;
		}
		else {
			$decision=$resultE1;
			$otherResults=$resultE2."&".$resultE3;
		}
	}
	else {
		if ($expertWeight1>$expertWeight2&&$expertWeight1>$expertWeight3) {
			$decision=$resultE1;
			$otherResults=$resultE2."&".$resultE3;
		}
		elseif ($expertWeight2>$expertWeight1&&$expertWeight3) {
			$decision=$resultE2;
			$otherResults=$resultE1."&".$resultE3;
		}
		elseif ($expertWeight3>$expertWeight1&&$expertWeight3>$expertWeight2) {
			$decision=$resultE3;
			$otherResults=$resultE1."&".$resultE2;
		}
	}
}
// return final result
$finalResult = $decision."%".$otherResults;
echo $finalResult;
?>

