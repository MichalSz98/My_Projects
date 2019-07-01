	var calc = "#calculator",wysunBtn = "#wysun";
	
	$(document).ready(function(){
    $(".but").click(function(){
  	$(this).fadeOut(50);
  	$(this).fadeIn(200);
	}); 

    $(calc).css({left:-290,top:"26%",position:'absolute'});

    $(wysunBtn).css('z-index',1);
    $(wysunBtn).css('opacity',0);

    $(wysunBtn).click(function(){
        
    	var $calc2=$(calculator);

    	if ($calc2.hasClass("clicked-once"))
    	{
    		$calc2.removeClass("clicked-once");

    		$calc2.animate({left:"-=1083",opacity:"show"},1500);

    		$(wysunBtn).css('opacity',0);
    		$(wysunBtn).css({left:0,height:"400px",width:"2px",top:"26%",position:'absolute'});
    	}
    	else
    	{
    		$calc2.addClass("clicked-once");

    		$calc2.animate({left:"+=1083",opacity:"show"},1500,function(){
    		
    		$(wysunBtn).css('opacity',1);
    		$(wysunBtn).css({left:1029,top:"24%",height: 20,width:50,position:'absolute'});
   			$(wysunBtn).hide();
   			$(wysunBtn).fadeIn("fast");
    		});	
    	}
    });
	});
