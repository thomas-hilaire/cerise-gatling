import scala.tools.nsc.io.File

object IDEPathHelper {

	val gatlingConfUrl = getClass.getClassLoader.getResource("gatling.conf").getPath
	val projectRootDir = File(gatlingConfUrl).parents(1)

	val ideSimulationsDirectoryPath = projectRootDir / "bin"
	
	val mavenSourcesDirectoryPath = projectRootDir / "src"
	val mavenResourcesDirectoryPath = projectRootDir / "resources"
	val mavenTargetDirectoryPath = projectRootDir / "target"
	val mavenBinariesDirectoryPath = mavenTargetDirectoryPath / "classes"

	val dataDirectoryPath = mavenResourcesDirectoryPath / "data"
	val requestBodiesDirectoryPath = mavenResourcesDirectoryPath / "request-bodies"

	val recorderOutputDirectoryPath = mavenSourcesDirectoryPath
	val resultsDirectoryPath = mavenTargetDirectoryPath / "gatling-results"
}