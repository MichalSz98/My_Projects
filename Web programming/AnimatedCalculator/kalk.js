		var checker=true,temp;
		function WRITE_ON_DISPLAY(sender, args)
		{	
			if (document.getElementById('WYS').value =='')
				{
					document.getElementById('WYS').value += sender.value;	// Pierwsza
				}
			else 
			{		
				if (checker== true && sender.value !='=')
					document.getElementById('WYS').value += sender.value;		
				else if (((checker==false && sender.value!='+') &&  (sender.value!='-' && sender.value!='*')) && ((sender.value!='/' && sender.value!='=') && sender.value!='.'))		// Kolejne
					document.getElementById('WYS').value += sender.value;		
					checker = Check_The_Last(sender.value);	
			}
			
			var text = document.getElementById('WYS').value;
			var text_splited = text.split("");

			if (text!="" && text.length==1)										//SPRAWDZANIE PIERWSZEJ WPROWADZONEJ
			{ 
				if (((text=="+") || (text=="*" || text=="/")) || (text=="=" || text=="."))
				{
					document.getElementById('WYS').value = '';
				}			
				if (text=='-')
					checker = false;
				if (text=='0')
					checker = true;
			}

			if (sender.value=='C')												// WYCZYŚĆ
				document.getElementById('WYS').value = '';
			
			if (text.length>=1 && sender.value == '=')							//POKAŻ WYNIK
			{

 				if (Check_If_Contain(document.getElementById('WYS').value.split("")))
 					{
 						//alert('jestem w 1');
 						temp = Set_temp(text_splited);	
 						//			alert('temp wynosi '+temp);
 						try {
 					   	eval(document.getElementById('WYS').value+temp); 
						} catch (e) {
    					if (e instanceof SyntaxError) {
       					 document.getElementById('WYS').value = 'BŁĄD SKŁADNI';
    					}
						}

						var label_text=eval(document.getElementById('WYS').value);
						//alert(label_text);
						document.getElementById('WYS').value  = '';
						document.getElementById('WYS').value  = label_text;
					}
 				else if (temp!="" && document.getElementById('WYS').value!="")
 					{
 						//alert('jestem w 2');
 						//alert('temp wynosi '+temp);
 						try {
 					   	eval(document.getElementById('WYS').value+temp); 
						} catch (e) {
    					if (e instanceof SyntaxError) {
       					 document.getElementById('WYS').value  = 'BŁĄD SKŁADNI';
    					}
						}

 						var label_text=eval(document.getElementById('WYS').value+temp);

						//alert(document.getElementById('WYS').value+"" + temp);
						document.getElementById('WYS').value  = '';
						document.getElementById('WYS').value  = label_text;
 					}

			}
		}
		function Check_text_content(sender)
		{
			//alert('Wchodze z '+sender);
			if (((sender=="+" || sender=="-") || (sender=="*" || sender=="/")) || sender==".")
				return false;
			return true;
		}
		function Check_The_Last(sender)
		{
			//alert('Wchodze z '+sender);
			if (((sender=="+" || sender=="-") || (sender=="*" || sender=="/")) || sender==".")
				return false;
			return true;
		}
		function Check_If_Contain(sender)
		{
			for(i=0;i<sender.length;i++)
			{
			if ((sender[i]=="+" || sender[i]=="-") || (sender[i]=="*" || sender[i]=="/"))
			return true;
			}
			return false;
		}
		function Set_temp(sender)
		{
			var result="";
			var counter=0;
			for (j=sender.length-1;j>=0;j--)
			{
					if ((sender[j]!="+" && sender[j]!="-") && (sender[j]!="*" && sender[j]!="/"))
					result+=sender[j];
				else	
					{
						result+=sender[j];
						break;
					}
			}
			return result.split("").reverse().join("");
		}