ThisBuild / organization := "com.siriusxm"
ThisBuild / scalaVersion := "3.2.2"

lazy val root = (project in file(".")).settings(
  name := "shopping-cart",
  libraryDependencies ++= Seq(
    "org.typelevel"                 %% "cats-effect"         % "3.5.0",
    "com.softwaremill.sttp.client3" %% "core"                % "3.8.16",
    "com.softwaremill.sttp.client3" %% "cats"                % "3.8.16",
    "com.softwaremill.sttp.client3" %% "circe"               % "3.8.16",
    "io.circe"                      %% "circe-generic"       % "0.14.1",
    "org.typelevel"                 %% "munit-cats-effect-3" % "1.0.7" % Test
  )
)
