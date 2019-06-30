<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang ="pl">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	</script>
	<title>Zadanie PHP2</title>

</head>
	<body>
					<div>
					<?php
						session_start();
						if ((isset($_POST["login"]) && !empty($_POST["login"])) && (isset($_POST["haslo"]) && !empty($_POST["haslo"])))
    					{
    						if ($_POST["login"]==="github" && $_POST["haslo"]==="github")
    						{
									$_SESSION['czy_zalogowany2'] = 1;
									$_SESSION['start2'] = time();
									$_SESSION['czas2'] = $_SESSION['start2'] + (1 * 60);
									header("Location: ../index.php");
    						}
    						else
    							{
    								echo "Nieprawidłowy login lub hasło! <br/><br/><a href='http://test1234.hmcloud.pl/Projekt1/index.php'>POWRÓT</a>";
    							}
    					}
					?>
				</div>
	</body>
</html>