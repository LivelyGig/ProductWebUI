package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.LGMain.{DashboardLoc, Loc}
import livelygig.client.components._
import livelygig.client.css.{HeaderCSS, DashBoardCSS, LftcontainerCSS}

import scalacss.ScalaCssReact._

object Dashboard {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard")
    .render_P(ctl => {
      //      <.div (^.id:="mainContainer", DashBoardCSS.Style.mainContainerDiv)(
      //       // AddNewAgent(AddNewAgent.Props(ctl)),
      //        <.div(DashBoardCSS.Style.splitContainer)(
      //          <.div(^.className:="split")(
      //            <.div(^.className:="row")(

      // the contents will vary depending on EntityType, e.g. Messages, Projects, Talent...
      "talent" match {
        case "talent" =>
          <.div(^.id := "mainContainer", DashBoardCSS.Style.mainContainerDiv)(
            // AddNewAgent(AddNewAgent.Props(ctl)),
            <.div(DashBoardCSS.Style.splitContainer)(
              <.div(^.className := "split")(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                    // todo: Need to parameterize the Search area depending on EntityType (e.g. Talent, Project) and preset
                    DashboardSearch.component(ctl)
                  ),
                  <.div(^.className := "col-md-10 col-sm-10 col-xs-10")(
                    // todo: Results will be parameterized depending on EntityType, preset
                    DashboardResults.component(ctl)
                  )
                ))
            )//row
          ) //mainContainer
      }
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