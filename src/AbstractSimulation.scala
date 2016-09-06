import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.core.Predef._
import scala.concurrent.duration._
import RunningConfig._

abstract class AbstractSimulation extends Simulation {

  def buildScenario: ScenarioBuilder

  LocationUtils.isTest match {
    case false => {
      setUp(buildScenario.inject(
        nothingFor(1 seconds),
        atOnceUsers(1)))
        .protocols(HTTP_PROTOCOL)
    }
    case true => {
      setUp(buildScenario.inject(
        nothingFor(1 seconds),
        atOnceUsers(1)))
      .protocols(HTTP_PROTOCOL)
    }
  }

}