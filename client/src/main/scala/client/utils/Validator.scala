package client.utils

import org.querki.jquery
import org.querki.jquery.JQuery

import scala.scalajs.js

/**
  * Created by bhagyashree.b on 4/28/2016.
  */

package object validator {
  implicit class SelectizeJquery(jq:JQuery) extends jquery.JQueryExtensions(jq){
    def validator(): js.Any = {
      jq.asInstanceOf[js.Dynamic].validator()
    }
  }
}

