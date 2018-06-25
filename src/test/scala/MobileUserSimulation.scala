import ScenarioParameters._
import ApplicationConfig.config
import io.gatling.core.Predef._
import io.gatling.http.Predef.http

/**
  * Simulate the behaviour or mobile users of a specific mobile app when a Push Notification is received.
  */
class MobileUserSimulation extends Simulation {
  
  val httpConfiguration = http
    .baseURL(config.http.baseUrl)
    .contentTypeHeader(config.http.contentTypeHeader)
    .acceptHeader(config.http.acceptHeader)
    .acceptEncodingHeader(config.http.acceptEncodingHeader)
    .headers(config.http.headers)

  val theScenarioBuilder = scenario("PushNotification")
    .exec(session => {
      val item = RedisQueue.pop

      if (item.isDefined)
        session
          .set(MESSAGE_ID, item.get.messageId)
          .set(IMAGE_NAME, item.get.imageName)
          .set(ENCODED_USER_ID, item.get.encodedUserId)
      else session
    })
    .doIf("${"+MESSAGE_ID+".exists()}") {
      exec(
        ActionReceiveNotification.request,
        pause(config.scenario.pause.notification.min, config.scenario.pause.notification.max),

        ActionReadMessage.request,
        pause(config.scenario.pause.reading.min, config.scenario.pause.reading.max),

        ActionListFollowedOrganizations.request
      )
    }

  setUp(
    theScenarioBuilder.inject(
      constantUsersPerSec(config.scenario.constantUsersPerSec.rate)
        during config.scenario.constantUsersPerSec.duration)
  ).protocols(httpConfiguration)
}




