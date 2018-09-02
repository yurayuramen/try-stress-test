//import Dependencies._

lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"

lazy val gatling = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.5",
      version      := "0.1.0"
    )),
    name := "gatling",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.3.1",
      libraryDependencies += "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.1.10"
  ) //.enablePlugins(JavaAppPackaging)
