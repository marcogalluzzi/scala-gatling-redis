import ApplicationConfig.config
import ScenarioParameters._
import Utils.getCurrentUTCTimeString
import io.circe.generic.auto._
import io.circe.syntax._
import io.gatling.core.Predef._
import io.gatling.http.Predef.http

object ActionReadMessage {

  case class MessageInfo(msgId: Long = 0, numReadsDelta: Long = 0, received: Boolean = true,
                         receivedDate: String = getCurrentUTCTimeString)

  val setMessageAsRead = exec(
    http("Mark message as read")
      .post(config.http.basePath + "/users/v2/reading/" + EL(MESSAGE_ID))
      .body(StringBody(session =>
        session(MESSAGE_ID).validate[Long].map(x => MessageInfo(x).asJson.noSpaces))).asJSON
      .header(config.http.authHeaderKey, EL(ENCODED_USER_ID))

  )

  val getImage = exec(
    http("Get image of message")
      .get(config.http.basePath + "/images/messages")
      .queryParamMap(Map("name" -> EL(IMAGE_NAME), "width" -> "1000", "height" -> "1000", "x-kon-appid" -> "1"))
      .header(config.http.authHeaderKey, EL(ENCODED_USER_ID))
  )

  val request = exec(
    setMessageAsRead,
    getImage
  )

}
