name := """play-titan-demo"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaEbean,
  "com.thinkaurelius.titan" % "titan-core" % "0.5.0",
  "com.thinkaurelius.titan" % "titan-es" % "0.5.0",
  "com.thinkaurelius.titan" % "titan-cassandra" % "0.5.0",
  "com.tinkerpop" % "frames" % "2.5.0"
)
