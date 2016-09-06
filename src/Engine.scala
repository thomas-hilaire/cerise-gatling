import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder

object Engine extends App {

	val props = new GatlingPropertiesBuilder
	props.dataDirectory(IDEPathHelper.dataDirectoryPath.toString)
	props.sourcesDirectory(IDEPathHelper.ideSimulationsDirectoryPath.toString)
	props.resultsDirectory(IDEPathHelper.resultsDirectoryPath.toString)
	props.bodiesDirectory(IDEPathHelper.requestBodiesDirectoryPath.toString)
	props.binariesDirectory(IDEPathHelper.ideSimulationsDirectoryPath.toString)

	Gatling.fromMap(props.build)
}
