package service

import java.util.UUID

import dto.{User, UserId}
import javax.inject.Inject
import repository.UserRepository

class UserService @Inject()(userRepository: UserRepository) {

  def createUser(user: User): UserId = {
    val id = UUID.randomUUID()
    userRepository.save(dao.User(id, user.name, user.age))
    UserId(id.toString)
  }

  def getUser(id: String): Option[User] = {
    userRepository.get(UUID.fromString(id)) match {
      case Some(user) => Some(toDto(user))
      case None => None
    }
  }

  def getUsers: Iterable[User] = {
    userRepository.getAll.map(toDto)
  }

  private def toDto(user: dao.User) = {
    User(user.name, user.age)
  }
}
