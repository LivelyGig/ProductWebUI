package client.services

import client.utils.{LabelsUtils}
import org.scalatest.GivenWhenThen
import shared.models.LabelModel

/**
  * Created by shubham.k on 01-06-2016.
  */
class LabelsUtilsTest extends UnitTest("Utils") with GivenWhenThen {
 "builProlog"  must "return each([label1],[label2])" in {
   Given("Seq(label1, label2) and prolog type each")
   val labelSeq = Seq(LabelModel(text = "label1"), LabelModel(text = "label2"))
   val queryType = LabelsUtils.PrologTypes.Each
   assert(LabelsUtils.buildProlog(labelSeq, queryType)==="each([label1],[label2])")
 }

}
