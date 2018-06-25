name := "scala-gatling-redis"

version := "0.1"

scalaVersion := "2.12.6"

enablePlugins(GatlingPlugin)

libraryDependencies ++= Seq(
  "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.3.1",
  "io.gatling" % "gatling-test-framework" % "2.3.1",
  "io.circe" %% "circe-core" % "0.9.3",
  "io.circe" %% "circe-generic" % "0.9.3",
  "io.circe" %% "circe-parser" % "0.9.3",
  "com.github.pureconfig" %% "pureconfig" % "0.9.1"
)
