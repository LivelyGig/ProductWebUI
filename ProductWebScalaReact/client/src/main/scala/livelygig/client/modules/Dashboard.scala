package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.LGMain.{ Loc}
import livelygig.client.components._
import livelygig.client.css.{DashBoardCSS}

import scalacss.ScalaCssReact._

object Dashboard {
  // create the React component for Dashboard
  val cp = Chart.ChartProps("Test chart", Chart.BarChart, ChartData(Seq("A", "B", "C","D"), Seq(ChartDataset(Seq(1, 2, 4, 3), "Data1"))))
  val cp1 = Chart.ChartProps("Test chart", Chart.LineChart, ChartData(Seq("A", "B", "C","D"), Seq(ChartDataset(Seq(1, 2, 4, 3), "Data1"))))
  val cp2 = Chart.ChartProps("Test chart", Chart.PieChart, ChartData(Seq("A", "B", "C","D"), Seq(ChartDataset(Seq(1, 2, 4, 3), "Data1"))))
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard")
    .render_P(ctl =>
      <.div(^.id := "mainContainer", DashBoardCSS.Style.mainContainerDiv)(
        <.div(^.className := "col-lg-1")(),
        <.div(^.className := "col-lg-10 dashboard-container")(
          <.div(^.className:="dashboardContainer")(
            <.div()(
              <.div(^.className:="col-md-6 col-sm-6")(
                <.div( DashBoardCSS.Style.attentionContainer)(
                  <.span(DashBoardCSS.Style.headerFontDashboard)("For your attention"), <.br(),
                  Chart(cp)
                )
              ),
              <.div(^.className:="col-md-6 col-sm-6")(
                <.div( DashBoardCSS.Style.opportunitiesContainer)(
                  <.span(DashBoardCSS.Style.headerFontDashboard)("Opportunities"), <.br(),
                  Chart(cp1)
                )
              )
            ),
            <.div()(
              <.div(^.className:="col-md-8 col-sm-8")(
                <.div(DashBoardCSS.Style.suggestionsContainer)(
                  <.span(DashBoardCSS.Style.headerFontDashboard)("Suggestions"), <.br(),
                  "Complete your Talent profile by adding a video introduciton. ", <.a()("(Hide)"), ".", <.br(),
                  "Create a standard offering, i.e., promise you can complete a typically requested service with a fixed price and predictable response time. ", <.a(^.href := "")("(Hide)"), ".", <.br()
                )
              ),
              <.div(^.className:="col-md-4 col-sm-4")(
                <.div(DashBoardCSS.Style.browseforContainer)(
                  <.span(DashBoardCSS.Style.headerFontDashboard)("Browse for"), <.br(),
                  "New projects", <.br(),
                  "New contensts", <.br(),
                  "New talent", <.br()
                )
              )
            ),
            <.div()(
              <.div(^.className:="col-md-12 col-sm-12")(
                <.div( DashBoardCSS.Style.introduceColleaguesContainer)(
                  <.span(DashBoardCSS.Style.headerFontDashboard)("Introduce Colleagues"), <.br(),
                  "Recommend a friend's profile to an employer's project. (15) with some matching skills.", <.br(),
                  "Recommend a friend's project (3) to a talent contact.", <.br()
                )
              )
            )
          )
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