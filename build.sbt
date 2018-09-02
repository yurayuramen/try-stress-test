name := """try-stress-test"""

organization := "com.example"

version := "1.0-SNAPSHOT"

//lazy val root = (project in file(".")).enablePlugins(PlayScala)

lazy val `gatling` = project

lazy val `play-quickstart` = project.aggregate(gatling).dependsOn(gatling)

lazy val `akka-http-quickstart` = project

lazy val `finagle-quickstart` = project

