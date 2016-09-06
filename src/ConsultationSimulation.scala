import java.nio.charset.StandardCharsets._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.Predef.jsonPath
import Execs._
import RunningConfig._
import io.gatling.http.response.{StringResponseBody, ResponseWrapper}

class ConsultationSimulation extends AbstractSimulation {

	override def buildScenario = {
	  scenario(getClass.getName()).exitBlockOnFail(
			feed(csv(LocationUtils.csvFile).circular)

			.exec(Execs.LOGIN)
			.exec(Execs.GENERATE_AUTH_TOKEN)
			.exec(Execs.GET_USER_DETAILS)
			.exec(Execs.FIRST_SOCKETIO)
			//.exec(Execs.WS_CONNECT)

			.exec(http("Search for chat personnal conversation")
				.get("/chat/api/chat/me/conversation")
				.check(status.is(200)))

			.exec(http("Search for domain details")
				.get("/api/domains/${domainId}")
				.check(status.is(200)))

			.exec(http("Search for user roles")
				.get("/cerise/api/user/roles")
				.check(status.is(200)))
/*
			.exec(http("TO-VERIFY Get user events, expect err 404")
				.get("/dav/api/calendars/${userId}.json")
				.check(status.in(404, 500)))
*/
			.exec(http("Get user's unread notifications")
				.get("/api/user/notifications/unread")
				.check(status.is(200)))

			.exec(http("Get causes by trends")
				.get("/cerise/api/causes?filter=trending")
				.check(status.is(200)))
			.pause(COMMON_PAUSE_DURATION)

			.exec(http("Get more causes, to 10")
				.get("/cerise/api/causes?limit=5&offset=5")
				.check(status.is(200)))
			.pause(COMMON_PAUSE_DURATION)

			.exec(http("Get more causes, to 15")
				.get("/cerise/api/causes?limit=5&offset=10")
				.check(status.is(200)))

			.exec(http("Get more causes, to 20")
				.get("/cerise/api/causes?limit=5&offset=15")
				.check(status.is(200)))

			.exec(http("Get more causes, to 30")
				.get("/cerise/api/causes?limit=10&offset=20")
				.check(status.is(200)))
			.pause(COMMON_PAUSE_DURATION)

			.exec(http("Get categories")
				.get("/cerise/api/categories")
				.check(status.is(200)))

			.exec(http("Get cause details")
				.get("/cerise/api/causes/${causeId}")
				.check(status.is(200)))

			.exec(http("Get cause proposals")
				.get("/cerise/api/causes/${causeId}/proposals")
				.check(
					status.is(200),
					jsonPath("$[0]._id").find.saveAs("proposalId")
				))

			.exec(http("Get cause actions")
				.get("/cerise/api/causes/${causeId}/actions")
				.check(status.is(200)))
			.pause(COMMON_PAUSE_DURATION)

			.exec(http("Get proposal details")
				.get("/cerise/api/causes/${causeId}/proposals/${proposalId}")
				.check(status.is(200)))

			.exec(http("Get proposal votes")
				.get("/cerise/api/causes/${causeId}/proposals/${proposalId}/votes?expandUser=true&limit=10&offset=0")
				.check(status.is(200)))
			.pause(COMMON_PAUSE_DURATION)

			//.exec(Execs.WS_CLOSE)

		)
	}
}