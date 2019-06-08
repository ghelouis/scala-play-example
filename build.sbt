name := """scala-play-example"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

crossScalaVersions := Seq("2.12.8", "2.11.12")

libraryDependencies += guice
libraryDependencies += "io.swagger" %% "swagger-play2" % "1.7.1"
libraryDependencies += "org.webjars" % "swagger-ui" % "3.22.2"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "3.0.0"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0"
libraryDependencies += "com.h2database" % "h2" % "1.4.199"


libraryDependencies += "org.mockito" % "mockito-all" % "1.9.5" % Test
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.2" % Test