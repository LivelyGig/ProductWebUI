package client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Bootstrap._
import client.components.Icon.Icon
import client.components._
import client.css.{DashBoardCSS, HeaderCSS, ProjectCSS}
import client.modules.AppModule
import client.services.LGCircuit
import japgolly.scalajs.react

import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._
import scalacss.StyleA
import scala.language.reflectiveCalls
import org.querki.jquery._
import shared.models.ProfilePostContent
import client.sessionitems.SessionItems
import client.utils.{AppUtils, ConnectionsUtils, ContentUtils, LabelsUtils}
import diode.AnyAction._

object NewProfile {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), addIcons: Icon, title: String)

  case class State(showUserSkillsForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showUserSkillsForm = true))
    }

    def addUserSkillsForm(): Callback = {
      t.modState(s => s.copy(showUserSkillsForm = true))
    }

    def addUserSkills(postUserSkills: Boolean = false): Callback = {
      //  log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showUserSkillsForm}")
      if (postUserSkills) {
        t.modState(s => s.copy(showUserSkillsForm = true))
      } else {
        t.modState(s => s.copy(showUserSkillsForm = false))
      }
    }
  }

  val component = ReactComponentB[Props]("UserSkills")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(/*ProjectCSS.Style.displayInitialbtn*/)(
        Button(Button.Props(B.addUserSkillsForm(), CommonStyle.default, P.addStyles, P.addIcons, P.title), P.buttonName),
        if (S.showUserSkillsForm) NewProfileForm(NewProfileForm.Props(B.addUserSkills))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build

  def apply(props: Props) = component(props)
}

object NewProfileForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (Boolean) => Callback)

  case class State(profilePost: ProfilePostContent, postUserSkills: Boolean = false)

  case class Backend(t: BackendScope[Props, State]) /* extends RxObserver(t)*/ {
    def hide = Callback {
      // instruct Bootstrap to hide the modal
      $(t.getDOMNode()).modal("hide")
    }

    def hidemodal = {
      // instruct Bootstrap to hide the modal
      $(t.getDOMNode()).modal("hide")
    }

    def mounted(props: Props): Callback = Callback {

    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      val state = t.state.runNow()
      val req = AppUtils.getPostData(state.profilePost, Seq(ConnectionsUtils.getSelfConnnection(AppModule.PROFILES_VIEW)),Nil, AppModule.PROFILES_VIEW)
      ContentUtils.postContent(req)
      t.modState(s => s.copy(postUserSkills = false))
    }

    def formClosed(state: State, props: Props): Callback = {
      props.submitHandler(state.postUserSkills)
    }
    def updateTalentProfileName(event: ReactEventI): react.Callback = {
      val value = event.target.value
      t.modState(s => s.copy(profilePost = s.profilePost.copy( talentProfile= s.profilePost.talentProfile.copy(name = value))))
    }
    def updateTalentCapabilities(event: ReactEventI): react.Callback = {
      val value = event.target.value
      t.modState(s => s.copy(profilePost = s.profilePost.copy( talentProfile= s.profilePost.talentProfile.copy(capabilities = value))))
    }
    def updateTalentProfileTitle(event: ReactEventI): react.Callback = {
      val value = event.target.value
      t.modState(s => s.copy(profilePost = s.profilePost.copy( talentProfile= s.profilePost.talentProfile.copy(title = value))))
    }
    def updateTalentProfileVideo(event: ReactEventI): react.Callback = {
      val value = event.target.value
      t.modState(s => s.copy(profilePost = s.profilePost.copy( talentProfile= s.profilePost.talentProfile.copy(video = value))))
    }
    /*def updateEmployerProfileName(event: ReactEventI): react.Callback = {
      val value = event.target.value
      t.modState(s => s.copy(profilePost = s.profilePost.copy( employerProfile= s.profilePost.employerProfile.copy(name = value))))
    }
    def updateEmployerProfileWebsite(event: ReactEventI): react.Callback = {
      val value = event.target.value
      t.modState(s => s.copy(profilePost = s.profilePost.copy( employerProfile= s.profilePost.employerProfile.copy(website = value))))
    }
    def updateEmployerProfileTagline(event: ReactEventI): react.Callback = {
      val value = event.target.value
      t.modState(s => s.copy(profilePost = s.profilePost.copy( employerProfile= s.profilePost.employerProfile.copy(tagline = value))))
    }
    def updateEmployerProfileVideo(event: ReactEventI): react.Callback = {
      val value = event.target.value
      t.modState(s => s.copy(profilePost = s.profilePost.copy( employerProfile= s.profilePost.employerProfile.copy(video = value))))
    }
    def updateEmployerProfileTwitter(event: ReactEventI): react.Callback = {
      val value = event.target.value
      t.modState(s => s.copy(profilePost = s.profilePost.copy( employerProfile= s.profilePost.employerProfile.copy(twitter = value))))
    }
    def updateEmployerProfileLogo(event: ReactEventI): react.Callback = {
      val value = event.target.value
      t.modState(s => s.copy(profilePost = s.profilePost.copy( employerProfile= s.profilePost.employerProfile.copy(logo = value))))
    }
    def updateModeratorProfileName(event: ReactEventI): react.Callback = {
      val value = event.target.value
      t.modState(s => s.copy(profilePost = s.profilePost.copy( moderatorProfile= s.profilePost.moderatorProfile.copy(name = value))))
    }
    def updateModeratorProfileCapabilities(event: ReactEventI): react.Callback = {
      val value = event.target.value
      t.modState(s => s.copy(profilePost = s.profilePost.copy( moderatorProfile= s.profilePost.moderatorProfile.copy(capabilities = value))))
    }
    def updateModeratorProfileCommission(event: ReactEventI): react.Callback = {
      val value = event.target.value
      t.modState(s => s.copy(profilePost = s.profilePost.copy( moderatorProfile= s.profilePost.moderatorProfile.copy(commission = value))))
    }
*/
    def render(s: State, p: Props) = {
      val headerText = "New Profile"
      val model = s.profilePost
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
          // this is called after the modal has been hidden (animation is completed)
          closed = () => formClosed(s, p)
        ),
        <.form(^.onSubmit ==> submitForm)(
          //          <.div(^.className:="row")(
          //            <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont)("User Skills"))
          //          ),
          <.div()(
            "Your persona currently has the following profiles:", <.br(),
            "Talent: Videographer ", <.a()("edit"), <.br(),
            "Talent:   .Net Developer ", <.a()("edit"), <.br(),
            "Talent Agency: Video Now On-Location News Agency ", <.a()("edit"), <.br()
          ),


          <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
            <.div(^.className := "row")(
              <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
                <.label(^.`for` := "Profile Type", "Create New Profile Type")
              ),
              <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                <.div(^.className := "btn-group")(
                  <.button(ProjectCSS.Style.projectdropdownbtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Talent")(
                    <.span(^.className := "caret")
                  ),
                  <.ul(^.className := "dropdown-menu")(
                    <.li()(<.a()("Talent")),
                    <.li()(<.a()("Talent Profile")),
                    <.li()(<.a()("Client")),
                    <.li()(<.a()("Moderator"))
                  )
                )
              )
            )
          ),


          "// For Talent:",
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Name", "Name *")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Name", ^.value := model.talentProfile.name, ^.onChange ==> updateTalentProfileName,
                ^.required := true)
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Title", "Title *")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Title", ^.value := model.talentProfile.title, ^.onChange ==> updateTalentProfileTitle,
                ^.required := true)
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Capabilities", "Capabilities *")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Capabilities", ^.value := model.talentProfile.capabilities, ^.onChange ==> updateTalentCapabilities,
                ^.required := true, ^.placeholder := "<Select top 10, ranked>")
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Video2", "Video")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Video2", ^.value := model.talentProfile.video, ^.onChange ==> updateTalentProfileVideo,
                ^.required := false, ^.placeholder := "A link to a video about yourself")
            )
          ),
          "// For Talent Profile:", <.br(),
          "// For Employer:",
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "EmployerName", "Employer Name *")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "EmployerName", /* ^.value := model.employerProfile.name, ^.onChange ==> updateEmployerProfileName,*/
                ^.required := true)
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Website", "Website")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Website", /*^.value := model.employerProfile.website, ^.onChange ==> updateEmployerProfileWebsite,*/
                ^.required := false)
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Tagline", "Tagline")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Tagline", /*^.value := model.employerProfile.tagline, ^.onChange ==> updateEmployerProfileTagline,*/
                ^.required := false, ^.placeholder := "Briefly describe your company")
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Video", "Video")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Video", /*^.value := model.employerProfile.video, ^.onChange ==> updateEmployerProfileVideo,*/
                ^.required := false, ^.placeholder := "A link to a video about your company")
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Twitter", "Twitter username")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Twitter",/*^.value := model.employerProfile.twitter, ^.onChange ==> updateEmployerProfileTwitter,*/
                ^.required := false, ^.placeholder := "@yourcompany")
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Logo", "Logo")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Logo",/*^.value := model.employerProfile.logo, ^.onChange ==> updateEmployerProfileLogo,*/
                ^.required := false, ^.placeholder := "<Choose File>")
            )
          ),
          "// For Moderator:",
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Name", "Name *")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Name", /*^.value := model.moderatorProfile.name, ^.onChange ==> updateModeratorProfileName,*/
                ^.required := true)
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Capabilities", "Capabilities *")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Capabilities", /*^.value := model.moderatorProfile.capabilities, ^.onChange ==> updateModeratorProfileCapabilities,*/
                ^.required := true, ^.placeholder := "<Select top 10, ranked>")
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Commission", "Commission *")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Commission", /*^.value := model.moderatorProfile.commission, ^.onChange ==> updateModeratorProfileCommission,*/
                ^.required := true, ^.placeholder := "Enter commission rate, 0% - 2%")
            )
          ),
          <.div(DashBoardCSS.Style.modalHeaderPadding, ^.className := "text-right")(
            <.button(^.tpe := "submit", ^.className := "btn",DashBoardCSS.Style.btnDefault, DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide, "Submit"),
            <.button(^.tpe := "button", ^.className := "btn",DashBoardCSS.Style.btnDefault, DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide, "Cancel")
          ),
          <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()
        )
      )
    }
  }

  private val component = ReactComponentB[Props]("UserSkillsModal")
    .initialState_P(p => State(new ProfilePostContent()))
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.postUserSkills) {
        scope.$.backend.hidemodal
      }
    })
    .build

  def apply(props: Props) = component(props)
}

