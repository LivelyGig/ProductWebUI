package client

/**
  * Created by shubham.k on 14-09-2016.
  */
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}


abstract class UnitTest(component: String) extends FlatSpec with Matchers with GivenWhenThen{
  behavior of component
}


