name := """timeline"""
maintainer := "guchounongyin@gmail.com"
organization := "com.github.gcnyin"

version := "0.1.0"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .disablePlugins(PlayFilters)

scalaVersion := "2.13.6"

libraryDependencies ++= Seq(
  guice,
  caffeine,
  "org.typelevel" %% "cats-core" % "2.6.1",
  "joda-time" % "joda-time" % "2.10.10",
  "org.springframework.security" % "spring-security-crypto" % "5.5.0",
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "org.flywaydb" %% "flyway-play" % "7.10.0",
  "org.postgresql" % "postgresql" % "42.2.21" % Runtime,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
)

import com.typesafe.sbt.packager.docker.{DockerChmodType, DockerPermissionStrategy}

dockerBaseImage := "openjdk:11-jre"
dockerChmodType := DockerChmodType.UserGroupWriteExecute
dockerPermissionStrategy := DockerPermissionStrategy.CopyChown
dockerExposedPorts ++= Seq(9000)
dockerUpdateLatest := true

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.github.gcnyin.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.github.gcnyin.binders._"
