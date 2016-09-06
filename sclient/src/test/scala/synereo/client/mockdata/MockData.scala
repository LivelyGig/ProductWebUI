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

}
