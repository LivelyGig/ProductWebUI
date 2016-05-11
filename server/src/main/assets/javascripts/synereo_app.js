window.onload = function() {
  function applyStylingToHomeFeed(){
    $("#homeFeedMediaList li").each(function(){
      if ($(this).offset().top < 400 &&  $(this).offset().top > 100) {
        $(this).prev().prev().css({"opacity":"0.35","transform": "scale(1)"});
        $(this).prev().css({"opacity":"0.6","transform": "scale(1)"});
        $(this).css({"opacity":"1","transform": "scale(1.05)"});
        $(this).next().css({"opacity":"0.6","transform": "scale(1)"});
        $(this).next().next().css({"opacity":"0.35","transform": "scale(1)"});
      }
    });
  };
  $(document).ready(function() {
    applyStylingToHomeFeed()
    $("#dashboardContainerMain").scroll(function() {
      applyStylingToHomeFeed()
    });
  });
};