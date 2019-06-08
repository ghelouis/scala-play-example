package service.tests

import java.time.Instant
import java.util.{Date, UUID}

import dao.User
import dto.SimpleUserDTO
import org.mockito.ArgumentCaptor
import org.mockito.Matchers.any
import org.mockito.Mockito.{times, verify, when}
import org.scalatest.AsyncFlatSpec
import org.scalatest.mockito.MockitoSugar
import repository.UserRepository
import service.UserService

import scala.concurrent.Future

class UserServiceSpec extends AsyncFlatSpec with MockitoSugar {

  "createUser" should "create a user" in {
    // given
    val userRepository = mock[UserRepository]
    val userService = new UserService(userRepository, executionContext)
    val user = SimpleUserDTO("Jon", 23)
    when(userRepository.save(any[User])).thenReturn(Future.successful(1))

    // when
    val (futureResult, userId) = userService.createUser(user)

    // then
    futureResult.map(result => {
      val capturedUser = ArgumentCaptor.forClass(classOf[User])
      verify(userRepository, times(1)).save(capturedUser.capture())
      assert(capturedUser.getValue.id == userId.id)
      assert(capturedUser.getValue.name == user.name)
      assert(capturedUser.getValue.age == user.age)
    })
  }

  "getUser" should "return the user if it exists" in {
    // given
    val userRepository = mock[UserRepository]
    val userService = new UserService(userRepository, executionContext)
    val id = UUID.fromString("b6689388-426e-4985-a1f5-c69bb2577eb2")
    val creationDate = Date.from(Instant.parse("2019-05-19T17:11:00.00Z"))
    val user = User(id, "Jon", 23, creationDate)
    when(userRepository.get(id)).thenReturn(Future.successful(Some(User(id, user.name, user.age, creationDate))))

    // when
    val futureMaybeUser = userService.getUser(id)

    // then
    futureMaybeUser.map(maybeUser => {
      verify(userRepository, times(1)).get(id)
      assert(maybeUser.isDefined)
      assert(maybeUser.get.id == user.id)
      assert(maybeUser.get.name == user.name)
      assert(maybeUser.get.age == user.age)
    })
  }

  it should "return None if the user doesn't exist" in {
    // given
    val userRepository = mock[UserRepository]
    val userService = new UserService(userRepository, executionContext)
    val id = UUID.fromString("b6689388-426e-4985-a1f5-c69bb2577eb2")
    when(userRepository.get(id)).thenReturn(Future.successful(None))

    // when
    val futureMaybeUser = userService.getUser(id)

    // then
    futureMaybeUser.map(maybeUser => {
      verify(userRepository, times(1)).get(id)
      assert(maybeUser.isEmpty)
    })
  }

  "getUsers" should "return all users" in {
    // given
    val userRepository = mock[UserRepository]
    val userService = new UserService(userRepository, executionContext)
    when(userRepository.getAll).thenReturn(Future.successful(List()))

    // when
    val futureUsers = userService.getUsers

    // then
    futureUsers.map(users => {
      verify(userRepository, times(1)).getAll
      assert(users.isEmpty)
    })
  }
}
