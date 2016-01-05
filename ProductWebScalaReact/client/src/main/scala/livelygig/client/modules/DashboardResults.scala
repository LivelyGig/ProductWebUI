package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.LGMain.Loc
import livelygig.client.components.Icon
import livelygig.client.css.{DashBoardCSS, LftcontainerCSS}

import scalacss.ScalaCssReact._

object DashboardResults {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard")
    .render_P(ctl =>
      // todo: Need to parameterize.  This example is for Talent
      <.div(^.id := "rsltScrollContainer", DashBoardCSS.Style.rsltContainer)(
        <.div(DashBoardCSS.Style.gigActionsContainer, ^.className := "col-md-12 col-sm-12 col-xs-12")(
          <.div(^.className := "col-md-8 col-sm-7 col-xs-8")(
            <.div(DashBoardCSS.Style.rsltGigActionsDropdown, ^.className := "dropdown")(
              <.button(DashBoardCSS.Style.gigMatchButton, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("LivelyGig Match ")(
                <.span(^.className := "caret")
              ),
              <.ul(^.className := "dropdown-menu")(
                <.li()(<.a(^.href := "#")("By Bid Amount")),
                <.li()(<.a(^.href := "#")("By LivelyGig Match")),
                <.li()(<.a(^.href := "#")("By Experience"))
              )
            ), //dropdown class
            <.div(DashBoardCSS.Style.rsltCountHolderDiv)("34,321 results")
          ),
          <.div(DashBoardCSS.Style.listIconPadding, ^.className := "col-md-4 col-sm-5 col-xs-4")(
            <.div(^.className := "pull-right")(
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Full Posts")(<.span(Icon.list))
            )
          )
        ), //col-12
        <.div(DashBoardCSS.Style.gigConversation, ^.className := "container-fluid")(
          <.div(^.id := "rsltSectionContainer", ^.className := "col-md-12 col-sm-12 col-xs-12")(
            <.ul(^.className := "media-list")(
              <.li(^.className := "media")(
                <.div(^.className := "media-left")(
                  <.img(DashBoardCSS.Style.profileImg, ^.src := "./assets/images/profile-img.png")
                ), //media-left
                <.div(^.className := "media-body")(
                  "Beautiful videos to capture your most memorable moments."
                ), //media-body
                <.div(DashBoardCSS.Style.profileNameHolder, ^.className := "col-md-3")("Abed A. -- Videographer"),
                <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Experience: 3 years"),
                <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Projects Completed: 8"),
                <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Availability: Negotiable")
              ), //li
              <.li(^.className := "media")(
                <.div(^.className := "media-left")(
                  <.img(DashBoardCSS.Style.profileImg, ^.src := "./assets/images/profile-img.png")
                ), //media-left
                <.div(^.className := "media-body")(
                  "Front-end developer."
                ), //media-body
                <.div(DashBoardCSS.Style.profileNameHolder, ^.className := "col-md-3")("Britta B. -- Developer"),
                <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Experience: 8 years"),
                <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Projects Completed: 24"),
                <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Availability: Negotiable")
              ), //li
              <.li(^.className := "media")(
                <.div(^.className := "media-left")(
                  <.img(DashBoardCSS.Style.profileImg, ^.src := "./assets/images/profile-img.png")
                ), //media-left
                <.div(^.className := "media-body")(
                  "Beautiful ads and marketing products."
                ), //media-body
                <.div(DashBoardCSS.Style.profileNameHolder, ^.className := "col-md-3")("Tom C. -- Designer"),
                <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Experience: 8 years"),
                <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Projects Completed: 24"),
                <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Availability: Negotiable")
              )
            ) //ul
          )
        ) //gigConversation
      )

    )
    .componentDidMount(scope => Callback {
      //val P = scope.props
      // instruct Bootstrap to show the modal
      //     jQuery(scope.getDOMNode()).modal(js.Dynamic.literal("backdrop" -> P.backdrop, "keyboard" -> P.keyboard, "show" -> true))})
      //
      //     var citynames = new Bloodhound({
      //           datumTokenizer: Bloodhound.tokenizers.obj.whitespace,
      //           queryTokenizer: Bloodhound.tokenizers.whitespace//,
      //           prefetch: {
      //             url: 'assets/citynames.json',
      //             filter: function(list) {
      //             return $.map(list, function(cityname) {
      //             return { name: cityname }; });
      //           }
      //         })

      //         citynames.initialize();
      //         jQuery(scope.getDOMNode()).input().tagsinput({
      //
      //       typeaheadjs: {
      //         name : 'citynames ',
      //         displayKey : 'name ',
      //         valueKey : 'name ',
      //         source : citynames.ttAdapter()
      //       }
      //
      //     })
      //
      //  var citynames = new Bloodhound({
      //    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
      //    queryTokenizer: Bloodhound.tokenizers.whitespace,
      //    prefetch: {
      //      url: 'assets/citynames.json',
      //      filter: function(list) {
      //      return $.map(list, function(cityname) {
      //      return { name: cityname }; });
      //    }
      //    }
      //  });
      //  citynames.initialize();
      //
      //  $('input').tagsinput({
      //    typeaheadjs: {
      //      name: 'citynames',
      //      displayKey: 'name',
      //      valueKey: 'name',
      //      source: citynames.ttAdapter()
      //    }
      //  });
    })
    .build
}