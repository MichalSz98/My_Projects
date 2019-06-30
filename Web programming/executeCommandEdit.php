<?php
	session_start();

	$editQuery = "update ".$_SESSION["dbName"]." set imie='".$_POST["imieNew"]."' ,nazwisko='".$_POST["nazwiskoNew"]."' where imie='".$_POST["imieOld"]."' and nazwisko='".$_POST["nazwiskoOld"]."' and id='".$_POST["idOld"]."'";
	
	$connection = mysqli_connect($_SESSION["server"],$_SESSION["user"],$_SESSION["password"],$_SESSION["db"])
	or die('Brak polaczenia z serwerem MySQL');

	mysqli_select_db($connection,$_SESSION["dbName"]);

    mysqli_query($connection,$editQuery);

    mysqli_close($connection);
    
    header("Location: http://test1234.hmcloud.pl/Projekt8/index.php");

   	session_destroy();
?>
