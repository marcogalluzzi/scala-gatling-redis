import ApplicationConfig.config
import ScenarioParameters._
import io.gatling.core.Predef._
import io.gatling.http.Predef.http

object ActionListFollowedOrganizations {

  val request = exec(
    http("Get followed organizations")
      .get(config.http.basePath + "/organizations/my")
      .header(config.http.authHeaderKey, EL(ENCODED_USER_ID))
  )
}