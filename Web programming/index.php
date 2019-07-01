<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang ="pl">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<link rel = "Stylesheet" href = "style.css" type = "text/css"/>
		<link rel = "Stylesheet" href = "AnimatedCalculator/style.css" type = "text/css"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<?php require 'Logowanie/plik.php';?>
	<title>Projekt</title>
</head>
	<body>

		<!--DATABASE PART-->
			
			<div style='display:inline' class='imieZero'>Imię</div>
			<div style='display:inline'  class ='nazwiskoZero'>Nazwisko</div>	


		<?php
			
			session_start();
				
			require '../../.htpasswd/DataBaseAccess.php';
		
			$selectQuery = "select Id,Imie,Nazwisko from ".$dbName."";

			$connection = mysqli_connect($server,$user,$password,$db)
			or die('Brak polaczenia z serwerem MySQL');

			mysqli_select_db($connection,$dbName);


			$wynik=mysqli_query($connection,$selectQuery); 
			while($linia=mysqli_fetch_array($wynik))
			{	 
			echo "<hr>";

			//Delete Person
			echo "<form method='POST' action='executeCommandDelete.php'>";
			
			//Delete data
			echo "<div id ='data'>";
			$imieDel = $linia['Imie'];
			$nazwiskoDel = $linia['Nazwisko'];
			$idDel = $linia['Id'];
			echo "</div>";

			//Edit data
			$imie = $linia['Imie'];
			$nazwisko = $linia['Nazwisko'];
			$id = $linia['Id'];

			echo "<div class='MainAll'>";
			echo "<div id ='divMain' class='divmain'>";

			echo "<div class ='imie'>";
			echo $imieDel;
			echo "</div>";

			echo "<div class ='nazwisko'>";
			echo $nazwiskoDel;
			echo "</div>";

			echo "</div>";
			
			//Change id of div
			echo "<script type=\"text/javascript\">
			document.getElementById('divMain').setAttribute('id', 'divMain$idDel');
            </script>";	


			echo "<input type='hidden' name='idDel' value='$idDel'/>";
			echo "<input type='hidden' name='imieDel' value='$imieDel'/>";
			echo "<input type='hidden' name='nazwiskoDel' value='$nazwiskoDel'/>";

			echo "<div id ='DeleteBtn' class='deleteBtn'>";
			echo "<input type='submit' name='DeleteBtn' value='Usun'/>";
			echo "</div>";

			echo "<script type=\"text/javascript\">
			document.getElementById('DeleteBtn').setAttribute('id', 'DeleteBtn$idDel');
            </script>";	

			echo "</form>";

				
			//Edit Person
			echo "<form method='POST' action='executeCommandEdit.php'>";

			//Change id of div
			echo "<script type=\"text/javascript\">
			document.getElementById('divMain').setAttribute('id', 'divMain$id');
            </script>";	
           	

			echo "<input type='hidden' name='idOld' value='$id'/>";
			echo "<input type='hidden' name='imieOld' value='$imie'/>";
			echo "<input type='hidden' name='nazwiskoOld' value='$nazwisko'/>";

			echo "<input type='hidden' class='imie2' name='imieNew' value='$imie'/>";
			echo "<input type='hidden' class='nazwisko2' name='nazwiskoNew' value='$nazwisko'/>";
			
			echo "<input type='hidden' name='EditBtn' value='Zatwierdź edytowanie'/>";
			
			echo "<div id ='EditBtn' >";
			echo "<input type='button' value='Edytuj' onclick='Edytuj(imieNew,nazwiskoNew,EditBtn,this,idOld)'/>";
			echo "</div>";
			echo "</div>";

			echo "<script type=\"text/javascript\">
            function Edytuj(senderImie,senderNazwisko,senderEditBtn,sender,senderidOld)
            {
            	ID2 = 'DeleteBtn'+senderidOld.value;
            	document.getElementById(ID2).style.display = 'none';
            	
            	ID = 'divMain'+ senderidOld.value;
            	document.getElementById(ID).textContent='';
            	senderImie.type = 'text';
            	senderNazwisko.type = 'text';
            	senderEditBtn.type = 'submit';

            	sender.type = 'hidden';
            }
            </script>";

			echo "</form>";

			echo "\n";
			}
			
			echo "<hr>";
			
			mysqli_close($connection);
		?>

		<div id='temp'></div>

		<!--Adding-->
		<form method="POST" action="executeCommandAdd.php">
		<input type="text" class='imie2' name="imieDodaj"/>
		<input type="text" class='nazwisko2' name="nazwiskoDodaj"/>
		<input type="button" value = "Dodaj" onclick="check(imieDodaj,nazwiskoDodaj,this)"/>
		</form>

		<script>
            function check(senderImie,senderNazwisko,Btn)
            {
            	obj= senderImie;
            	txt = obj.value;
            	
            	obj2 = senderNazwisko;
            	txt2 = obj2.value;

            	if (txt.length==0 || txt2.length==0)
            	document.getElementById("temp").innerHTML = "By dodać do bazy danych musisz uzupełnić wszystkie pola!";
            	else
            	Btn.type='submit';
            }
            </script>

    <!-- CALCULATOR PART -->

    <script src="AnimatedCalculator/calc_jquery.js" type="text/javascript">WRITE_ON_DISPLAY();</script>

    <script src="AnimatedCalculator/kalk.js" type="text/javascript">WRITE_ON_DISPLAY();</script>

    <button id="wysun"></button>

	<div class = "calculator" id="calculator">

	<div class="wysw"><input type="text" id="WYS" value=""/></div>

	<div class = "rows">

	<div class = "row1">
	<button class="but"  onclick="WRITE_ON_DISPLAY(this)" value='7'/>7
	<button class="but" onclick="WRITE_ON_DISPLAY(this)" value='8'/>8
	<button class="but"  onclick="WRITE_ON_DISPLAY(this)" value='9'/>9
	<button class="but"  onclick="WRITE_ON_DISPLAY(this)" value='/'/>/
	</div>

	<div class="row2">
	<button class="but"  onclick="WRITE_ON_DISPLAY(this)" value='4'/>4
	<button class="but" onclick="WRITE_ON_DISPLAY(this)" value='5'/>5
	<button class="but"  onclick="WRITE_ON_DISPLAY(this)" value='6'/>6
	<button class="but"  onclick="WRITE_ON_DISPLAY(this)" value='*'/>*
	</div>

	<div class="row3">
	<button class="but"  onclick="WRITE_ON_DISPLAY(this)" value='1'/>1
	<button class="but"  onclick="WRITE_ON_DISPLAY(this)" value='2'/>2
	<button class="but"  onclick="WRITE_ON_DISPLAY(this)" value='3'/>3
	<button class="but"  onclick="WRITE_ON_DISPLAY(this)" value='-'/>-
	</div>

	<div class = "row4">
	<button class="but"  onclick="WRITE_ON_DISPLAY(this)" value='0'/>0
	<button class="but"  onclick="WRITE_ON_DISPLAY(this)" value='C'/>C
	<button class="but"  onclick="WRITE_ON_DISPLAY(this)" value='.'/>.
	<button class="but"  onclick="WRITE_ON_DISPLAY(this)" value='+'/>+
	</div>

	<div class = "row5">
	<button class="but result_butt"  onclick="WRITE_ON_DISPLAY(this)" value='='/>=
	</div>

	</div>
	
	</div>

		<div class = "end">
		<br/><a href="http://test1234.hmcloud.pl/Projekt8/Logowanie/loguj.php">Wyloguj</a>
	<p>
		<a href="http://validator.w3.org/check?uri=referer"><img
		src="http://www.w3.org/Icons/valid-xhtml10" alt="Valid XHTML 1.0 Transitional" height="31" width="88" /></a>
     </p>
		</div>	
	
</body>

</html>