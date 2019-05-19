package controllers

import java.util.UUID

import dto.{SimpleUser, User, UserId}
import io.swagger.annotations.{Api, ApiImplicitParam, ApiImplicitParams, ApiOperation, ApiResponse, ApiResponses}
import javax.inject._
import play.api.libs.json.{JsSuccess, JsValue, Json, Reads, Writes}
import play.api.mvc._
import service.UserService


@Api("users")
@Singleton
class UserController @Inject()(cc: ControllerComponents, userService: UserService) extends AbstractController(cc) {

  private implicit val userReads: Reads[User] = Json.reads[User]

  private implicit val simpleUserReads: Reads[SimpleUser] = Json.reads[SimpleUser]

  private implicit val userWrites: Writes[User] = Json.writes[User]

  private implicit val userIdWrites: Writes[UserId] = Json.writes[UserId]

  @ApiOperation(value = "Create a new user", response = classOf[UserId])
  @ApiResponses(Array(new ApiResponse(code = 400, message = "Invalid input")))
  @ApiImplicitParams(Array(new ApiImplicitParam(value = "User object that needs to be added", required = true, dataType = "dto.SimpleUser", paramType = "body")))
  def createUser: Action[JsValue] = Action(parse.json) { request: Request[JsValue] =>
    Json.fromJson[SimpleUser](request.body) match {
      case JsSuccess(user, _) =>
        val id = userService.createUser(user)
        Ok(Json.toJson(id))
      case _ => BadRequest
    }
  }

  @ApiOperation(value = "Get an existing user", response = classOf[User])
  @ApiResponses(Array(new ApiResponse(code = 404, message = "Not found")))
  @ApiImplicitParams(Array(new ApiImplicitParam(value = "ID of the user to retrieve", required = true, dataType = "String", paramType = "path")))
  def getUser(id: UUID): Action[AnyContent] = Action {
    userService.getUser(id) match {
      case Some(user) => Ok(Json.toJson(user))
      case None => NotFound
    }
  }

  @ApiOperation(value = "Get all existing users", response = classOf[User], responseContainer = "List")
  def getUsers: Action[AnyContent] = Action {
    Ok(Json.toJson(userService.getUsers))
  }
}
