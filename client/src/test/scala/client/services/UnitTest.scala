package client.services

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by bhagyashree.b on 2016-05-18.
  */

abstract class UnitTest(component: String) extends FlatSpec with Matchers{
  behavior of component
}