package bootstrap.liftweb

import java.io.File
import java.net.URL

import org.eclipse.jetty.server.{HttpConnectionFactory, HttpConfiguration, ServerConnector, Server}
import org.eclipse.jetty.webapp.WebAppContext

object Start extends App {

  val buffer = 5000000

  def httpConfig = {
    val config = new HttpConfiguration()
    config.setOutputBufferSize(buffer)
    config
  }

  val server = new Server()
  val connector = new ServerConnector(server, new HttpConnectionFactory(httpConfig))
  connector.setPort(8081)

  server.addConnector(connector)

  val webctx = new WebAppContext()
  webctx.setContextPath("/")
  webctx.setTempDirectory(File.createTempFile("ppp", "s"))

  webctx.setServer(server)
  webctx.setWar("src/main/webapp")
//
//  val resource: URL = webctx.getClass.getClassLoader.getResource("webapp")
//  val webappDirFromJar = resource.toExternalForm
//  webctx.setWar(webappDirFromJar)

  server.setHandler(webctx)
  server.start()
  server.join()
}
