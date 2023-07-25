ThisBuild / organization := "com.siriusxm"
ThisBuild / scalaVersion := "3.2.2"

lazy val root = (project in file(".")).settings(
  name := "shopping-cart",
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-effect"         % "3.5.0",
    "org.typelevel" %% "munit-cats-effect-3" % "1.0.7" % Test
  )
)
