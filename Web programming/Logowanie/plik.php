		<?php
		 session_start();
		 if (isset($_SESSION["czy_zalogowany"]))
		 {
		 	$czas_teraz = time();
		 	if ($czas_teraz > $_SESSION['czas'])
		 	{	 	
		 	session_destroy();
    		echo "Sesja wygasła!<br/><a href='http://test1234.hmcloud.pl/Projekt8/index.php'>Zaloguj się ponownie.</a><br/><br/>";
		 	}
		 }
		 else
		 	{		
		 		$_SESSION['redirectURL'] = $_SERVER['REQUEST_URI'];
				echo $_SERVER['REQUEST_URI'];
		 		header("Location: Logowanie/loguj.php");
		 	}
		 	
		?>