package controllers

import formats.HackerNewsEntryFormats.hackerNewsEntryWrites
import play.api.mvc._
import services.HackerNewsService

import javax.inject._
import scala.concurrent.ExecutionContext
import play.api.libs.json.Json

@Singleton
class HackerNewsController @Inject()(
  hackerNewsService: HackerNewsService
)(val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController {

  def getEntries: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    hackerNewsService.fetchEntries().map { entries =>
      Ok(Json.toJson(entries))
    }
  }
}
