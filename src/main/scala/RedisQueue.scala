import ApplicationConfig.config
import com.redis.RedisClientPool
import io.circe.generic.auto._
import io.circe.parser.decode

/**
  * Simulate a queue using a Redis list.
  * Only `pop` has been implemented since a push should be done by a server application.
  */
object RedisQueue {
  val redisClients = new RedisClientPool(config.redis.host, config.redis.port)

  case class RedisItem(messageId: Long, imageName: String, encodedUserId: String)

  def pop: Option[RedisItem] = {
    val listValue = redisClients.withClient {
      client => {
        client.lpop(config.redis.listKey)
      }
    }

    if (listValue.isDefined) {
      val eitherErrorOrItem = decode[RedisItem](listValue.get)
      if (eitherErrorOrItem.isRight) {
        return Option(eitherErrorOrItem.right.get)
      }
    }
    Option.empty
  }
}
