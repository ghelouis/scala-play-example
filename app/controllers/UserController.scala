package controllers

import java.util.UUID

import dto.{SimpleUserDTO, UserDTO, UserIdDTO}
import io.swagger.annotations.{Api, ApiImplicitParam, ApiImplicitParams, ApiOperation, ApiResponse, ApiResponses}
import javax.inject._
import play.api.libs.json.{JsSuccess, JsValue, Json, Reads, Writes}
import play.api.mvc._
import service.UserService

import scala.concurrent.{ExecutionContext, Future}


@Api("users")
@Singleton
class UserController @Inject()(implicit val ec: ExecutionContext, cc: ControllerComponents, userService: UserService) extends AbstractController(cc) {

  private implicit val userReads: Reads[UserDTO] = Json.reads[UserDTO]

  private implicit val simpleUserReads: Reads[SimpleUserDTO] = Json.reads[SimpleUserDTO]

  private implicit val userWrites: Writes[UserDTO] = Json.writes[UserDTO]

  private implicit val userIdWrites: Writes[UserIdDTO] = Json.writes[UserIdDTO]

  @ApiOperation(value = "Create a new user", response = classOf[UserIdDTO])
  @ApiResponses(Array(new ApiResponse(code = 400, message = "Invalid input")))
  @ApiImplicitParams(Array(new ApiImplicitParam(value = "User object that needs to be added", required = true, dataType = "dto.SimpleUserDTO", paramType = "body")))
  def createUser: Action[JsValue] = Action.async(parse.json) { request: Request[JsValue] =>
    Json.fromJson[SimpleUserDTO](request.body) match {
      case JsSuccess(user, _) =>
        val (futureResult, id) = userService.createUser(user)
        futureResult.map(result =>
          if (result == 0) {
            BadRequest
          } else {
            Ok(Json.toJson(id))
          }).recover {
          case e: Throwable => BadRequest(e.getMessage)
        }
      case _ =>
        Future.successful(BadRequest)
    }
  }

  @ApiOperation(value = "Get an existing user", response = classOf[UserDTO])
  @ApiResponses(Array(new ApiResponse(code = 404, message = "Not found")))
  @ApiImplicitParams(Array(new ApiImplicitParam(value = "ID of the user to retrieve", required = true, dataType = "String", paramType = "path")))
  def getUser(id: UUID): Action[AnyContent] = Action.async {
    userService.getUser(id) map {
      case Some(user) => Ok(Json.toJson(user))
      case None => NotFound
    } recover {
      case e: Throwable => BadRequest(e.getMessage)
    }
  }

  @ApiOperation(value = "Get all existing users", response = classOf[UserDTO], responseContainer = "List")
  def getUsers: Action[AnyContent] = Action.async {
    userService.getUsers.map(user =>
      Ok(Json.toJson(user))
    ).recover {
      case e: Throwable => BadRequest(e.getMessage)
    }
  }
}
