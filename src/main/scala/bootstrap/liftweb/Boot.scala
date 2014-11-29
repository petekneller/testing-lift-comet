package bootstrap.liftweb

import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.Timer

import code.comet.BondCometActor
import code.lib.Tick
import net.liftweb.common.Full
import net.liftweb.http.{CometActor, CometCreationInfo, LiftRules}
import net.liftweb.sitemap.Menu.{Menuable, WithSlash}
import net.liftweb.sitemap.{Menu, SiteMap}

import scala.reflect.ClassTag


class Boot {
  def boot {

    try {
      LiftRules.addToPackages("code")

      val entries: List[Menuable with WithSlash] = List(
        Menu.i("Bond") / "index"
      )
      LiftRules.setSiteMap(SiteMap(entries: _*))

      LiftRules.ajaxStart = Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

      LiftRules.ajaxEnd = Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

      LiftRules.early.append(_.setCharacterEncoding("UTF-8"))


      LiftRules.cometCreation.append {
        case CometCreationInfo(contType, name, html, attr, session) if contType == comet[BondCometActor] ⇒ {
          val actor = BondCometActor()
          actor.initCometActor(session, Full(comet[BondCometActor]), name, html, attr)

          new Timer(5000, new ActionListener {
            override def actionPerformed(p1: ActionEvent): Unit = {
              println("sending tick")
              actor ! Tick
            }
          }).start()

          actor
        }
      }

    }
  }

  private def comet[T <: CometActor : ClassTag]: String = implicitly[ClassTag[T]].runtimeClass.getSimpleName

}
