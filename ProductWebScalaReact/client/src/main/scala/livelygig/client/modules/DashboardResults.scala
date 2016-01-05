package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.LGMain.Loc
import livelygig.client.components.Icon
import livelygig.client.css.{HeaderCSS, DashBoardCSS, LftcontainerCSS}

import scalacss.ScalaCssReact._

object DashboardResults {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard")
    .render_P(ctl =>
      // todo: Need to parameterize.  This example is for Talent
      <.div(^.id:="rsltScrollContainer", DashBoardCSS.Style.rsltContainer)(
        <.div(DashBoardCSS.Style.gigActionsContainer , ^.className:="row")(
          <.div(^.className:="col-md-3 col-sm-3 col-xs-3")(
            <.input(^.`type` := "checkbox",DashBoardCSS.Style.rsltCheckboxStyle),
            //                      <.span(DashBoardCSS.Style.MarginLeftchkproduct, ^.className:="checkbox-lbl"),
            <.div (DashBoardCSS.Style.rsltGigActionsDropdown, ^.className:="dropdown")(
              <.button(DashBoardCSS.Style.gigMatchButton, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Select Bulk Action ")(
                <.span(^.className:="caret", DashBoardCSS.Style.rsltCaretStyle)
              ),
              <.ul(^.className:="dropdown-menu")(
                <.li()(<.a(^.href:="#")("Hide")),
                <.li()(<.a(^.href:="#")("Favorite")),
                <.li()(<.a(^.href:="#")("Unhide")),
                <.li()(<.a(^.href:="#")("Unfavorite"))
              )
            )//dropdown class
          ),
          <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(
            <.div (DashBoardCSS.Style.rsltCountHolderDiv)("2,352 Results")
          ),
          <.div(^.className:="col-md-4 col-sm-4 col-xs-4")(
            <.div (DashBoardCSS.Style.rsltGigActionsDropdown, ^.className:="dropdown")(
              <.button(DashBoardCSS.Style.gigMatchButton, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("By Date ")(
                <.span(^.className:="caret", DashBoardCSS.Style.rsltCaretStyle)
              ),
              <.ul(^.className:="dropdown-menu")(
                <.li()(<.a(^.href:="#")("By Date")),
                <.li()(<.a(^.href:="#")("By Experience")),
                <.li()(<.a(^.href:="#")("By Reputation")),
                <.li()(<.a(^.href:="#")("By Rate")),
                <.li()(<.a(^.href:="#")("By Projects Completed"))
              )
            ),
            <.button(DashBoardCSS.Style.gigMatchButton, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Newest ")(
              <.span(Icon.longArrowDown)
            )
          ),
          <.div(/*DashBoardCSS.Style.listIconPadding ,*/ ^.className:="col-md-3 col-sm-3 col-xs-3")(
            <.div(^.className:="pull-right" )(
              // todo: icon buttons should be different.  Earlier mockup on s3 had <span class="icon-List1">  2  3  ?
              <.button(DashBoardCSS.Style.btn,"data-toggle".reactAttr := "tooltip" , "title".reactAttr := "View Summery")(<.span(Icon.list)),
              <.button(DashBoardCSS.Style.btn,"data-toggle".reactAttr := "tooltip" , "title".reactAttr := "View Brief")(<.span(Icon.list)),
              <.button(DashBoardCSS.Style.btn,"data-toggle".reactAttr := "tooltip" , "title".reactAttr := "View Full Posts")(<.span(Icon.list))
            )
          )
        ),//col-12
        <.div(^.className:="container-fluid" )(
          <.div (^.id:="rsltSectionContainer", ^.className:="col-md-12 col-sm-12 col-xs-12")(
            <.ul(^.className:="media-list")(
              <.li(^.className:="media", DashBoardCSS.Style.rsltpaddingTop10p)(
                <.input(^.`type` := "checkbox",DashBoardCSS.Style.rsltCheckboxStyle),
                <.span(^.className:="checkbox-lbl"),
                <.div (DashBoardCSS.Style.profileNameHolder )("Name : job-title"),
                <.div(^.className:="col-md-12")(
                  <.div (DashBoardCSS.Style.rsltProfileDetailsHolder)("Experience: 8 years"),
                  <.div (DashBoardCSS.Style.rsltProfileDetailsHolder)("Projects Completed: 24"),
                  <.div (DashBoardCSS.Style.rsltProfileDetailsHolder)("Availability: Negotiable")),
                <.div(^.className:="media-left")(
                  <.img(DashBoardCSS.Style.profileImg, ^.src := "./assets/images/profile-img.png")
                ), //media-left
                <.div(^.className:="media-body")(
                  "lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.",
                  <.div(^.className:="col-md-12 col-sm-12")(
                    <.button(HeaderCSS.Style.rsltContainerBtn, ^.className:="btn")("Button3")(),
                    <.button(HeaderCSS.Style.rsltContainerBtn, ^.className:="btn")("Button2")(),
                    <.button(HeaderCSS.Style.rsltContainerBtn, ^.className:="btn")("Button1")())
                )//media-body
              ),//li
              <.li(^.className:="media", DashBoardCSS.Style.rsltContentBackground, DashBoardCSS.Style.rsltpaddingTop10p)(
                <.input(^.`type` := "checkbox",DashBoardCSS.Style.rsltCheckboxStyle),
                <.span(^.className:="checkbox-lbl"),
                <.div (DashBoardCSS.Style.profileNameHolder )("Name : job-title"),
                <.div(^.className:="col-md-12")(
                  <.div (DashBoardCSS.Style.rsltProfileDetailsHolder)("Experience: 8 years"),
                  <.div (DashBoardCSS.Style.rsltProfileDetailsHolder)("Projects Completed: 24"),
                  <.div (DashBoardCSS.Style.rsltProfileDetailsHolder)("Availability: Negotiable")),
                <.div(^.className:="media-left")(
                  <.img(DashBoardCSS.Style.profileImg, ^.src := "./assets/images/profile-img.png")
                ), //media-left
                <.div(^.className:="media-body")(
                  "lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.",
                  <.div(^.className:="col-md-12 col-sm-12")(
                    <.button(HeaderCSS.Style.rsltContainerBtn, ^.className:="btn")("Button3")(),
                    <.button(HeaderCSS.Style.rsltContainerBtn, ^.className:="btn")("Button2")(),
                    <.button(HeaderCSS.Style.rsltContainerBtn, ^.className:="btn")("Button1")())
                )//media-body
              ),//li
              <.li(^.className:="media", DashBoardCSS.Style.rsltpaddingTop10p)(
                <.input(^.`type` := "checkbox",DashBoardCSS.Style.rsltCheckboxStyle),
                <.span(^.className:="checkbox-lbl"),
                <.div (DashBoardCSS.Style.profileNameHolder )("Name : job-title"),
                <.div(^.className:="col-md-12")(
                  <.div (DashBoardCSS.Style.rsltProfileDetailsHolder)("Experience: 8 years"),
                  <.div (DashBoardCSS.Style.rsltProfileDetailsHolder)("Projects Completed: 24"),
                  <.div (DashBoardCSS.Style.rsltProfileDetailsHolder)("Availability: Negotiable")),
                <.div(^.className:="media-left")(
                  <.img(DashBoardCSS.Style.profileImg, ^.src := "./assets/images/profile-img.png")
                ), //media-left
                <.div(^.className:="media-body")(
                  "lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.",
                  <.div(^.className:="col-md-12 col-sm-12")(
                    <.button(HeaderCSS.Style.rsltContainerBtn, ^.className:="btn")("Button3")(),
                    <.button(HeaderCSS.Style.rsltContainerBtn, ^.className:="btn")("Button2")(),
                    <.button(HeaderCSS.Style.rsltContainerBtn, ^.className:="btn")("Button1")())
                )//media-body
              )
            )//ul
          )
        )//gigConversation
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