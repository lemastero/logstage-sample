import com.github.pshirshov.izumi.sbt.deps.Izumi.R
import com.github.pshirshov.izumi.sbt.deps.IzumiDeps

name := "scala-ua-2019"

version := "0.1"

scalaVersion := "2.12.8"

organization in ThisBuild := "com.ratoshniuk"

resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.9")

// LogStage machinery
libraryDependencies ++= Seq(
  R.fundamentals_bio,
  IzumiDeps.R.zio_core,
  IzumiDeps.R.zio_interop
)
