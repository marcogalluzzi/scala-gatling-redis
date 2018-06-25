/**
  * Scenario session parameters
  */
object ScenarioParameters {
  /** Get the Expression Language string */
  def EL(s: String) =  "${"+s+"}"

  val MESSAGE_ID = "messageId"
  val IMAGE_NAME = "imageName"
  val ENCODED_USER_ID = "encodedUserId"
}