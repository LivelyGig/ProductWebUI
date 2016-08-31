package synereo.client.mockdata

/**
  * Created by shubham.k on 31-08-2016.
  */
object MockData {
    def getMessagesResponse(): String = {
      "[{\"msgType\":\"evalSubscribeResponse\",\"content\":{\"sessionURI\":\"agent-session://ff03586051a338dd7577f8d99cd661a0dbea/c9a4ad18b45a69b8595e7b6fff35dfea619f379f03100d35c7fb5fd88de1b64c\",\"pageOfPosts\":[\"{\\\"$type\\\":\\\"shared.models.MessagePost\\\",\\\"uid\\\":\\\"8f3880c4389b41a0bc73bc9b4318a508\\\",\\\"created\\\":\\\"2016-08-31T09:32:32.464Z\\\",\\\"modified\\\":\\\"2016-08-31T09:32:32.464Z\\\",\\\"connections\\\":[{\\\"source\\\":\\\"alias://ff03586051a338dd7577f8d99cd661a0dbea/alias\\\",\\\"label\\\":\\\"2a5212ae-4361-417e-802d-813c2ee51c68\\\",\\\"target\\\":\\\"alias://8feadc4e8d12171a6d6c409ead4fd9a1aca6/alias\\\"},{\\\"source\\\":\\\"agent://ff03586051a338dd7577f8d99cd661a0dbea\\\",\\\"label\\\":\\\"alias\\\",\\\"target\\\":\\\"agent://ff03586051a338dd7577f8d99cd661a0dbea\\\"}],\\\"postContent\\\":{\\\"$type\\\":\\\"shared.models.MessagePostContent\\\",\\\"text\\\":\\\"and some content\\\",\\\"subject\\\":\\\"some tyext \\\"}}\"],\"connection\":{\"source\":\"agent://ff03586051a338dd7577f8d99cd661a0dbea\",\"label\":\"alias\",\"target\":\"agent://ff03586051a338dd7577f8d99cd661a0dbea\"},\"filter\":\"all([MESSAGEPOSTLABEL])\"}}]"
    }
}
