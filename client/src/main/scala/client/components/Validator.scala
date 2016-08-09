package client.components

import org.querki.jquery.JQuery
import org.querki.jsext.{JSOptionBuilder, OptMap}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName
import org.querki.jsext._
import scala.language.implicitConversions
import scala.scalajs.js.Any
/**
  * Created by bhagyashree.b on 2016-06-16.
  */

object Validator {

@JSName("Validator")
@js.native
trait ValidatorJQuery extends js.Object {
  def validator(options: ValidatorOptions):Any = js.native
// def popover(options: PopoverOptions):Any = js.native
}


@js.native
trait ValidatorOptions extends js.Object
class ValidatorOptionBuilder(val dict:OptMap) extends JSOptionBuilder[ValidatorOptions, ValidatorOptionBuilder](new ValidatorOptionBuilder(_)) {
  def html(v:Boolean) = jsOpt("html", v)
  def validate() = jsOpt("",Any)
}
object ValidatorOptions extends ValidatorOptionBuilder(noOpts)


implicit def jq2validator(jq: JQuery): ValidatorJQuery = jq.asInstanceOf[ValidatorJQuery]

//  @JSName("Validator")
//  def validate(/*options: ValidatorOptions*/):Any = js.native
}