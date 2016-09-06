import io.gatling.core.Predef._
import io.gatling.http.Predef._

object LocationUtils {

	val isTest = if (System.getenv("TEST") == null) true else System.getenv("TEST").toBoolean
	System.out.println("Mode production is " + !isTest)

	def url(test: String, prod: String) = if (isTest) test else prod

	val csvFile = url("credentials.csv", "credentials-prod.csv")

	val BASE_URL = url("http://DEV_SERVER", "https://PROD_SERVER")
	val BASE_WS_URL = url("ws://DEV_SERVER", "wss://PROD_SERVER")
}

object Execs {

	val LOGIN = http("Log user in")
			.post("/api/login")
			.body(StringBody("""{
				 |"username":"${username}",
				 |"password":"${password}",
				 |"rememberme":false,
				 |"recaptcha":{"needed":false,"data":null}
				 |}""".stripMargin))
			.check(status.is(200))


	val GENERATE_AUTH_TOKEN = http("Generate auth token")
			.get("/api/authenticationtoken")
			.check(
				status.is(200),
				jsonPath("$.token").find.saveAs("authToken")
			)

	val GET_USER_DETAILS = http("Search for user details")
			.get("/api/user?_=1472977648415")
			.check(
				status.is(200),
				jsonPath("$.domains[0].domain_id").find.saveAs("domainId"),
				jsonPath("$._id").find.saveAs("userId")
			)

	val FIRST_SOCKETIO = http("First socket.io request")
			.get("/socket.io/")
			.queryParam("token", "${authToken}")
			.queryParam("user", "${userId}")
			.queryParam("EIO", "3")
			.queryParam("transport", "polling")
			.queryParam("t", "1473008003681-0")
			.check(
				status.is(200),
				headerRegex("Set-Cookie", "io=(.*)").saveAs("sid")
			)

	val WS_CONNECT = ws("Connect WS")
			.open("/socket.io/")
			.header("Sec-WebSocket-Extensions", "permessage-deflate; client_max_window_bits")
			.queryParam("token", "${authToken}")
			.queryParam("user", "${userId}")
			.queryParam("EIO", "3")
			.queryParam("sid", "${sid}")
			.queryParam("transport", "websocket")

	val WS_PING = ws("Ping WS").sendText(" ${wsProbeCallCount}probe")

	val WS_CLOSE = ws("Close WS").close
}