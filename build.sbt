name := """hacker-news-scraper-backend"""
organization := "com.joseguzman"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.16"

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test,
  "org.playframework" %% "play-test" % "3.0.7" % Test,
  "org.mockito" %% "mockito-scala-scalatest" % "1.17.37" % Test,
  "org.mockito" %% "mockito-scala" % "1.17.37" % Test,
  "net.ruippeixotog" %% "scala-scraper" % "3.1.3",
  "org.playframework" %% "play-json" % "3.0.4"
)