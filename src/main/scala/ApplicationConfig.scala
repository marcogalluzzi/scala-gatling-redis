import scala.concurrent.duration.FiniteDuration

object ApplicationConfig {

  case class HttpConfig (baseUrl: String, basePath: String, contentTypeHeader: String, acceptHeader: String,
                           acceptEncodingHeader: String, authHeaderKey: String, headers: Map[String, String])

  case class ConstantUsersPerSec(rate: Double, duration: FiniteDuration)
  case class UniformPause(min: FiniteDuration, max: FiniteDuration)
  case class Pause(notification: UniformPause, reading: UniformPause)
  case class ScenarioConfig (constantUsersPerSec: ConstantUsersPerSec,
                               pause: Pause)

  case class RedisConfig(host: String, port: Int, listKey: String)

  case class Config(http: HttpConfig, scenario: ScenarioConfig, redis: RedisConfig)

  val config = pureconfig.loadConfigOrThrow[Config]
}
