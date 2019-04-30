package controllers

import io.swagger.annotations.{Api, ApiParam, ApiResponse, ApiResponses}
import javax.inject._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
@Api("Main")
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
  
  def explore() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.explore())
  }

  def tutorial() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.tutorial())
  }

  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Success!"),
    new ApiResponse(code = 404, message = "Missing parameter")))
  def hello(@ApiParam(value = "Name of the person to greet") name: String) = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.hello(name))
  }

  def redirectDocs = Action {
    Redirect(url = "/assets/lib/swagger-ui/index.html", queryString = Map("url" -> Seq("/swagger.json")))
  }

}
