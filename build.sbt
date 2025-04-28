name := """hacker-news-scraper-backend"""
organization := "com.joseguzman"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.16"

dependencyOverrides ++= Seq(
  "com.typesafe.play" %% "twirl-api" % "1.5.1",
  "org.scala-lang.modules" %% "scala-xml" % "1.2.0"
)

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test,
  "org.playframework" %% "play-test" % "3.0.7" % Test,
  "org.mockito" %% "mockito-scala-scalatest" % "1.17.37" % Test,
  "org.mockito" %% "mockito-scala" % "1.17.37" % Test,
  "net.ruippeixotog" %% "scala-scraper" % "3.1.3",
  "org.playframework" %% "play-json" % "3.0.4",
  "com.iheart" %% "play-swagger" % "1.0.2"
)