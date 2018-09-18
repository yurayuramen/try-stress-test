name := """my-tiny-play"""
//organization := "com.example"

//version := "1.0-SNAPSHOT"

//lazy val root = (project in file(".")).enablePlugins(PlayScala)

//バックエンドをakka-httpにする場合
//lazy val `play-quickstart` = (project in file(".")).enablePlugins(PlayScala)

//バックエンドをnettyにする場合
lazy val `play-quickstart` = (project in file(".")).enablePlugins(PlayScala, PlayNettyServer).disablePlugins(PlayAkkaHttpServer) //.aggregate(gatling).dependsOn(gatling)

scalaVersion := "2.12.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "net.debasishg" %% "redisclient" % "3.7"
libraryDependencies += "com.twitter" %% "finagle-redis" % "18.8.0"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"



javaOptions in Test += "-Dconfig.file=./conf/test.conf"
