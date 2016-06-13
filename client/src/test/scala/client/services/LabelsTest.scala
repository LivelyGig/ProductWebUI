package client.services

import client.utils.{LabelsUtils}
import org.scalatest.GivenWhenThen
import shared.models.Label

/**
  * Created by shubham.k on 01-06-2016.
  */
class LabelsTest extends UnitTest("Utils") with GivenWhenThen {
 "builProlog"  must "return each([label1],[label2])" in {
   Given("Seq(label1, label2) and prolog type each")
   val labelSeq = Seq(Label(text = "label1"), Label(text = "label2"))
   val queryType = LabelsUtils.PrologTypes.Each
   assert(LabelsUtils.buildProlog(labelSeq, queryType)==="each([label1],[label2])")
 }

}
