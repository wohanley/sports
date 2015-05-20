import NativePackagerKeys._

packageArchetype.java_application

name := """sports"""
version := "1.0"

scalaVersion := "2.11.6"

scalacOptions += "-deprecation"

libraryDependencies ++= Seq(
  "com.wohanley" %% "robots" % "0.1.0-SNAPSHOT"
)
