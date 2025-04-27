package controllers

import enums.FilterModeEnum
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

  def getEntries(filter: Option[String]): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val filterMode: FilterModeEnum = FilterModeEnum.fromOptionString(filter)

    hackerNewsService.fetchEntries(filterMode)
      .map(entries => Ok(Json.toJson(entries)))
      .recover {
        case e: Throwable =>
          InternalServerError(Json.obj(
            "error" -> "Unexpected error fetching Hacker News entries",
            "details" -> e.getMessage
          ))
      }
  }
}
