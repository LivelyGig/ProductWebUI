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
          $(this).prev().next().css("opacity",0.6);
        }
      });
      console.log("\n \n\n\n  ******************** \n\n\n");          
    });
  });
};

var lis = $("#homeFeedMediaList li"),
fraction = 0.8;
function checkScroll() {

    for(var i = 0; i < lis.length; i++) {

        var li = lis[i];

        var x = li.offsetLeft, y = li.offsetTop, w = li.offsetWidth, h = li.offsetHeight, r = x + w, //right
            b = y + h, //bottom
            visibleX, visibleY, visible;

            visibleX = Math.max(0, Math.min(w, window.pageXOffset + window.innerWidth - x, r - window.pageXOffset));
            visibleY = Math.max(0, Math.min(h, window.pageYOffset + window.innerHeight - y, b - window.pageYOffset));

            visible = visibleX * visibleY / (w * h);

            if (visible > fraction) {
                $(li).$(this).next().css("background-color","red");
            } else {
                li.pause();
            }

    }

}

window.addEventListener('scroll', checkScroll, false);
window.addEventListener('resize', checkScroll, false);


