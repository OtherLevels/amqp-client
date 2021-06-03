name := "amqp-client"

organization := "com.github.sstone"
 
version := "1.7-SNAPSHOT"
 
scalaVersion := "2.11.7"

scalacOptions  ++= Seq("-feature", "-language:postfixOps")

// Note we use 'externalResolvers' and not 'resolvers' because resolvers are
// merged into externalResolvers which already contains a list of default
// repositories we do not want. Let's overwrite them with Nexus instead.
// Ref: http://www.scala-sbt.org/release/docs/Library-Dependencies.html
externalResolvers := Seq(
  "local"               at "file://"+Path.userHome.absolutePath+"/.ivy2/local",
  "snapshots"           at "https://nexus.awsotherlevels.com/repository/maven-ol-snapshots/",
  "releases"            at "https://nexus.awsotherlevels.com/repository/maven-ol-releases/",
  "maven-public"        at "https://nexus.awsotherlevels.com/repository/maven-public/",
  "ivy-releases"        at "https://nexus.awsotherlevels.com/repository/ivy-releases/"
)
// Nexus
publishTo := {
  val nexus = "https://nexus.awsotherlevels.com/repository/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "maven-ol-snapshots/")
  else
    Some("releases"  at nexus + "maven-ol-releases/")
}
credentials += Credentials(Path.userHome / ".sbt" / ".credentials")
 
resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies <<= scalaVersion { scala_version => 
    val akkaVersion   = "2.3.11"
    Seq(
        "com.rabbitmq"         % "amqp-client"          % "5.12.0",
        "com.typesafe.akka"    %% "akka-actor"          % akkaVersion % "provided",
        "com.typesafe.akka"    %% "akka-slf4j"          % akkaVersion % "test",
        "com.typesafe.akka"    %% "akka-testkit"        % akkaVersion  % "test",
        "org.scalatest"        %% "scalatest"           % "2.2.5" % "test",
        "ch.qos.logback"       %  "logback-classic"     % "1.1.2" % "test",
        "junit"           	   % "junit"                % "4.12" % "test"
    )
}
