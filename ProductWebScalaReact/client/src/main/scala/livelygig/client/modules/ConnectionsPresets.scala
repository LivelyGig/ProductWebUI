package livelygig.client.modules

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.css.{MessagesCSS, HeaderCSS}
import livelygig.client.modals.UserSkills
import scalacss.ScalaCssReact._

object ConnectionsPresets {
  // create the React component for Dashboard
  val component = ReactComponentB[Unit]("Connections")
    .render(ctl =>
      <.div(^.id := "middelNaviContainer", HeaderCSS.Style.middelNaviContainer)(
        <.div(/*^.className := "row"*/)(
          <.div(^.className := "col-lg-1")(),
          <.div(^.className := "col-lg-10 col-sm-12 col-xs-12")(
            <.div()(
              <.div(^.className := "btn-group")(
                <.button(HeaderCSS.Style.projectCreateBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Favorited ")(
                  <.span(^.className := "caret")
                ),
                <.ul(HeaderCSS.Style.dropdownMenuWidth, ^.className := "dropdown-menu")(
                  <.li()(<.a(^.href := "#")("All")),
                  <.li()(<.a(^.href := "#")("Online")),
                  <.li()(<.a(^.href := "#")("Favorited")),
                  <.li()(<.a(^.href := "#")("Hidden")),
                  <.li(^.className := "divider")(),
                  <.li()(<.a(^.href := "#")("Customize..."))
                )
              ),

              <.div(MessagesCSS.Style.newProjectbtn, ^.paddingTop:="5px")(
                // NewMessage(NewMessage.Props(p.ctl, "New Connection"))
                <.button(HeaderCSS.Style.createNewProjectBtn, ^.className := "btn")("New Connection")
              )
            )
          ),
          <.div(^.className := "col-lg-1")()
        )
      )
    )


    .componentDidMount(scope => Callback {
    })
    .build
}
