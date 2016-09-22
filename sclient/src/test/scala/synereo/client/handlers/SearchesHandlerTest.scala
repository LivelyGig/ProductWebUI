package synereo.client.handlers

import diode.ActionResult.ModelUpdate
import diode.RootModelRW
import shared.dtos.Connection
import shared.models.Label
import synereo.client.UnitTest
import synereo.client.rootmodels.{SearchesRootModel, SessionRootModel}

/**
  * Created by bhagyashree.b on 22-09-2016.
  */
class SearchesHandlerTest extends UnitTest("SearchesHandler") {
  val handler = new SearchesHandler(new RootModelRW(SearchesRootModel()))

  val labelStrSeq = Seq("label1", "label2","label3","label4")

  val label = Label("uid", "text", "color", "imgSrc", "parentUid",  false)

  val newCnxnSeq  = Seq(Connection("newSource1", "newLabel1", "newTarget1"),
    Connection("newSource2", "newLabel2", "newTarget2"),
    Connection("newSource3", "newLabel3", "newTarget3"))

  "CreateLabels" should "Created labels" in {
    val result = handler.handle(CreateLabels(labelStrSeq))
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue != null)
      case _ =>
        assert(false)
    }

  }

  "AddLabel" should "Added Labels" in {
    val result = handler.handle(AddLabel(label))
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue != null)
      case _ =>
        assert(false)
    }

  }
  "UpdatePrevSearchLabel" should "Update previous search Labels" in {
    val result = handler.handle(UpdatePrevSearchLabel("label"))
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue != null)
      case _ =>
        assert(false)
    }

  }
  "UpdatePrevSearchCnxn" should "Update previous search Connections" in {
    val result = handler.handle(UpdatePrevSearchCnxn(newCnxnSeq))
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue != null)
      case _ =>
        assert(false)
    }
  }
}
