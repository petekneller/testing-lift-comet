package code.comet

import net.liftweb.common.Box
import net.liftweb.http.{CometActor, LiftSession}

import scala.xml.NodeSeq

trait CustomisableCometActor extends CometActor {
  override def initCometActor(
                               theSession: LiftSession,
                               theType: Box[String],
                               name: Box[String],
                               defaultHtml: NodeSeq,
                               attributes: Map[String, String]) = super.initCometActor(theSession, theType, name, defaultHtml, attributes)

}
