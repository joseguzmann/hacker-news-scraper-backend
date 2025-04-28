package controllers

import javax.inject._
import play.api.mvc._
import play.api.Environment
import scala.io.Source

@Singleton
class SwaggerController @Inject()(
  val controllerComponents: ControllerComponents,
  env: Environment
) extends BaseController {

  def swaggerSpec: Action[AnyContent] = Action {
    val file = env.getFile("public/swagger.json")
    val source = Source.fromFile(file)
    val jsonString = try source.mkString finally source.close()

    Ok(jsonString).as("application/json")
  }
}
