import sbt.Keys.name

/*
name := "quickstart"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies += "com.twitter" %% "finagle-http" % "18.8.0"

enablePlugins(JavaAppPackaging)
*/

lazy val `finagle-quickstart` = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.example",
      scalaVersion    := "2.12.6"
    )),
    name := "finagle-quickstart",
    libraryDependencies ++= Seq(
      "com.twitter" %% "finagle-http" % "18.8.0"
      ,"com.twitter" %% "finagle-redis" % "18.8.0"

    ),
    version := "1.0"
  ).enablePlugins(JavaAppPackaging)



