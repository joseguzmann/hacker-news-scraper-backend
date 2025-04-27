package controllers

import enums.FilterModeEnum
import formats.HackerNewsEntryFormats.hackerNewsEntryWrites
import play.api.mvc._
import services.HackerNewsService

import javax.inject._
import scala.concurrent.ExecutionContext
import play.api.libs.json.Json
import play.api.Logging

import scala.util.{Failure, Success}

@Singleton
class HackerNewsController @Inject()(
  hackerNewsService: HackerNewsService
)(val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController with Logging {

  def getEntries(filter: Option[String]): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val filterMode: FilterModeEnum = FilterModeEnum.fromOptionString(filter)

    hackerNewsService.fetchEntries(filterMode)
      .transform {
        case Success(entries) =>
          Success(Ok(Json.toJson(entries)))

        case Failure(e) =>
          logger.error("Unexpected error fetching Hacker News entries", e)

          Success(InternalServerError(Json.obj(
            "error" -> "Unexpected error fetching Hacker News entries",
            "details" -> e.getMessage
          )))
      }
  }
}
