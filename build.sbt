name := "BakalaursAPI"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.mongodb" % "mongo-java-driver" % "2.8.0",
  javaJdbc,
  javaEbean,
  cache
)

play.Project.playJavaSettings
