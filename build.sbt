name := """play-titan-demo"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final",
  "com.thinkaurelius.titan" % "titan-core" % "0.5.0",
  "com.thinkaurelius.titan" % "titan-es" % "0.5.0",
  "com.thinkaurelius.titan" % "titan-cassandra" % "0.5.0",
  "com.tinkerpop" % "frames" % "2.5.0"
)
