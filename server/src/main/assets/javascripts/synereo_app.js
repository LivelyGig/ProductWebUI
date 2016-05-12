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
  function checkScrollSpeed(){
    var lastPos, newPos, timer, delta, 
        delay =  50; // in "ms" (higher means lower fidelity )

        function clear() {
          lastPos = null;
          delta = 0;
        }
        clear();
        function calculateDelta(){
          newPos = window.scrollY;
      if ( lastPos != null ){ // && newPos < maxScroll 
        delta = newPos -  lastPos;
      }
      lastPos = newPos;
      clearTimeout(timer);
      timer = setTimeout(clear, delay);
      return delta;
    };
    return calculateDelta
  };
  $(document).ready(function() {
    applyStylingToHomeFeed()
    $("#dashboardContainerMain").scroll(function() {
     // console.log(checkScrollSpeed) 
     applyStylingToHomeFeed()
   });
  });
};