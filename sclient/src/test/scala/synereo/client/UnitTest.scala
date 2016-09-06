package synereo.client

import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

/**
  * Created by bhagyashree.b on 2016-05-18.
  */

abstract class UnitTest(component: String = "") extends FlatSpec with Matchers with GivenWhenThen{
  behavior of component
}