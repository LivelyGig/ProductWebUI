package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.LGMain.Loc
import livelygig.client.components._
import livelygig.client.css.{DashBoardCSS, LftcontainerCSS}

import scalacss.ScalaCssReact._

object DashboardSearch {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard")
    .render_P(ctl =>
      // todo: Need to parameterize on type (e.g. Talent, Project) and preset (e.g. Recommended Mathces)
      "talentPreset1" match {
        case "talentPreset1" =>
          <.div(^.id := "slctScrollContainer", DashBoardCSS.Style.slctContainer)(
            <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition)(
              <.div(DashBoardCSS.Style.slctHeaders)("Skills"),
              <.div(LftcontainerCSS.Style.slctleftcontentdiv, LftcontainerCSS.Style.resizable, ^.id := "resizablecontainerskills")(
                //<input type="text" value="Amsterdam,Washington" data-role="tagsinput"
                <.input(^.`type` := "text", "data-role".reactAttr := "tagsinput")
              ),
              <.div(DashBoardCSS.Style.slctHeaders)("Categories"),
              <.div(LftcontainerCSS.Style.slctleftcontentdiv, LftcontainerCSS.Style.resizable, ^.id := "resizablecontainerskills")(
              ),
              <.div(DashBoardCSS.Style.slctHeaders)("Price Range"),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidth)(
                  <.input(^.`type` := "checkbox")
                ),
                <.div(DashBoardCSS.Style.slctInputLeftContainerMargin)(
                  <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                )
              ),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidth)(
                  <.input(^.`type` := "checkbox")
                ),
                <.div(DashBoardCSS.Style.slctInputLeftContainerMargin)(
                  <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                )
              ),
              <.div(DashBoardCSS.Style.slctHeaders)("Posted Date"),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidth)(
                  <.input(^.`type` := "checkbox"),
                  //                  <span class="checkbox-lbl"></span>
                  <.span(DashBoardCSS.Style.checkboxLbl)()
                ),
                <.div(DashBoardCSS.Style.slctInputLeftContainerMargin)(
                  <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                )
              ),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidth)(
                  <.input(^.`type` := "checkbox")
                ),
                <.div(DashBoardCSS.Style.slctInputLeftContainerMargin)(
                  <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                )
              ),
              <.div(DashBoardCSS.Style.slctHeaders)("Channels"),
              <.div(LftcontainerCSS.Style.slctleftcontentdiv, LftcontainerCSS.Style.resizable, ^.id := "resizablecontainerskills")(
              )
            )
          )
      })
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