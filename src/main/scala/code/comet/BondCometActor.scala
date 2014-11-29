package code.comet

import java.util.Date

import code.lib.Init
import net.liftweb.http.RenderOut
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.jquery.JqJE._

case class BondCometActor() extends CustomisableBondCometActor {

  def render: RenderOut = {
    this ! Init
    <h1>Last comms from server at: <span id="now">never!</span></h1>
  }

  def update: Unit = partialUpdate(JqId("now") ~> JqText(new Date().toString))

  override def lowPriority: PartialFunction[Any, Unit] = {
    case _ => {
      println("received tick")
      update
    }
  }
}
