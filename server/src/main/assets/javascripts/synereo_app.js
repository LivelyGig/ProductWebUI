window.onload = function() {
  $(document).ready(function() {
    var homeFeedMediaList = $("#homeFeedMediaList");
    var homeFeedMediaListHeight =$("#homeFeedMediaList").height();
    var numberOfLi = $("#homeFeedMediaList li").length;
    $("#homeFeedMediaList").scroll(function() {
      $("#homeFeedMediaList li").each(function(){
        if ($(this).position().top < -$(this).height() ) {
          $(this).next().css("opacity",1);
          $(this).next().next().css("opacity",0.6);
        }
      });
      console.log("\n \n\n\n  ******************** \n\n\n");          
    });
  });
};

