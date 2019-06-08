package controllers.tests

import java.util.UUID

import dto.UserDTO
import org.mockito.Mockito.when
import org.scalatest.FlatSpec
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.inject._
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.{JsArray, Json}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import service.UserService

import scala.concurrent.Future

class UserControllerSpec extends FlatSpec with MockitoSugar with GuiceOneServerPerSuite {

  "createUser" should "return 400 if the body is wrongly formatted" in {
    // given
    val body = "{}"
    val request = FakeRequest(POST, "/users").withBody(Json.parse(body))

    // when
    val Some(result) = route(app, request)

    // then
    assert(status(result) == BAD_REQUEST)
  }

  it should "return the user id" in {
    // given
    val body = """{ "name": "Jon", "age": 23 }"""
    val request = FakeRequest(POST, "/users").withBody(Json.parse(body))

    // when
    val Some(result) = route(app, request)

    // then
    assert(status(result) == OK)
  }

  "getUser" should "return 404 if the user doesn't exist" in {
    // given
    val id = "17B8C995-69F3-464C-95A2-31DEF704A371"
    val request = FakeRequest(GET, s"/users/$id")

    // when
    val Some(result) = route(app, request)

    // then
    assert(status(result) == NOT_FOUND)
  }

  it should "return the user" in {
    // given
    val userService = mock[UserService]
    val app = new GuiceApplicationBuilder()
        .bindings(bind[UserService] to userService)
    .build()
    val id = UUID.fromString("17B8C995-69F3-464C-95A2-31DEF704A371")
    val request = FakeRequest(GET, s"/users/$id")
    val user = UserDTO(id, "Jon", 23)
    when(userService.getUser(id)).thenReturn(Future.successful(Some(user)))

    // when
    val Some(result) = route(app, request)

    // then
    assert(status(result) == OK)
    val json = contentAsJson(result)
    assert((json \ "id").as[UUID] == user.id)
    assert((json \ "name").as[String] == user.name)
    assert((json \ "age").as[Int] == user.age)
  }

  "getUsers" should "return all existing users" in {
    // given
    val userService = mock[UserService]
    val app = new GuiceApplicationBuilder()
      .bindings(bind[UserService] to userService)
      .build()
    val request = FakeRequest(GET, s"/users")
    val jon = UserDTO(UUID.randomUUID(), "Jon", 23)
    val dany = UserDTO(UUID.randomUUID(), "Daenerys", 23)
    when(userService.getUsers).thenReturn(Future.successful(List(jon, dany)))

    // when
    val Some(result) = route(app, request)

    // then
    assert(status(result) == OK)
    val users = contentAsJson(result).as[JsArray].value
    assert(users.size == 2)
    assert((users(0) \ "id").as[UUID] == jon.id)
    assert((users(0) \ "name").as[String] == jon.name)
    assert((users(0) \ "age").as[Int] == jon.age)
    assert((users(1) \ "id").as[UUID] == dany.id)
    assert((users(1) \ "name").as[String] == dany.name)
    assert((users(1) \ "age").as[Int] == dany.age)
  }
}
