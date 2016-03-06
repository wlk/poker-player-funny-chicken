package org.leanpoker.player

import javax.servlet.annotation.WebServlet
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import com.google.gson.JsonParser

@WebServlet(Array("/"))
class PlayerServlet extends HttpServlet {

  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    resp.getWriter.print("Scala player is running")
  }

  @Override
  override def doPost(req: HttpServletRequest, resp: HttpServletResponse) {
    req.getParameter("action") match {
      case "bet_request" =>
        val gameState = req.getParameter("game_state")
        resp.getWriter.print(Player.betRequest(new JsonParser().parse(gameState)))
      case "showdown" =>
        val gameState = req.getParameter("game_state")
        Player.showdown(new JsonParser().parse(gameState))
      case _ =>
        resp.getWriter.print(Player.VERSION)
    }
  }
}
