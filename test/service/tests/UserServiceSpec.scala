package service.tests

import java.time.Instant
import java.util.{Date, UUID}

import dao.User
import dto.SimpleUser
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.{times, verify, when}
import org.scalatest.FlatSpec
import org.scalatest.mockito.MockitoSugar
import repository.UserRepository
import service.UserService

class UserServiceSpec extends FlatSpec with MockitoSugar {

  "createUser" should "create a user" in {
    // given
    val userRepository = mock[UserRepository]
    val userService = new UserService(userRepository)
    val user = SimpleUser("Jon", 23)

    // when
    val userId = userService.createUser(user)

    // then
    val capturedUser = ArgumentCaptor.forClass(classOf[User])
    verify(userRepository, times(1)).save(capturedUser.capture())
    assert(capturedUser.getValue.id == userId.id)
    assert(capturedUser.getValue.name == user.name)
    assert(capturedUser.getValue.age == user.age)
  }

  "getUser" should "return the user if it exists" in {
    // given
    val userRepository = mock[UserRepository]
    val userService = new UserService(userRepository)
    val id = UUID.fromString("b6689388-426e-4985-a1f5-c69bb2577eb2")
    val creationDate = Date.from(Instant.parse("2019-05-19T17:11:00.00Z"))
    val user = User(id, "Jon", 23, creationDate)
    when(userRepository.get(id)).thenReturn(Some(User(id, user.name, user.age, creationDate)))

    // when
    val maybeUser = userService.getUser(id)

    // then
    verify(userRepository, times(1)).get(id)
    assert(maybeUser.isDefined)
    assert(maybeUser.get.id == user.id)
    assert(maybeUser.get.name == user.name)
    assert(maybeUser.get.age == user.age)
  }

  it should "return None if the user doesn't exist" in {
    // given
    val userRepository = mock[UserRepository]
    val userService = new UserService(userRepository)
    val id = UUID.fromString("b6689388-426e-4985-a1f5-c69bb2577eb2")
    when(userRepository.get(id)).thenReturn(None)

    // when
    val maybeUser = userService.getUser(id)

    // then
    verify(userRepository, times(1)).get(id)
    assert(maybeUser.isEmpty)
  }

  "getUsers" should "return all users" in {
    // given
    val userRepository = mock[UserRepository]
    val userService = new UserService(userRepository)
    when(userRepository.getAll).thenReturn(List())

    // when
    userService.getUsers

    // then
    verify(userRepository, times(1)).getAll
  }
}
