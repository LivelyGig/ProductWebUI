var ScrollSpeedMonitor = (function()
{
    var self = this;

    function ScrollSpeedMonitor (callbackMethod)
    {
        callback = callbackMethod;

        $("#dashboardContainerMain").scroll(function(e)
        {
            var scrollTop = $(this).scrollTop();
            didScroll(new Date().getTime(), scrollTop);
        });
    }

    var callback;
    var direction = 'unknown';
    var lastDate = -1;
    var lastScrollTop = -1;

    this.thisMinimumTrackingDelayInMs = 25;

    function didScroll (timeStamp, scrollTop)
    {
        if (lastDate + self.thisMinimumTrackingDelayInMs <= timeStamp)
        {
            var offset = Math.abs(scrollTop - lastScrollTop);
            var direction = getDirection(scrollTop);
            var delayInMs = timeStamp - lastDate;
            var speedInPxPerMs = offset / delayInMs;

            if (speedInPxPerMs > 0)
            {

                callback(speedInPxPerMs, timeStamp, direction);
            }

            lastDate = timeStamp;
        }
    };

    function getDirection (scrollTop)
    {
        var currentScrollTop = lastScrollTop;
        lastScrollTop = scrollTop;

        if (currentScrollTop > -1)
        {
            if (currentScrollTop >= scrollTop)
            {
                return 'down';
            }

            return 'up';
        }

        return 'unknown';
    }

    function reset ()
    {
        direction = 'unknown';
        lastDate = -1;
        lastScrollTop = -1;
    }

    return ScrollSpeedMonitor;
}());

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
    applyStylingToHomeFeed();
    var hoverInterval=  setInterval(function(){
      // $("#dashboardContainerMain").scroll(applyStylingToHomeFeed);
      var scrollSpeedMonitor = new ScrollSpeedMonitor(function (speedInPxPerMs, timeStamp, newDirection){
        console.log('Scroll speed: ' + speedInPxPerMs);
        if (speedInPxPerMs > 4) {
            $("#homeFeedMediaList li").css("opacity","0.2");
        }else{
            applyStylingToHomeFeed();
        }
    });
      $("#homeFeedMediaList li").hover(function(){
        $(this).prev().prev().css({"opacity":"0.35","transform": "scale(1)"});
        $(this).prev().css({"opacity":"0.6","transform": "scale(1)"});
        $(this).css({"opacity":"1","transform": "scale(1.05)"});
        $(this).next().css({"opacity":"0.6","transform": "scale(1)"});
        $(this).next().next().css({"opacity":"0.35","transform": "scale(1)"});
    }/*,
    function(){
      $(this).prev().prev().css({"opacity":"0.35","transform": "scale(1)"});
      $(this).prev().css({"opacity":"0.6","transform": "scale(1)"});
      $(this).css({"opacity":"1","transform": "scale(1.05)"});
      $(this).next().css({"opacity":"0.6","transform": "scale(1)"});
      $(this).next().next().css({"opacity":"0.35","transform": "scale(1)"});
  }*/);
  }, 1000);
    setTimeout(function(){
      clearInterval(hoverInterval);
  },100000000);
});
};
