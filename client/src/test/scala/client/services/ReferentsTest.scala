package client.services

import shared.models.Referents

/**
  * Created by bhagyashree.b on 2016-05-20.
  */

class ReferentsTest extends UnitTest("ReferentsModel") {

  "Attributes of Referents" should "not be null " in {
  val referents = new Referents(" "," ")
  referents.referentId shouldNot be(null)
  referents.referentName shouldNot  be(null)
}

}
