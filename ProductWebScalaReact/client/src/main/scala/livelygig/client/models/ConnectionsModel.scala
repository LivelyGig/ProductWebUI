package livelygig.client.models

import livelygig.shared.dtos.{Connection}

/**
  * Created by shubham.k on 1/12/2016.
  */
case class ConnectionsModel(sessionURI: String, connection: Connection,name: String, imgSrc: String)
/*
case class ConnectionProfileResponse(sessionURI: String, connection: Connection, jsonBlob: String ,
                                     jsonBlobModel: Option[JsonBlobModel])
  extends RequestContent

case class JsonBlobModel(name: String, imgSrc: String)

case class Connection (source: String, label: String, target: String)*/
