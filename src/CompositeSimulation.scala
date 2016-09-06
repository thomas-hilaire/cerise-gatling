import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import scala.concurrent.duration._
import RunningConfig._

class CompositeSimulation extends Simulation {

	LocationUtils.isTest match {
		case false => {
			setUp(
				new ConsultationSimulation().buildScenario.inject(
					nothingFor(1 seconds),
					rampUsers(10) over (1 minutes),
					rampUsers(150) over (5 minutes),
					constantUsersPerSec(5) during (9 minutes)
				)
				,
				new ConnectButDoNothingSimulation().buildScenario.inject(
					nothingFor(1 seconds),
					rampUsers(20) over (1 minutes),
					rampUsers(4000) over (5 minutes),
					constantUsersPerSec(15) during (9 minutes)
				)
			)
			.protocols(HTTP_PROTOCOL)
		}
		case true => {
			setUp(
				new ConsultationSimulation().buildScenario.inject(
					nothingFor(1 seconds),
					rampUsers(10) over (5 minutes),
					constantUsersPerSec(0.2) during (10 minutes)
				)
				,
				new ConnectButDoNothingSimulation().buildScenario.inject(
					nothingFor(1 seconds),
					rampUsers(10) over (5 minutes),
					constantUsersPerSec(0.2) during (9 minutes)
				)
			)
			.protocols(HTTP_PROTOCOL)
		}
	}

}