	function CheckSend(sender)
	{
		obj = document.getElementById("log");
		txt = obj.value;
		obj2 = document.getElementById("pass");
		txt2 = obj2.value;
		
		if (txt!="" && txt2!="")
		{
			sender.type = "submit";
		}
		else
		{
			sender.type = "button";
			document.getElementById("other").style.marginTop = "30px";
			document.getElementById("other").style.fontFamily = "Arial";
			document.getElementById("other").style.textAlign = "center";
			document.getElementById("other").style.color = 'white';
			document.getElementById("other").style.fontSize = '13pt';
			document.getElementById("other").textContent = "Uzupełnij dane!";	
		}
	}