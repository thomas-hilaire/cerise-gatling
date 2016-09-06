import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

object RunningConfig {

  val COMMON_PAUSE_DURATION = if (LocationUtils.isTest) 0 seconds else 4 seconds
  val WS_WAIT = if (LocationUtils.isTest) 5 seconds else 15 seconds
  val WS_HEARTHBEAT = 10 seconds

  val HTTP_PROTOCOL = http
		.baseURL(LocationUtils.BASE_URL)
		.wsBaseURL(LocationUtils.BASE_WS_URL)
		.wsReconnect
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.connectionHeader("keep-alive")
		.userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:20.0) Gecko/20100101 Firefox/20.0")
	  .contentTypeHeader("application/json;charset=UTF-8")

}