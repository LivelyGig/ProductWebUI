package livelygig.client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap._
import livelygig.client.components._
import livelygig.client.css._
import livelygig.client.logger._
import livelygig.client.models.UserModel
import livelygig.client.services.CoreApi._
import livelygig.client.services._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._

object Invoice {
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(ctl: RouterCtl[Loc])

  case class State(showInvoiceFlag: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback =  {
      t.modState(s => s.copy(showInvoiceFlag = true))
    }
    def addInvoiceForm() : Callback = {
      t.modState(s => s.copy(showInvoiceFlag = true))
    }
    def addNewLoginForm() : Callback = {
      t.modState(s => s.copy(showInvoiceFlag = true))
    }

    def addInvoice(userModel: UserModel, showInvoiceFlag: Boolean = false): Callback = {
      log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showInvoiceFlag}")
      if(showInvoiceFlag){
        createUser(userModel).onComplete {
          case Success(s) =>
            log.debug(s"createUser msg : ${s.msgType}")
            if (s.msgType == ApiResponseMsg.CreateUserWaiting){
              t.modState(s => s.copy(showInvoiceFlag = true)).runNow()
            } else {
              log.debug(s"createUser msg : ${s.content}")
              t.modState(s => s.copy(/*showRegistrationFailed = true*/)).runNow()
            }
          case Failure(s) =>
            log.debug(s"createUserFailure: ${s}")
            t.modState(s => s.copy(/*showErrorModal = true*/)).runNow()
          // now you need to refresh the UI
        }
        t.modState(s => s.copy(showInvoiceFlag = true))
      } else {
        t.modState(s => s.copy(showInvoiceFlag = false))
      }
    }
  }

  val component = ReactComponentB[Props]("AddInvoice")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(ProjectCSS.Style.displayInitialbtn)(

        Button(Button.Props(B.addInvoiceForm(), CommonStyle.default, Seq(HeaderCSS.Style.createNewProjectBtn)),"Invoice"),
        if (S.showInvoiceFlag) InvoiceForm(InvoiceForm.Props(B.addInvoice))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build
  def apply(props: Props) = component(props)
}

object InvoiceForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: (UserModel, Boolean) => Callback)
  case class State(userModel: UserModel, postProject: Boolean = false)


  case class Backend(t: BackendScope[Props, State])/* extends RxObserver(t)*/ {
    def hide = Callback {
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }
    def mounted(props: Props): Callback = Callback {

    }
    def updateName(e: ReactEventI) = {
      t.modState(s => s.copy(userModel = s.userModel.copy(name = e.target.value)))
    }
    def updateEmail(e: ReactEventI) = {
      t.modState(s => s.copy(userModel = s.userModel.copy(email = e.target.value)))
    }
    def updatePassword(e: ReactEventI) = {
      t.modState(s => s.copy(userModel = s.userModel.copy(password = e.target.value)))
    }
    def toggleBTCWallet(e: ReactEventI) = {
      t.modState(s => s.copy(userModel = s.userModel.copy(createBTCWallet = !s.userModel.createBTCWallet)))
    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(postProject = false))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.postProject)
      props.submitHandler(state.userModel, state.postProject)
    }

    def render(s: State, p: Props) = {
      if (s.postProject){
        jQuery(t.getDOMNode()).modal("hide")
      }
      val headerText = "Invoice Overlay"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className:="row")(
//            <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont,MessagesCSS.Style.paddingLeftModalHeaderbtn)("Invoice"))
          ),//main row
          <.div(^.className:="row" , DashBoardCSS.Style.MarginLeftchkproduct)(
            <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
               <.div(^.className:="row")(
                  <.div(^.className:="col-md-8 col-sm-8 col-xs-8 text-center")(
                    <.div(DashBoardCSS.Style.modalHeaderText)("Project Name -- Milestone One")
                  ),
                 <.div(^.className:="col-md-2 col-sm-2 col-xs-2", InvoiceCSS.Style.invoiceText)(
                   <.div(DashBoardCSS.Style.modalHeaderText)("Buyer")
                 ),
                 <.div(^.className:="col-md-2 col-sm-2 col-xs-2", InvoiceCSS.Style.invoiceText)(
                   <.div(DashBoardCSS.Style.modalHeaderText)("Seller")
                 )
               ),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-8 col-sm-8 col-xs-8")(
                  <.div(DashBoardCSS.Style.modalHeaderText)(
                    <.div(^.className:="row")(
                      <.div(^.className:="col-md-12 col-sm-12 col-xs-12",MessagesCSS.Style.slctMessagesInputWidth)(
                        <.div("Item One")
                        //                        <.span(^.className:="checkbox-lbl")
                      ),
                      <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                        <.input(^.className:="form-control", InvoiceCSS.Style.invoiceInputHeightWidth , ^.placeholder:="$")
                      )
                    )

                  )
                ),
                <.div(^.className:="col-md-2 col-sm-2 col-xs-2", InvoiceCSS.Style.invoiceText)(
                  <.div(DashBoardCSS.Style.modalHeaderText)("Buyer")
                ),
                <.div(^.className:="col-md-2 col-sm-2 col-xs-2", InvoiceCSS.Style.invoiceText)(
                  <.div(DashBoardCSS.Style.modalHeaderText)("Seller")
                )
              )


           )


          ),
          <.div()(
            <.div(DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.footTextAlign)(
              //              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Post"),
              //              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
            )
          ),
          <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("InvoiceForm")
    .initialState_P(p => State(new UserModel("","","",false)))
    .renderBackend[Backend]
    .componentDidMount(scope => Callback {
      val P = scope.props
      val S=scope.state
      val B=scope.backend

      def hideModal = Callback {
        if (S.postProject) {
          def hide = Callback {
            jQuery(scope.getDOMNode()).modal("hide")
          }
        }
      }
    })
    .componentDidUpdate(scope=> Callback{

    })
    .build
  def apply(props: Props) = component(props)
}


