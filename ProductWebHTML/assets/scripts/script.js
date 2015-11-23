  $( document ).ready(function() {
    $("#navi-collapse>ul>li>a:contains('Talent')").addClass('active');
    $("#navi-collapse>ul>li>a").click(function(){
    $("#navi-collapse>ul>li>a").removeClass('active');
    $(this).addClass('active');
  });
  setTimeout(function(){
    $('.rslt-gig-conversation').perfectScrollbar({
      suppressScrollX:true,
      wheelPropagation: false
    });
  }, 200);
    
   $(".profile-name-holder").show();   
   $(".rslt-profile-details-holder").hide();
   $(".media-left").hide();
   $(".media-body").hide();
   $("#rslt-view-names").addClass("active-btn");
  
   $("#rslt-view-names").click(function() {
    $("#rslt-view-details , #rslt-view-description").removeClass("active-btn");
    $(this).addClass('active-btn');
    $(".profile-name-holder").show();   
    $(".rslt-profile-details-holder").hide();
    $(".media-left").hide();
    $(".media-body").hide();
    $('.rslt-gig-conversation').perfectScrollbar('update');
  });
  $("#rslt-view-details").click(function() {
    $("#rslt-view-names , #rslt-view-description").removeClass("active-btn");
    $(this).addClass('active-btn');        
    $(".profile-name-holder").show();   
    $(".rslt-profile-details-holder").show();
    $(".media-left").hide();
    $(".media-body").hide();
    setTimeout(function(){
        $('.rslt-gig-conversation').perfectScrollbar({
            suppressScrollX:true,
            wheelPropagation: false
        });
    }, 200);
      $('.rslt-gig-conversation').perfectScrollbar('update');
  });
  $("#rslt-view-description").click(function() {
     $("#rslt-view-names , #rslt-view-details").removeClass("active-btn");
     $(this).addClass('active-btn');
     $(".profile-name-holder").show();   
     $(".rslt-profile-details-holder").show();
     $(".rslt-profile-name-holder").show();
     $(".media-left").show();
     $(".media-body").show();
     $('.rslt-gig-conversation').perfectScrollbar('update');
  });
/*resizable*/
    $(".resizable").resizable({
    handles: 's',
    stop: function(event, ui) {
        $(this).css("width", '');
    }
    });
  
/*Splitter code*/
        splitter = $('.split').touchSplit({leftMin:"0", rightMin:"0", thickness: "3px", dock:"left"})
          
/*perfect scrollbar*/    
  $('[data-toggle="tooltip"]').tooltip(); 
  $('#resizablecontainerskills').perfectScrollbar().perfectScrollbar('update');
  $('#resizablecontainercategories').perfectScrollbar().perfectScrollbar('update');
  $('#resizablecontainerchannel').perfectScrollbar().perfectScrollbar('update');
  $('#slct-scroll-container').perfectScrollbar();

/* tokanizable*/
  $('#tokenize').tokenize();
  $('#tokenizechannel').tokenize();
  $("body").click(function(event){
     if(event.target.id == 'resizablecontainerskills'){
       return;
     }    
   $("#resizablecontainerskills ul.TokensContainer li").removeClass("Token");
   $("#resizablecontainerskills a.Close").text('');
   $("#resizablecontainerskills div.Tokenize ul li span").addClass("addcomma");      
   $("#resizablecontainerskills div.Tokenize ul li span").last().removeClass( "addcomma");
   $("#resizablecontainerskills div.Tokenize ul li").addClass("display-inline-text");
   $('#resizablecontainerskills').addClass("slct-skill-margin-hover");
 });
 $("#resizablecontainerskills .TokensContainer").on('click','li', function(){
   addInputTagSkills();
 });
 $("#resizablecontainerskills ul.TokensContainer .TokenSearch>input").add("#resizablecontainerskills ul.TokensContainer").on('click',function(){
   addInputTagSkills();
 });
 $("#resizablecontainerskills").on('click', function(){ 
   addInputTagSkills();
 });
 $("#resizablecontainerskills .Tokenize").on('click', function(){ 
   addInputTagSkills();
 });

  function addInputTagSkills(){
   $("#resizablecontainerskills ul.TokensContainer li").addClass("Token");
   $("#resizablecontainerskills li.TokenSearch").removeClass("Token");
   $("#resizablecontainerskills a.Close").text('x');
   $("#resizablecontainerskills div.Tokenize ul li span").removeClass("addcomma");
   $(this).removeClass("slct-skill-margin-hover");
  }
  $("body").click(function(event){
       if(event.target.id == 'resizablecontainerchannel'){
        return;
  }
   $("#resizablecontainerchannel ul.TokensContainer li").removeClass("Token");
   $("#resizablecontainerchannel a.Close").text('');
   $("#resizablecontainerchannel div.Tokenize ul li span").addClass("addcomma");
   $("#resizablecontainerchannel div.Tokenize ul li span").last().removeClass("addcomma");
   $("#resizablecontainerchannel div.Tokenize ul li").addClass("display-inline-text");
   $('#resizablecontainerchannel').addClass("slct-skill-margin-hover");
 });
   $("#resizablecontainerchannel .TokensContainer").on('click', ' li', function(){
       addInputTagChannel();
    });
   $("#resizablecontainerchannel ul.TokensContainer .TokenSearch>input").add("#resizablecontainerchannel ul.TokensContainer").on('click',function(){
       addInputTagChannel();
    });
   $("#resizablecontainerchannel").on('click', function(){ 
        addInputTagChannel();
    });

  function addInputTagChannel(){
   $("#resizablecontainerchannel ul.TokensContainer li").addClass("Token");
   $("#resizablecontainerchannel li.TokenSearch").removeClass("Token");
   $("#resizablecontainerchannel a.Close").text('x');
   $("#resizablecontainerchannel div.Tokenize ul li span").removeClass("addcomma");
   $("#resizablecontainerchannel div.Tokenize ul li").removeClass("display-inline-text");
   $('#resizablecontainerchannel').removeClass("slct-skill-margin-hover");
  }


$("#resizablecontainerskills").mouseenter(function(){
  var resizableHeight = parseInt($("#resizablecontainerskills div.tokenize-sample").height());
   if( resizableHeight <= 100)
   {
     $("#resizablecontainerskills .ps-scrollbar-y").css("display","none");
   }
   else
   {
     $("#resizablecontainerskills .ps-scrollbar-y").css("display","initial");
   }    
});

$("#resizablecontainerchannel").mouseenter(function(){
    var resizableHeightChannel = parseInt($("#resizablecontainerchannel div.tokenize-sample").height());
   if( resizableHeightChannel <= 100)
   {  
     $("#resizablecontainerchannel .ps-scrollbar-y").css("display","none");
     $("#resizablecontainercategories .ps-scrollbar-x").css("display","none");
   }
   else
   {
     $("#resizablecontainerchannel .ps-scrollbar-y").css("display","initial");
   }    
});

 $("#resizablecontainercategories .ps-scrollbar-y").css("display","none");
 $("#resizablecontainercategories .ps-scrollbar-x").css("display","none");
 $("#resizablecontainercategories").mouseenter(function(){
    var resizableHeight = parseInt($("ul.dynatree-container").height());
    var resizableContainerHeight = parseInt($("#resizablecontainercategories").height());
   if( resizableHeight <= 100)
   {
     $("#resizablecontainercategories .ps-scrollbar-y").css("display","none");
     $("#resizablecontainercategories .ps-scrollbar-x").css("display","none");  
   }
   if(resizableHeight > resizableContainerHeight)
   {
     $("#resizablecontainercategories .ps-scrollbar-y").css("display","initial");
   }
   else
   {
     $("#resizablecontainercategories .ps-scrollbar-y").css("display","none");
   }    
 });
}); //ready
