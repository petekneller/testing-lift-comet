resolvers += Resolver.url("sbt-plugin-snapshots", new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-snapshots"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.earldouglas" % "xsbt-web-plugin" % "0.6.0")


addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.11.2")
