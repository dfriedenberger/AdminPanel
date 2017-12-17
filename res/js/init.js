(function($){
  $(function(){

    $('.button-collapse').sideNav();
    $('.parallax').parallax();
    $('select').material_select();
    //CRUD
	$('button.create').click(function(e) {
	    e.preventDefault();
	    window.location = window.location+"/create";	
	});
	
	
    $('button.post').click(function(e) {
    	
    	e.preventDefault();
	   	
    	$form = $(this).closest('form'); 
    
    	 
    	$.post($form.attr('action'), $form.serialize())
    	.done(function(msg){ 
    		
    		if(msg.state == "Ok")
    		{
    			window.location = msg.redirect;	
    			return;
    		}
    		alert(msg.state+" / "+msg.message)
    	})
        .fail(function(xhr, status, error) {
    		alert(error)
        });
    	
    	
    	
    });
    
    
    
    
  }); // end of document ready
})(jQuery); // end of jQuery name space