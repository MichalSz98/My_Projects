<?php
	session_start();

	$deleteQuery = "delete from ".$_SESSION["dbName"]." where Id='".$_POST["idDel"]."'";
	
	$connection = mysqli_connect($_SESSION["server"],$_SESSION["user"],$_SESSION["password"],$_SESSION["db"])
	or die('Brak polaczenia z serwerem MySQL');

	mysqli_select_db($connection,$_SESSION["dbName"]);

    mysqli_query($connection,$deleteQuery);

    mysqli_close($connection);
    
    header("Location: http://test1234.hmcloud.pl/Projekt8/index.php");
?>
