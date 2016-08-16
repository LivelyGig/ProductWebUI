package client.rootmodel

import shared.dtos.Introduction

/**
  * Created by mandar.k on 7/19/2016.
  */
case  class IntroRootModel(introResponse:Seq[Introduction]) {
  def updated(newIntroRes: Introduction) = {
    introResponse.indexWhere(_.connection.target == newIntroRes.connection.target) match {
      case -1 =>
        IntroRootModel(introResponse :+ newIntroRes)
      case target =>
        IntroRootModel(introResponse.updated(target, newIntroRes))
    }
  }
}

