<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang ="pl">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<link rel = "Stylesheet" href = "style.css" type = "text/css"/>
	<title>KALKULATOR</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>	
<script>
	var calc = "#calculator",wysunBtn = "#wysun";
	
	$(document).ready(function(){
   
    $(".but").click(function(){
  	$(this).fadeOut(50);
  	$(this).fadeIn(200);
	}); 

	$(calc).css({left:-290,top:"25%",position:'absolute'});

    $(wysunBtn).css('z-index',1);
    $(wysunBtn).css('opacity',0);

    $(wysunBtn).click(function(){
        
    	var $calc2=$(calculator);

    	if ($calc2.hasClass("clicked-once"))
    	{
    		$calc2.removeClass("clicked-once");

    		$calc2.animate({left:"-=1083",opacity:"show"},1500);

    		$(wysunBtn).css('opacity',0);
    		$(wysunBtn).css({left:0,height:"400px",width:"2px",top:"20%",position:'relative'});
    	}
    	else
    	{
    		$calc2.addClass("clicked-once");

    		$calc2.animate({left:"+=1083",opacity:"show"},1500,function(){
    		
    		$(wysunBtn).css('opacity',1);
    		$(wysunBtn).css({left:1029,top:"18%",height: 20,width:50,position:'absolute'});
   			$(wysunBtn).hide();
   			$(wysunBtn).fadeIn("fast");
    		});	
    	}
    });
	});
</script>
	</head>
	<body>

	
	
	<script src="kalk.js" type="text/javascript">
		WRITE_ON_DISPLAY();
	</script>

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

	</body>
</html>
