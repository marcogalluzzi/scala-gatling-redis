import ApplicationConfig.config
import ScenarioParameters._
import io.circe.generic.auto._
import io.circe.syntax._
import io.gatling.core.Predef._
import io.gatling.http.Predef.http

object ActionReceiveNotification {
  case class Message(id: Int = 0, serverID: Int = 0, serverOrganizationFk: Int = 0, visibleFlag: Boolean = false,
                     readFlag: Boolean = false, receivedFlag: Boolean = false, numReads: Long = 0)

  val request = exec(
    http("Set message as received")
      .post(config.http.basePath + "/users/notification/" + EL(MESSAGE_ID))
      .body(StringBody(Message().asJson.noSpaces)).asJSON
      .header(config.http.authHeaderKey, EL(ENCODED_USER_ID))
  )

}

