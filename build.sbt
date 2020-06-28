name := "deequ-tests"
version := "0.1"
scalaVersion := "2.11.12"
libraryDependencies += "com.amazon.deequ" % "deequ" % "1.0.4"
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.5" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.5" % "provided"
assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
assemblyJarName in assembly := s"${name.value}_${scalaVersion.value}-${version.value}.jar"