<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang ="pl">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<link rel = "Stylesheet" href = "styleLoguj.css" type = "text/css"/>
	<script src="scripts.js" type="text/javascript"></script>
	<?php require 'destroySession.php';?>
	<title>Zadanie PHP2</title>

</head>
	<body>
			

		<div id = "other"></div>

		<form method="post" action="sprawdz.php">
			<div class = "main">

				<div class = "content">		


				<div  id = "login">Login:</div>

				<div id = "login2"><input id = "log" class ="txt" type="text" name="login"/></div>

				<div id = "haslo">Hasło:</div>

				<div id ="haslo2"><input id = "pass" class = "txt" type="text" name="haslo"/></div>

				<div><br/><input class="button" type="submit" value="Wyślij" onclick = "CheckSend(this)"/></div>
				</div>
			</div>
			<div class="end"></div>
				
		</form>
	</body>
</html>