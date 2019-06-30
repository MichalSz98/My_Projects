		<?php
		 session_start();
		 if (isset($_SESSION["czy_zalogowany2"]))
		 {
		 	$czas_teraz = time();
		 	if ($czas_teraz > $_SESSION['czas2'])
		 	{	 	
		 	session_destroy();
    		echo "<a href='http://test1234.hmcloud.pl/Projekt1/index.php'>Zaloguj się ponownie.</a><br/><br/>";
		 	}
		 }
		 else
		 	{		
		 		header("Location: Logowanie/loguj.html");
		 	}
		 	
		?>