package synereo.client.mockdata

/**
  * Created by shubham.k on 31-08-2016.
  */
object MockData {
  val messagesResponse =
    "[{\"msgType\":\"evalSubscribeResponse\",\"content\":{\"sessionURI\":\"agent-session://ff03586051a338dd7577f8d99cd661a0dbea/c9a4ad18b45a69b8595e7b6fff35dfea619f379f03100d35c7fb5fd88de1b64c\",\"pageOfPosts\":[\"{\\\"$type\\\":\\\"shared.models.MessagePost\\\",\\\"uid\\\":\\\"8f3880c4389b41a0bc73bc9b4318a508\\\",\\\"created\\\":\\\"2016-08-31T09:32:32.464Z\\\",\\\"modified\\\":\\\"2016-08-31T09:32:32.464Z\\\",\\\"connections\\\":[{\\\"source\\\":\\\"alias://ff03586051a338dd7577f8d99cd661a0dbea/alias\\\",\\\"label\\\":\\\"2a5212ae-4361-417e-802d-813c2ee51c68\\\",\\\"target\\\":\\\"alias://8feadc4e8d12171a6d6c409ead4fd9a1aca6/alias\\\"},{\\\"source\\\":\\\"agent://ff03586051a338dd7577f8d99cd661a0dbea\\\",\\\"label\\\":\\\"alias\\\",\\\"target\\\":\\\"agent://ff03586051a338dd7577f8d99cd661a0dbea\\\"}],\\\"postContent\\\":{\\\"$type\\\":\\\"shared.models.MessagePostContent\\\",\\\"text\\\":\\\"and some content\\\",\\\"subject\\\":\\\"some tyext \\\"}}\"],\"connection\":{\"source\":\"agent://ff03586051a338dd7577f8d99cd661a0dbea\",\"label\":\"alias\",\"target\":\"agent://ff03586051a338dd7577f8d99cd661a0dbea\"},\"filter\":\"all([MESSAGEPOSTLABEL])\"}}]"
  val cnxnRes =
    "[{\"msgType\":\"connectionProfileResponse\",\"content\":{\"sessionURI\":\"agent-session://ff03586051a338dd7577f8d99cd661a0dbea/c5e007bca9000f11742eec9fe2091c1966c8a6a640868a4c6956ba998c68f04d\",\"connection\":{\"source\":\"alias://ff03586051a338dd7577f8d99cd661a0dbea/alias\",\"label\":\"8a3846e4-e3de-4147-a4f5-4e27b3445c94\",\"target\":\"alias://727587f490da782f52bb9bf4b837f22519aa/alias\"},\"jsonBlob\":\"{\\\"name\\\":\\\"c\\\"}\"}}]"
  val introNot =
    "[{\n\t\t\"msgType\": \"introductionNotification\",\n\t\t\"content\": {\n\t\t\t\"introSessionId\": \"ea7b728e-a1aa-4a44-971b-416b709af1f8\",\n\t\t\t\"correlationId\": \"0daf131d-c95e-4789-89f1-93d1d96bdd1f\",\n\t\t\t\"connection\": {\n\t\t\t\t\"source\": \"alias://344338c5db29c231d320d3d9a35a27d0a7c9/alias\",\n\t\t\t\t\"label\": \"3e94e571-2c53-4658-9ca4-74e32ddcd1e3\",\n\t\t\t\t\"target\": \"alias://ff03586051a338dd7577f8d99cd661a0dbea/alias\"\n\t\t\t},\n\t\t\t\"message\": \"Hi jon & user,\\nHere's an introduction for the two of you to connect.\\nwith love,\\nNodeAdmin QueenSplicious\",\n\t\t\t\"introProfile\": \"{\\\"name\\\":\\\"user\\\"}\"\n\t\t}\n\t}]"
  val cnctNot =
    "[{\n\t\t\"msgType\": \"connectNotification\",\n\t\t\"content\": {\n\t\t\t\"connection\": {\n\t\t\t\t\"source\": \"alias://84aadc278ae36f3058147cb957441e1bbd96/alias\",\n\t\t\t\t\"label\": \"2d1767c2-8e56-48a6-95d4-838e0129cecf\",\n\t\t\t\t\"target\": \"alias://344338c5db29c231d320d3d9a35a27d0a7c9/alias\"\n\t\t\t},\n\t\t\t\"introProfile\": \"{\\\"name\\\":\\\"jon\\\"}\"\n\t\t}\n\t}]"
  val mixedReponse = "[{\"msgType\":\"evalSubscribeResponse\",\"content\":{\"sessionURI\":\"agent-session://ff03586051a338dd7577f8d99cd661a0dbea/c9a4ad18b45a69b8595e7b6fff35dfea619f379f03100d35c7fb5fd88de1b64c\",\"pageOfPosts\":[\"{\\\"$type\\\":\\\"shared.models.MessagePost\\\",\\\"uid\\\":\\\"8f3880c4389b41a0bc73bc9b4318a508\\\",\\\"created\\\":\\\"2016-08-31T09:32:32.464Z\\\",\\\"modified\\\":\\\"2016-08-31T09:32:32.464Z\\\",\\\"connections\\\":[{\\\"source\\\":\\\"alias://ff03586051a338dd7577f8d99cd661a0dbea/alias\\\",\\\"label\\\":\\\"2a5212ae-4361-417e-802d-813c2ee51c68\\\",\\\"target\\\":\\\"alias://8feadc4e8d12171a6d6c409ead4fd9a1aca6/alias\\\"},{\\\"source\\\":\\\"agent://ff03586051a338dd7577f8d99cd661a0dbea\\\",\\\"label\\\":\\\"alias\\\",\\\"target\\\":\\\"agent://ff03586051a338dd7577f8d99cd661a0dbea\\\"}],\\\"postContent\\\":{\\\"$type\\\":\\\"shared.models.MessagePostContent\\\",\\\"text\\\":\\\"and some content\\\",\\\"subject\\\":\\\"some tyext \\\"}}\"],\"connection\":{\"source\":\"agent://ff03586051a338dd7577f8d99cd661a0dbea\",\"label\":\"alias\",\"target\":\"agent://ff03586051a338dd7577f8d99cd661a0dbea\"},\"filter\":\"all([MESSAGEPOSTLABEL])\"}},{\"msgType\":\"connectionProfileResponse\",\"content\":{\"sessionURI\":\"agent-session://ff03586051a338dd7577f8d99cd661a0dbea/c5e007bca9000f11742eec9fe2091c1966c8a6a640868a4c6956ba998c68f04d\",\"connection\":{\"source\":\"alias://ff03586051a338dd7577f8d99cd661a0dbea/alias\",\"label\":\"8a3846e4-e3de-4147-a4f5-4e27b3445c94\",\"target\":\"alias://727587f490da782f52bb9bf4b837f22519aa/alias\"},\"jsonBlob\":\"{\\\"name\\\":\\\"c\\\"}\"}},{\n\t\t\"msgType\": \"introductionNotification\",\n\t\t\"content\": {\n\t\t\t\"introSessionId\": \"ea7b728e-a1aa-4a44-971b-416b709af1f8\",\n\t\t\t\"correlationId\": \"0daf131d-c95e-4789-89f1-93d1d96bdd1f\",\n\t\t\t\"connection\": {\n\t\t\t\t\"source\": \"alias://344338c5db29c231d320d3d9a35a27d0a7c9/alias\",\n\t\t\t\t\"label\": \"3e94e571-2c53-4658-9ca4-74e32ddcd1e3\",\n\t\t\t\t\"target\": \"alias://ff03586051a338dd7577f8d99cd661a0dbea/alias\"\n\t\t\t},\n\t\t\t\"message\": \"Hi jon & user,\\nHere's an introduction for the two of you to connect.\\nwith love,\\nNodeAdmin QueenSplicious\",\n\t\t\t\"introProfile\": \"{\\\"name\\\":\\\"user\\\"}\"\n\t\t}\n\t},{\n\t\t\"msgType\": \"connectNotification\",\n\t\t\"content\": {\n\t\t\t\"connection\": {\n\t\t\t\t\"source\": \"alias://84aadc278ae36f3058147cb957441e1bbd96/alias\",\n\t\t\t\t\"label\": \"2d1767c2-8e56-48a6-95d4-838e0129cecf\",\n\t\t\t\t\"target\": \"alias://344338c5db29c231d320d3d9a35a27d0a7c9/alias\"\n\t\t\t},\n\t\t\t\"introProfile\": \"{\\\"name\\\":\\\"jon\\\"}\"\n\t\t}\n\t},{\"msgType\":\"balanceChanged\",\"content\":{\"sessionURI\":\"agent-session://ff03586051a338dd7577f8d99cd661a0dbea/c5e007bca9000f11742eec9fe2091c1966c8a6a640868a4c6956ba998c68f04d\", \"address\":\"testAddress\", \"tx\":\"testtx\", \"prevBalance\":\"testprevBalance\", \"newBalance\":\"testnewBalance\"}}]"

  val balChanged = "[{\"msgType\":\"balanceChanged\",\"content\":{\"sessionURI\":\"agent-session://ff03586051a338dd7577f8d99cd661a0dbea/c5e007bca9000f11742eec9fe2091c1966c8a6a640868a4c6956ba998c68f04d\", \"address\":\"testAddress\", \"tx\":\"testtx\", \"prevBalance\":\"testprevBalance\", \"newBalance\":\"testnewBalance\"}}]"

  val selectizeInputId = "connectionsSelectizeInputId"

  val initializeSessionResponse = "[{\n\t\"msgType\": \"initializeSessionResponse\",\n\t\"content\": {\n\t\t\"sessionURI\": \"agent-session://ff03586051a338dd7577f8d99cd661a0dbea/344a925a8f848b0c2f1c314c1ae9995410a0c00b4bcdfcb9c84042dad1815bf6\",\n\t\t\"listOfAliases\": [\"alias\"],\n\t\t\"defaultAlias\": \"alias\",\n\t\t\"listOfLabels\": [\"leaf(text(\\\"Label\\\"), display(color(\\\"\\\"), image(\\\"\\\")))\"],\n\t\t\"listOfConnections\": [{\n\t\t\t\"source\": \"alias://ff03586051a338dd7577f8d99cd661a0dbea/alias\",\n\t\t\t\"label\": \"3e827cf6-0be2-4f19-883f-50bc78957ff3\",\n\t\t\t\"target\": \"alias://3a5343e917f79c68111ca028e775bf1c259e/alias\"\n\t\t}],\n\t\t\"lastActiveLabel\": \"\",\n\t\t\"jsonBlob\": {\n\t\t\t\"name\": \"NodeAdmin QueenSplicious\",\n\t\t\t\"imgSrc\": \"data:image/png;base64,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\"\n\t\t},\n\t\t\"M2\": \"5d15f3b2254f885086e10289265e1ad2438510f5cca7619b94d142f4cbb5c9d0a9ba6c92087526d83deae616cd91ee25d038649fb9a253d6c42a2ac8beb54704\"\n\t}\n}]"

}
