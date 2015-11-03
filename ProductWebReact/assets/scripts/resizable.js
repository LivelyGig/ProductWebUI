
$(document).ready(function(){

$(".resizable").resizable({
    handles: 's',
    stop: function(event, ui) {
        $(this).css("width", '');
   }
});


 
});