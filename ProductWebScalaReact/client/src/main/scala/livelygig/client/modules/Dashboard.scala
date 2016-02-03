package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.LGMain.{ContractsLoc, DashboardLoc, Loc}
import livelygig.client.components._
import livelygig.client.css.{HeaderCSS, DashBoardCSS, LftcontainerCSS}

import scalacss.ScalaCssReact._

object Dashboard {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard")
    .render_P(ctl =>
      <.div(^.id := "mainContainer", DashBoardCSS.Style.mainContainerDiv)(

        <.div(^.className := "col-lg-1")(),
        <.div(^.className := "col-lg-10")(
          <.span(^.fontWeight.bold)("For your attention"), <.br(),
          <.a(^.href := "")("18"), " Unread messages", <.br(),
          <.a(^.href := "")("2"), " Active Contracts", <.br(),
          <.span(^.fontWeight.bold)("Opportunities"), <.br(),
          <.a(^.href := "")("2"), " Introduction Requests", <.br(),
          <.a(^.href := "")("Grow"), " your network", <.br(),
          <.a(^.href := "")("Search"), " for gigs", <.br(),
          <.span(^.fontWeight.bold)("Suggestions"), <.br(),
          "Complete your Talent profile by adding a video introduciton. ", <.a(^.href := "")("(Hide)"), ".", <.br(),
          "Create a standard offering, i.e., promise you can complete a typically requested service with a fixed price and predictable response time. ", <.a(^.href := "")("(Hide)"), ".", <.br(),
          <.span(^.fontWeight.bold)("Introduce Colleagues"), <.br(),
          "Recommend a friend's profile to an employer's project. (15) with some matching skills.", <.br(),
          "Recommend a friend's project (3) to a talent contact.", <.br(),
          <.span(^.fontWeight.bold)("Browse for..."), <.br(),
          "New projects", <.br(),
          "New contensts", <.br(),
          "New talent", <.br(),
          ""
        ),
        <.div(^.className := "col-lg-1")()
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