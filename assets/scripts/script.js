  $( document ).ready(function() {
    $("#navi-collapse>ul>li>a:contains('Talent')").focus();
    setTimeout(function(){
        $('.gig-conversation').perfectScrollbar();
    }, 200);
    

    $(".profile-name-holder").show();   
    $(".Details-holder").hide();
    $(".media-left").hide();
    $(".media-body").hide();


    $("#view-names").click(function() {
        $(".profile-name-holder").show();   
        $(".Details-holder").hide();
        $(".media-left").hide();
        $(".media-body").hide();
        $('.gig-conversation').perfectScrollbar('update');
    });

    $("#view-details").click(function() {

        $(".profile-name-holder").show();   
        $(".Details-holder").show();
        $(".media-left").hide();
        $(".media-body").hide();
        setTimeout(function(){
            $('.gig-conversation').perfectScrollbar();
        }, 200);
        $('.gig-conversation').perfectScrollbar('update');
    });
    $("#view-description").click(function() {
        $(".profile-name-holder").show();   
        $(".Details-holder").show();
        $(".profile-description").show();
        $(".media-left").show();
        $(".media-body").show();
        $('.gig-conversation').perfectScrollbar('update');
    }); 
    $(".profile-action-buttons").hide();
    $('.profile-description').hover(function() {

        $(this).find(".profile-action-buttons").show();
    },
    function(){
        $(this).find(".profile-action-buttons").hide();
    }
    );
});
 
      
  $(function () {

/*Splitter code*/
        splitter = $('.split').touchSplit({leftMin:"0%", rightMin:"100%", thickness: "10px", dock:"left"})
          
/*perfect scrollbar*/
    
        $('#slct-scroll-container').perfectScrollbar();

/* tokanizable*/
    
        $('#tokenize').tokenize();
        $('#tokenizechannel').tokenize();
    });




