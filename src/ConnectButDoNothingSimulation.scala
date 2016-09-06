import io.gatling.core.Predef._

class ConnectButDoNothingSimulation extends AbstractSimulation {

  override def buildScenario = {
    scenario(getClass.getName()).exitBlockOnFail(
      feed(csv(LocationUtils.csvFile).circular)
        .exec(Execs.LOGIN)
        .exec(Execs.GENERATE_AUTH_TOKEN)
        .exec(Execs.GET_USER_DETAILS)
        .exec(Execs.FIRST_SOCKETIO)
        .exec(Execs.WS_CONNECT)
        .pause(RunningConfig.WS_WAIT)
        .exec(Execs.WS_CLOSE)
        .exec(Execs.WS_CONNECT)
        .pause(RunningConfig.WS_WAIT)
        .exec(Execs.WS_CLOSE)
        .exec(Execs.WS_CONNECT)
        .pause(RunningConfig.WS_WAIT)
//        .exec(s => s.set("wsProbeCallCount", 1))
//        .during(RunningConfig.WS_WAIT, "Keep websocket connection open", false) (
//            .exec(Execs.WS_PING)
//            .pause(RunningConfig.WS_HEARTHBEAT)
//            .exec(s => s.set("wsProbeCallCount", s("wsProbeCallCount").as[Int] + 1))
//        )
        .exec(Execs.WS_CLOSE)
    )
  }
}