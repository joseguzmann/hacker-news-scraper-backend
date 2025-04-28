package handlers

import play.api.OptionalSourceMapper
import play.api.http.DefaultHttpErrorHandler
import play.api.mvc._
import play.api.routing.Router

import javax.inject.{Provider, Singleton}
import scala.concurrent._

@Singleton
class ErrorHandler @javax.inject.Inject() (
  env: play.api.Environment,
  config: play.api.Configuration,
  sourceMapper: OptionalSourceMapper,
  router: Provider[Router]
)(implicit ec: ExecutionContext) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

  override protected def onNotFound(request: RequestHeader, message: String): Future[Result] = {
    Future.successful(
      Results.Redirect("/docs/index.html")
    )
  }
}
